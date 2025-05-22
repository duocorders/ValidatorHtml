package model.stack;

import model.exception.EmptyStackException;
import model.interfaces.IStack;
import model.list.LinkedList;

public class Stack<T> implements IStack<T> {
    private LinkedList<T> list = new LinkedList<T>();

    @Override
    public void push(T data) {
        list.insert(data);
    }

    @Override
    public T pop() {
        T value;
        value = peek();

        list.remove(value);

        return value;
    }

    @Override
    public T peek() {
        if(isEmpty())
            throw new EmptyStackException();
        return list.getFirst().getData();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void release() {
        list = new LinkedList<>();

        try{
            while (true) {
                pop();
            }
        }catch (EmptyStackException e){
        }
    }

    public String toString(){
        return list.toString();
    }
}