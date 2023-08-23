import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BubbleSort<T> {
    private final Comparator<T> comparator;
    private ArrayList<T> list;

    public BubbleSort(Comparator<T> comparator, ArrayList<T> list) {
        this.comparator = comparator;
        this.list = list;
    }

    public ArrayList<T> sort(){
        int length = list.size();
        for(int i=0; i<length-1; i++){
            for(int j=0; j<length-i-1; j++){
                if(comparator.compare(list.get(j), list.get(j+1))>0){
                    Collections.swap(list, j, j+1);
                }
            }

            for(int m=0; m<length; m++){
                System.out.print(list.get(m) + " ");
            }
            System.out.println();
        }
        return list;
    }
}
