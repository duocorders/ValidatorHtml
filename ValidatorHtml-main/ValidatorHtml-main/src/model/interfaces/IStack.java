package model.interfaces;

public interface IStack<T> {
    public void push(T info);
    public T pop();
    public T peek();
    public boolean isEmpty();
    public void release();
}
