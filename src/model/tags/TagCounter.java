package model.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import model.sort.*;

public class TagCounter {
    private final List<TagInfo> tags = new ArrayList<>();

    public void add(String tag) {
        tag = tag.toLowerCase();
        for (TagInfo tagInfo : tags) {
            if (tagInfo.getName().equals(tag)) {
                tagInfo.increment();
                return;
            }
        }
        tags.add(new TagInfo(tag));
    }

    public TagInfo[] getSortedTags() {
        TagInfo[] array = tags.toArray(new TagInfo[0]);
        AbstractSort<TagInfo> sorter;

        if (array.length <= 10) {
            System.out.println("Using BubbleSort");
            sorter = new BubbleSort<TagInfo>();
        } else {
            System.out.println("Using QuickSort");
            sorter = new QuickSort<TagInfo>();
        }
        System.out.println("array: " + Arrays.toString(array));
        sorter.setData(array);
        sorter.sort();
        System.out.println("sorted: " + Arrays.toString(array));
        return array;
    }
}