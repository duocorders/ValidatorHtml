package model.list;

public class NodeList<T> {
    private T data;
    private NodeList<T> next;
    
    public NodeList() {
    }

    public T getData(){
        return this.data;
    }

    public void setData(T changeData){
        this.data = changeData;
    }

    public NodeList<T> getNext(){
        return this.next;
    }

    public void setNext(NodeList<T> next){
        this.next = next;
    }
}
