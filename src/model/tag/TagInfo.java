package model.tag;

public class TagInfo implements Comparable<TagInfo> {
    private final String nome;
    private int contador;

    @Override
    public int compareTo(TagInfo other) {
        return this.nome.compareTo(other.nome);
    }

    public TagInfo(String nome) {
        this.nome = nome;
        this.contador = 1;
    }

    public void incrementar() {
        contador++;
    }

    public String getNome() {
        return nome;
    }

    public int getContador() {
        return contador;
    }
}