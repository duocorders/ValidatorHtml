package validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

import model.stack.Stack;
import model.tag.TagCounter;
import util.Util;
public class Validator {
    private final String[] singletons = {
        "meta", "base", "br", "col", "command", "embed", "hr", "img", "input",
        "link", "param", "source", "!doctype"
    };
    private final TagCounter tagCounter = new TagCounter();
    private final StringBuilder report = new StringBuilder();

    public boolean validate(String path) {
        Stack<String> stack = new Stack<>();
        int lineNum = 0;
        boolean inComment = false, inScript = false, inStyle = false;
        boolean hasError = false; // Adicione esta variável no início do método

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty()) continue;

                int pos = 0;
                while (pos < line.length()) {
                    // 1. Comentários
                    if (inComment) {
                        int endComment = line.indexOf("-->", pos);
                        if (endComment == -1) break;
                        pos = endComment + 3;
                        inComment = false;
                        continue;
                    }
                    int startComment = line.indexOf("<!--", pos);
                    if (startComment != -1 && startComment == line.indexOf("<", pos)) {
                        inComment = true;
                        pos = startComment + 4;
                        continue;
                    }

                    // 2. Próxima tag
                    int start = line.indexOf("<", pos);
                    if (start == -1) break;

                    // 3. Ignorar se ainda em comentário
                    if (inComment) {
                        pos = start + 1;
                        continue;
                    }

                    // 4. Encontrar fechamento da tag, ignorando > em aspas
                    int end = findTagEnd(line, start);
                    if (end == -1) break;

                    String fullTag = line.substring(start + 1, end).trim();
                    boolean closing = fullTag.startsWith("/");
                    String tagName = Util.extractTagName(fullTag).toLowerCase();

                    // 5. Script/style blocks
                    if (!inScript && !inStyle && !closing) {
                        if (tagName.equals("script")) {
                            inScript = true;
                        } else if (tagName.equals("style")) {
                            inStyle = true;
                        }
                    } else if (inScript && closing && tagName.equals("script")) {
                        inScript = false;
                        pos = end + 1;
                        continue;
                    } else if (inStyle && closing && tagName.equals("style")) {
                        inStyle = false;
                        pos = end + 1;
                        continue;
                    }

                    // 6. Ignorar tags dentro de script/style
                    if (inScript || inStyle) {
                        pos = end + 1;
                        continue;
                    }

                    // 7. Tag auto-fechante
                    boolean selfClosing = isSelfClosing(fullTag, tagName);

                    if (selfClosing) {
                        tagCounter.add(tagName);
                    } else if (closing) {
                        if (stack.isEmpty()) {
                            report.append("Erro linha ").append(lineNum)
                                  .append(": tag final </").append(tagName)
                                  .append("> sem correspondente de abertura.\n");
                            hasError = true;
                            pos = end + 1; // <-- Adicione esta linha
                            continue;
                        }
                        String lastOpened = stack.pop();
                        if (!lastOpened.equals(tagName)) {
                            // Reporta todas as tags abertas até encontrar a correta
                            report.append("Erro linha ").append(lineNum)
                                  .append(": esperava </").append(lastOpened)
                                  .append(">, mas encontrou </").append(tagName).append(">\n");
                            hasError = true;
                            // Desempilha até encontrar a tag correta ou a pilha acabar
                            while (!stack.isEmpty() && !stack.peek().equals(tagName)) {
                                String missing = stack.pop();
                                report.append("Faltando tag final para <").append(missing).append(">\n");
                            }
                            if (!stack.isEmpty()) {
                                stack.pop(); // Remove a tag correspondente
                            }
                            pos = end + 1;
                            continue;
                        }
                    } else {
                        stack.push(tagName);
                        tagCounter.add(tagName);
                    }

                    pos = end + 1;
                }
            }

            while (!stack.isEmpty()) {
                report.append("Faltando tag final para <").append(stack.pop()).append(">\n");
                hasError = true;
            }

        } catch (Exception e) {
            report.append("Erro ao ler arquivo: ").append(e.getMessage()).append("\n");
            return false;
        }

        if (hasError) {
            return false;
        }
        report.append("Arquivo está bem formatado!\n");
        return true;
    }

    // Função auxiliar para encontrar o fim da tag ignorando > dentro de aspas
    private int findTagEnd(String line, int start) {
        int end = start + 1;
        boolean inQuotes = false;
        char quoteChar = 0;
        while (end < line.length()) {
            char c = line.charAt(end);
            if ((c == '"' || c == '\'') && (end == start + 1 || line.charAt(end - 1) != '\\')) {
                if (!inQuotes) {
                    inQuotes = true;
                    quoteChar = c;
                } else if (c == quoteChar) {
                    inQuotes = false;
                }
            } else if (c == '>' && !inQuotes) {
                return end;
            }
            end++;
        }
        return -1;
    }

    // Função auxiliar para checar se a tag é auto-fechante
    private boolean isSelfClosing(String fullTag, String tagName) {
        return fullTag.endsWith("/") || Arrays.asList(singletons).contains(tagName);
    }

    public String getReport() {
        return report.toString();
    }

    public TagCounter getTagCounter() {
        return tagCounter;
    }
}