package model;
public class Stack<T> {
    private final Object[] elements;
    private int top;

    public Stack(int capacity) {
        elements = new Object[capacity];
        top = -1;
    }

    public void push(T item) {
        elements[++top] = item;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        return (T) elements[top--];
    }

    @SuppressWarnings("unchecked")
    public T peek() {
        return (T) elements[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}