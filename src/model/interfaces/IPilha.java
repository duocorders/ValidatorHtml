package model.interfaces;

public interface IPilha<T> {
    public void push(T info);
    public T pop();
    public T peek();
    public boolean estaVazia();
    public void liberar();
}
