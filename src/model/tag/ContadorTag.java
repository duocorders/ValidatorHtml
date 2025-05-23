package model.tag;

import model.list.ListaEncadeada;
import model.list.NoLista;
import model.sort.*;

public class ContadorTag {
    private final ListaEncadeada<TagInfo> tags = new ListaEncadeada<>();

    public void adicionar(String tag) {
        tag = tag.toLowerCase();

        int tamanho = tags.obterComprimento();
        for (int i = 0; i < tamanho; i++) {
            
            NoLista<TagInfo> noTagInfo = tags.obterNo(i);
            TagInfo tagInfo = noTagInfo.getInfo();

            if (tagInfo.getNome().equals(tag)) {
                tagInfo.incrementar();
                return;
            }
        }
        tags.inserir(new TagInfo(tag));
    }

    public TagInfo[] getTagsOrdenadas() {
        
        int tamanho = tags.obterComprimento();
        TagInfo[] array = new TagInfo[tamanho];
        
        for (int i = 0; i < tamanho; i++) {
            array[i] = tags.obterNo(i).getInfo();
        }

        OrdenacaoAbstract<TagInfo> ordenador;

        if (array.length <= 10) {
            ordenador = new OrdenacaoBolha<TagInfo>();
        } else {
            ordenador = new OrdenacaoQuickSort<TagInfo>();
        }

        ordenador.setInfo(array); 
        ordenador.ordenar();
        return array;
    }
}