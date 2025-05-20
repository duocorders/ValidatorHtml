package model;

import model.interfaces.IStack;
import model.utils.EmptyStackException;

public class Stack<T> implements IStack<T> {
    private ChainedList<T> list = new ChainedList<T>();

    @Override
    public void push(T info) {
        list.insert(info);
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
        return list.getFirst().getInfo();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void release() {
        list = new ChainedList<>();

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