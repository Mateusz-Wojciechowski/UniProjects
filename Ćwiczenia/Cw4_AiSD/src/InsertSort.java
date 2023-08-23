import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class InsertSort<T> {
    private final Comparator<T> comparator;

    public InsertSort(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public ArrayList<T> sort(ArrayList<T> list){
        int length = list.size();

        for(int i=1; i<length; i++){
            int j = i-1;
            T tmp = list.get(i);
            while(j>=0 && comparator.compare(tmp, list.get(j))>0){
                list.set(j+1, list.get(j));
                j--;
            }
            list.set(j+1, tmp);
        }
        return list;
    }

    public List<T> sortIT(List<T> list) {

        for(int i=1; i<list.size(); i++){
            T key = list.get(i);
            int insertPos = binarySearch(list, 0, i, key);
            int j = i;
            while(j>insertPos){
                Collections.swap(list, j, j-1);
                j--;
            }
            list.set(insertPos, key);
        }
        return list;
    }

    public int binarySearch(List<T> list, int firstIndex, int lastIndex, T key){

        while(firstIndex<lastIndex){
            int midIndex = (firstIndex + lastIndex)/2;
            if(comparator.compare(list.get(midIndex), key)<=0){
                firstIndex = midIndex + 1;
            }
            else lastIndex = midIndex;
        }
        return firstIndex;
    }

}
