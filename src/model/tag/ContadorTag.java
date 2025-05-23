package model.tag;

import model.list.ListaEncadeada;
import model.list.NoLista;
import model.sort.*;

public class ContadorTag {
    private final ListaEncadeada<TagInfo> tags = new ListaEncadeada<>();

    public void add(String tag) {
        tag = tag.toLowerCase();

        int tamanho = tags.obterComprimento();
        for (int i = 0; i < tamanho; i++) {
            
            NoLista<TagInfo> nodeTagInfo = tags.obterNo(i);
            TagInfo tagInfo = nodeTagInfo.obterInfo();

            if (tagInfo.getName().equals(tag)) {
                tagInfo.increment();
                return;
            }
        }
        tags.insert(new TagInfo(tag));
    }

    public TagInfo[] getSortedTags() {
        
        int length = tags.obterComprimento();
        TagInfo[] array = new TagInfo[length];
        
        for (int i = 0; i < length; i++) {
            array[i] = tags.obterNo(i).obterInfo();
        }

        OrdenacaoAbstract<TagInfo> sorter;

        if (array.length <= 10) {
            sorter = new OrdenacaoBolha<TagInfo>();
        } else {
            sorter = new OrdenacaoQuickSort<TagInfo>();
        }

        sorter.setinfo(array); 
        sorter.sort();
        return array;
    }
}