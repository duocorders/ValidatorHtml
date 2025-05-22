package model.tags;

public class TagInfo implements Comparable<TagInfo> {
    private final String name;
    private int count;

    @Override
    public int compareTo(TagInfo other) {
        return this.name.compareTo(other.name);
    }

    public TagInfo(String name) {
        this.name = name;
        this.count = 1;
    }

    public void increment() {
        count++;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}