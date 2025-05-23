package validator;

import java.io.BufferedReader;
import java.io.FileReader;

import model.stack.PilhaLista;
import model.tag.ContadorTag;
import util.Util;
public class Validator {
    private final String[] singletons = {
        "meta", "base", "br", "col", "command", "embed", "hr", "img", "input",
        "link", "param", "source", "!doctype"
    };
    private final ContadorTag contadorTag = new ContadorTag();
    private final StringBuilder report = new StringBuilder();

    public boolean validar(String caminho) {
        PilhaLista<String> pilhaLista = new PilhaLista<>();
        int linhaNum = 0;
        boolean ehComentario = false, ehScript = false, ehStyle = false;
        boolean estaComErro = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                linhaNum++;
                linha = linha.trim();

                if (linha.isEmpty()) 
                    continue;

                int pos = 0;
                while (pos < linha.length()) {

                    if (ehComentario) {
                        int fimComentario = linha.indexOf("-->", pos);

                        if (fimComentario == -1) 
                            break;
                            
                        pos = fimComentario + 3;
                        ehComentario = false;
                        continue;
                    }
                    
                    int inicioComentario = linha.indexOf("<!--", pos);
                    if (inicioComentario != -1 && inicioComentario == linha.indexOf("<", pos)) {
                        ehComentario = true;
                        pos = inicioComentario + 4;
                        continue;
                    }

                    int inicio = linha.indexOf("<", pos);
                    if (inicio == -1) 
                        break;

                    if (ehComentario) {
                        pos = inicio + 1;
                        continue;
                    }

                    int fim = encontrarTagFim(linha, inicio);
                    if (fim == -1) 
                        break;

                    String tagCompleta = linha.substring(inicio + 1, fim).trim();
                    boolean fechamentoTag = tagCompleta.startsWith("/");
                    String tagName = Util.extrairTagName(tagCompleta).toLowerCase();

                    if (!ehScript && !ehStyle && !fechamentoTag) {
                        if (tagName.equals("script")) {
                            ehScript = true;
                        } else if (tagName.equals("style")) {
                            ehStyle = true;
                        }
                    } else if (ehScript && fechamentoTag && tagName.equals("script")) {
                        ehScript = false;
                        pos = fim + 1;
                        continue;
                    } else if (ehStyle && fechamentoTag && tagName.equals("style")) {
                        ehStyle = false;
                        pos = fim + 1;
                        continue;
                    }

                    if (ehScript || ehStyle) {
                        pos = fim + 1;
                        continue;
                    }

                    boolean ehSingletonTag = verificarSingletonTag(tagCompleta, tagName);

                    if (ehSingletonTag) {
                        contadorTag.adicionar(tagName);
                    } else if (fechamentoTag) {
                        if (pilhaLista.estaVazia()) {
                            report.append("Erro linha ").append(linhaNum)
                                  .append(": tag final </").append(tagName)
                                  .append("> sem correspondente de abertura.\n");
                            estaComErro = true;
                            pos = fim + 1; 
                            continue;
                        }
                        String lastOpened = pilhaLista.pop();
                        if (!lastOpened.equals(tagName)) {

                            report.append("Erro linha ").append(linhaNum)
                                  .append(": esperava </").append(lastOpened)
                                  .append(">, mas encontrou </").append(tagName).append(">\n");
                            estaComErro = true;

                            while (!pilhaLista.estaVazia() && !pilhaLista.peek().equals(tagName)) {
                                String missing = pilhaLista.pop();
                                report.append("Faltando tag final para <").append(missing).append(">\n");
                            }
                            if (!pilhaLista.estaVazia()) {
                                pilhaLista.pop();
                            }
                            pos = fim + 1;
                            continue;
                        }
                    } else {
                        pilhaLista.push(tagName);
                        contadorTag.adicionar(tagName);
                    }

                    pos = fim + 1;
                }
            }

            while (!pilhaLista.estaVazia()) {
                report.append("Faltando tag final para <").append(pilhaLista.pop()).append(">\n");
                estaComErro = true;
            }

        } catch (Exception e) {
            report.append("Erro ao ler arquivo: ").append(e.getMessage()).append("\n");
            return false;
        }

        if (estaComErro) {
            return false;
        }
        report.append("Arquivo está bem formatado!\n");
        return true;
    }

    private int encontrarTagFim(String linha, int inicio) {
        int fim = inicio + 1;
        boolean dentroDeAspas = false;
        char quoteChar = 0;
        while (fim < linha.length()) {
            char c = linha.charAt(fim);
            if ((c == '"' || c == '\'') && (fim == inicio + 1 || linha.charAt(fim - 1) != '\\')) {
                if (!dentroDeAspas) {
                    dentroDeAspas = true;
                    quoteChar = c;
                } else if (c == quoteChar) {
                    dentroDeAspas = false;
                }
            } else if (c == '>' && !dentroDeAspas) {
                return fim;
            }
            fim++;
        }
        return -1;
    }

    private boolean verificarSingletonTag(String tagCompleta, String tagName) {
        if (tagCompleta.endsWith("/")) {
            return true;
        }
        for (int i = 0; i < singletons.length; i++) {
            if (singletons[i].equals(tagName)) {
                return true;
            }
        }
        return false;
    }

    public String getReport() {
        return report.toString();
    }

    public ContadorTag getContadorTag() {
        return contadorTag;
    }
}