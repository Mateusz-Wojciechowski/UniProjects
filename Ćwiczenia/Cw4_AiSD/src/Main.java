import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    // zrobic metode robiaca deep copy listy
    public static void main(String[] args) {
       IntegerComparator comparator = new IntegerComparator();
       InsertSort<Integer> insertSort = new InsertSort<>(comparator);
       List<Integer> list = new ArrayList<>();
       SelectionSort<Integer> selectionSort = new SelectionSort<>(comparator, list);

       Collections.addAll(list, 76, 71, 5, 57);
        System.out.println(selectionSort.sortIT());
//       System.out.println(insertSort.sortIT(list));

//       list.clear();
//       Collections.addAll(list, 76, 71, 5, 57, 12, 50, 20, 93, 20, 55, 62, 3);
//       SelectionSort<Integer> selectionSort = new SelectionSort<>(comparator, list);
//       System.out.println(selectionSort.sort());

//       list.clear();
//       Collections.addAll(list, 76, 71, 5, 57, 12, 50, 20, 93, 20, 55, 62, 3);
//       BubbleSort<Integer> bubbleSort = new BubbleSort<>(comparator, list);
//       System.out.println(bubbleSort.sort());

//       list.clear();
//       Collections.addAll(list, 76, 71, 5, 57, 12, 50, 20, 93, 20, 55, 62, 3);
//       HeapSort<Integer> heapSort = new HeapSort<>(comparator, list);
//       System.out.println(heapSort.heapSort());

//       list.clear();
//       Collections.addAll(list, 76, 71, 5, 57, 12, 50, 20, 93, 20, 55, 62, 3);
//       ShakerSort<Integer> shakerSort = new ShakerSort<>(comparator, list);
//       System.out.println(shakerSort.sort());

    }
}