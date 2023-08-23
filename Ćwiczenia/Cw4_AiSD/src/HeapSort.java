import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HeapSort <T>{
    private final Comparator<T> comparator;
    private ArrayList<T> list;

    public HeapSort(Comparator<T> comparator, ArrayList<T> list) {
        this.comparator = comparator;
        this.list = list;
    }

    public void sink(int index){
        int maxIndex = index;
        int childIndex = 2*index+1;

        if(childIndex<list.size()){// size sie zmienia
            if(comparator.compare(list.get(childIndex), list.get(maxIndex))>0){
                maxIndex = childIndex;
            }
        }

        if(childIndex+1<list.size()){
            if(comparator.compare(list.get(childIndex+1), list.get(maxIndex))>0){
                maxIndex = childIndex;
            }
        }

        Collections.swap(list, index, maxIndex);
        if(index!=maxIndex){
            sink(maxIndex);
        }
    }

    public void buildHeap(){
        for(int i = (list.size()/2)-1; i>=0; i--){
            sink(i);
        }
    }

    public ArrayList<T> heapSort(){
        buildHeap();
        for(int i=list.size()-1; i>0; i--){
            Collections.swap(list, i, 0);
            sink(0);
        }
        return list;
    }
}
