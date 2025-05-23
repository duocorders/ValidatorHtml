package model.stack;

import model.exception.PilhaVaziaException;
import model.interfaces.IPilha;
import model.list.ListaEncadeada;

public class PilhaLista<T> implements IPilha<T> {
    private ListaEncadeada<T> list = new ListaEncadeada<T>();

    @Override
    public void push(T data) {
        list.inserir(data);
    }

    @Override
    public T pop() {
        T value;
        value = peek();

        list.retirar(value);

        return value;
    }

    @Override
    public T peek() {
        if(estaVazia())
            throw new PilhaVaziaException();
        return list.getPrimeiro().getInfo();
    }

    @Override
    public boolean estaVazia() {
        return list.estaVazia();
    }

    @Override
    public void liberar() {
        this.list = new ListaEncadeada<>();
    }

    public String toString(){
        return list.toString();
    }
}