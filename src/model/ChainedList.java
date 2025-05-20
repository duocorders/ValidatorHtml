package model;

public class ChainedList<T> {
    public NodeList<T> first;

    public ChainedList() {
        this.first = null;
    }

    public NodeList<T> getFirst() {
        return this.first;
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public void insert(T info) {
        NodeList<T> newNode = new NodeList<>();
        newNode.setInfo(info);
        newNode.setNext(first);
        this.first = newNode;
    }

    public NodeList<T> search(T info) {
        NodeList<T> p = first;
        while (p != null) {
            if (p.getInfo().equals(info)) {
                return p;
            }
            p = p.getNext();
        }
        return null;
    }

    public void remove(T info) {
        NodeList<T> previous = null;
        NodeList<T> p = first;

        while (p != null && !(p.getInfo().equals(info))) {
            previous = p;
            p = p.getNext();
        }

        if (p != null) {
            if (p == this.first) {
                this.first = p.getNext();
            } else {
                previous.setNext(p.getNext());
            }
        }
    }

    public int getLength() {
        int length = 0;
        NodeList<T> p = first;

        while (p != null) {
            length++;
            p = p.getNext();
        }

        return length;
    }

    public NodeList<T> getNode2(int index) {
        int length = getLength();
        NodeList<T> p = first;
        NodeList<T> foundNode = null;

        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index is not a valid position in the list.");
        }

        for (int i = 0; i < length; i++) {
            if (index == i) {
                foundNode = p;
                break;
            }
            p = p.getNext();
        }

        return foundNode;
    }

    public NodeList<T> getNode(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index is not a valid position in the list.");
        }

        NodeList<T> p = first;

        while (p != null && index > 0) {
            index--;
            p = p.getNext();
        }

        if (p == null) {
            throw new IndexOutOfBoundsException("Index is not a valid position in the list.");
        }
        return p;
    }

    public void display() {
        NodeList<T> p = first;
        while (p != null) {
            System.out.println(p.getInfo());
            p = p.getNext();
        }
    }

    public String toString() {
        String result = "";
        NodeList<T> p = first;

        while (p != null) {
            if (p != first) {
                result += ",";
            }
            result += p.getInfo();
            p = p.getNext();
        }

        return result;
    }
}