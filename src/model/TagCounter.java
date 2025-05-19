package model;
public class TagCounter {
    private final TagInfo[] tags = new TagInfo[100];
    private int total = 0;

    public void add(String tag) {
        tag = tag.toLowerCase();
        for (int i = 0; i < total; i++) {
            if (tags[i].name().equals(tag)) {
                tags[i].increment();
                return;
            }
        }
        tags[total++] = new TagInfo(tag);
    }

    public TagInfo[] getSortedTags() {
        for (int i = 0; i < total - 1; i++) {
            int min = i;
            for (int j = i + 1; j < total; j++) {
                if (tags[j].name().compareTo(tags[min].name()) < 0) {
                    min = j;
                }
            }
            TagInfo temp = tags[i];
            tags[i] = tags[min];
            tags[min] = temp;
        }
        TagInfo[] sorted = new TagInfo[total];
        System.arraycopy(tags, 0, sorted, 0, total);
        return sorted;
    }
}