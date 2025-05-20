package model;

public class NodeList<T> {
    private T info;
    private NodeList<T> next;
    
    public NodeList() {
    }

    public T getInfo(){
        return this.info;
    }

    public void setInfo(T changeInfo){
        this.info = changeInfo;
    }

    public NodeList<T> getNext(){
        return this.next;
    }

    public void setNext(NodeList<T> next){
        this.next = next;
    }
}
