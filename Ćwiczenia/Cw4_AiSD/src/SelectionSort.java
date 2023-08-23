import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SelectionSort<T>{
    private final Comparator<T> comparator;
    private List<T> list;

    public SelectionSort(Comparator<T> comparator, List<T> list) {
        this.comparator = comparator;
        this.list = list;
    }

    public List<T> sort(){
        int length = list.size();

        for(int i=0; i<length-1; i++){
            int indexOfMax = i;
            for(int j=i+1; j<length; j++){
                if(comparator.compare(list.get(j), list.get(indexOfMax))>0){
                    indexOfMax = j;
                }
            }
            Collections.swap(list, i, indexOfMax);
        }
        return list;
    }

    public List<T> sortIT(){
        for(int i=0; i<list.size(); i++){
            int indexOfMin = i;
            int indexOfMax = i;
            for(int j=i+1; j<list.size()-i; j++){
                if(comparator.compare(list.get(j), list.get(indexOfMax))>0){
                    indexOfMax = j;
                }

                if(comparator.compare(list.get(j), list.get(indexOfMin))<0){
                    indexOfMin = j;
                }
            }
            if(list.size()-i-1==indexOfMax){
                indexOfMax = indexOfMin;
            }

            Collections.swap(list, list.size()-i-1, indexOfMin);
            Collections.swap(list, i, indexOfMax);
        }
        return list;
    }

}
