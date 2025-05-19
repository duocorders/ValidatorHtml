package model;
public class TagInfo {
    private final String name;
    private int count;

    public TagInfo(String name) {
        this.name = name;
        this.count = 1;
    }

    public void increment() {
        count++;
    }

    public String name() {
        return name;
    }

    public int count() {
        return count;
    }
}