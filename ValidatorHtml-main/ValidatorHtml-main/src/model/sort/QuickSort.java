package model.sort;

public class QuickSort<T extends Comparable<T>> extends AbstractSort<T> {

    @Override
    public void sort() {
        quickSort(0, getData().length - 1);
    }

    private void quickSort(int start, int end) {
        if (start < end) {
            int pivot = partition(start, end);
            quickSort(start, pivot - 1);
            quickSort(pivot + 1, end);
        }
    }

    private int partition(int start, int end) {
        T[] array = getData();
        T pivot = array[start];
        int left = start + 1;
        int right = end;

        while (true) {
            while (left <= end && array[left].compareTo(pivot) <= 0) {
                left++;
            }
            while (right >= start && array[right].compareTo(pivot) > 0) {
                right--;
            }
            if (left >= right) {
                break;
            }
            swap(left, right);
        }

        swap(start, right);
        return right;
    }
}
