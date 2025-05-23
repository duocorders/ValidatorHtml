package model.list;

public class ListaEncadeada<T> {
    public NoLista<T> primeiro;

    public ListaEncadeada() {
        this.primeiro = null;
    }

    public NoLista<T> getPrimeiro() {
        return this.primeiro;
    }

    public boolean estaVazia() {
        return this.primeiro == null;
    }

    public void inserir(T info) {
        NoLista<T> novoNo = new NoLista<>();
        novoNo.setInfo(info);
        novoNo.setProximo(primeiro);
        this.primeiro = novoNo;
    }

    public NoLista<T> search(T info) {
        NoLista<T> p = primeiro;
        while (p != null) {
            if (p.getInfo().equals(info)) {
                return p;
            }
            p = p.getProximo();
        }
        return null;
    }

    public void retirar(T info) {
        NoLista<T> proximo = null;
        NoLista<T> p = primeiro;

        while (p != null && !(p.getInfo().equals(info))) {
            proximo = p;
            p = p.getProximo();
        }

        if (p != null) {
            if (p == this.primeiro) {
                this.primeiro = p.getProximo();
            } else {
                proximo.setProximo(p.getProximo());
            }
        }
    }

    public int obterComprimento() {
        int tamanho = 0;
        NoLista<T> p = primeiro;

        while (p != null) {
            tamanho++;
            p = p.getProximo();
        }

        return tamanho;
    }

    public NoLista<T> obterNo(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Índice não é uma posição válida na lista.");
        }

        NoLista<T> p = primeiro;

        while (p != null && index > 0) {
            index--;
            p = p.getProximo();
        }

        if (p == null) {
            throw new IndexOutOfBoundsException("Índice não é uma posição válida na lista.");
        }
        return p;
    }

    public void exibir() {
        NoLista<T> p = primeiro;
        while (p != null) {
            System.out.println(p.getInfo());
            p = p.getProximo();
        }
    }

    public String toString() {
        String resultado = "";
        NoLista<T> p = primeiro;

        while (p != null) {
            if (p != primeiro) {
                resultado += ",";
            }
            resultado += p.getProximo();
            p = p.getProximo();
        }

        return resultado;
    }
}