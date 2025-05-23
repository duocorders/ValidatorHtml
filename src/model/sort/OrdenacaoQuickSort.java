package model.sort;

public class OrdenacaoQuickSort<T extends Comparable<T>> extends OrdenacaoAbstract<T> {

    @Override
    public void ordenar() {
        quickSort(0, getInfo().length - 1);
    }

    private void quickSort(int inicio, int fim) {
        if (inicio < fim) {
            int pivo = particionar(inicio, fim);
            quickSort(inicio, pivo - 1);
            quickSort(pivo + 1, fim);
        }
    }

    private int particionar(int inicio, int fim) {
        T[] vetor = getInfo();
        T pivo = vetor[inicio];
        int esquerda = inicio + 1;
        int direita = fim;

        while (true) {
            while (esquerda <= fim && vetor[esquerda].compareTo(pivo) <= 0) {
                esquerda++;
            }
            while (direita >= inicio && vetor[direita].compareTo(pivo) > 0) {
                direita--;
            }
            if (esquerda >= direita) {
                break;
            }
            trocar(esquerda, direita);
        }

        trocar(inicio, direita);
        return direita;
    }
}
