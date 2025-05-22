package model.sort;

public abstract class AbstractSort<T extends Comparable<T>> {
    private T[] data;

    public void setData(T[] data) {
        this.data = data;
    }

    public T[] getData() {
        return data;
    }

    public void swap(int a, int b) {
        T temp = data[a];
        data[a] = data[b];
        data[b] = temp;
    }

    public abstract void sort();
}