package validator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;

import model.Stack;
import model.TagCounter;
import model.utils.Util;

public class Validator {
    private final String[] singletons = { "meta", "base", "br", "col", "command", "embed", "hr", "img", "input", "link", "param", "source", "!doctype" };
    private final TagCounter tagCounter = new TagCounter();
    private final StringBuilder report = new StringBuilder();

    public boolean validate(String path) {
        Stack<String> stack = new Stack<String>();
        int lineNum = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lineNum++;
                line = line.trim();
                if (line.isEmpty()) continue;

                int start = line.indexOf("<");

                while (start != -1) {
                    int end = line.indexOf(">", start);
                    if (end == -1) break;

                    String fullTag = line.substring(start + 1, end).trim();
                    boolean closing = fullTag.startsWith("/");
                    String tagName = Util.extractTagName(fullTag);

                    if (Arrays.asList(singletons).contains(tagName)) {
                        tagCounter.add(tagName);
                    } else if (closing) {
                        if (stack.isEmpty()) {
                            report.append("Erro linha ").append(lineNum).append(": tag final inesperada </").append(tagName).append(">\n");
                            return false;
                        }
                        String lastOpened = stack.pop();
                        if (!lastOpened.equals(tagName)) {
                            report.append("Erro linha ").append(lineNum).append(": esperava </").append(lastOpened).append(">, mas encontrou </").append(tagName).append(">\n");
                            return false;
                        }
                    } else {
                        stack.push(tagName);
                        tagCounter.add(tagName);
                    }

                    start = line.indexOf("<", end);
                }
            }

            while (!stack.isEmpty()) {
                report.append("Faltando tag final para <").append(stack.pop()).append(">\n");
                return false;
            }

        } catch (Exception e) {
            report.append("Erro ao ler arquivo: ").append(e.getMessage()).append("\n");
            return false;
        }

        report.append("Arquivo está bem formatado!\n");
        return true;
    }

    public String getReport() {
        return report.toString();
    }

    public TagCounter getTagCounter() {
        return tagCounter;
    }
}