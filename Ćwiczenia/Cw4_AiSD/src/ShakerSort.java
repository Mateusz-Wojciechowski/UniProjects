import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShakerSort<T>{
    private final Comparator<T> comparator;
    private ArrayList<T> list;

    public ShakerSort(Comparator<T> comparator, ArrayList<T> list) {
        this.comparator = comparator;
        this.list = list;
    }

    public ArrayList<T> sort(){
        boolean sorted = false;
        int startIndex = 0;
        int endIndex = list.size();

        while(!sorted){

            sorted = true;

            for(int i=startIndex; i<endIndex-1; i++) {
                if (comparator.compare(list.get(i), list.get(i + 1)) > 0) {
                    Collections.swap(list, i, i + 1);
                    sorted = false;
                }
            }
                if(sorted) return list;
                endIndex--;
                sorted = true;

                for(int j = endIndex-1; j>=startIndex; j--){
                    if(comparator.compare(list.get(j), list.get(j+1))>0){
                        Collections.swap(list, j, j+1);
                        sorted = false;
                    }
                }

                if(sorted) return list;
                startIndex++;
            }
        return list;
        }


}
