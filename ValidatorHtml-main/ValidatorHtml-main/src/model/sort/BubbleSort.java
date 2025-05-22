package model.sort;

public class BubbleSort<T extends Comparable<T>> extends AbstractSort<T> {
    @Override
    public void sort() {
        T[] vetor = getData();
        for (int i = vetor.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (vetor[j].compareTo(vetor[j + 1]) > 0) {
                    swap(j, j + 1);
                }
            }
        }
    }
}
