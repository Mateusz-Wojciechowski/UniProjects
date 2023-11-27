import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ShuffleIterator<T> implements Iterator<T> {

    private Iterator<T> iterator1;
    private Iterator<T> iterator2;
    private boolean hasNext = true;
    private T elemNext;
    private int i = 0;

    public ShuffleIterator(Iterator<T> iterator1, Iterator<T> iterator2){
        this.iterator1 = iterator1;
        this.iterator2 = iterator2;
    }

    @Override
    public T next() throws NoSuchElementException {
        if(hasNext()){
            if(i%2==0 && iterator1.hasNext() || !iterator2.hasNext()){
                elemNext = iterator1.next();
                i++;
                if(!iterator1.hasNext() && !iterator2.hasNext()) hasNext = false;
                return elemNext;
            }

            else{
                elemNext = iterator2.next();
                i++;
                if(!iterator1.hasNext() && !iterator2.hasNext()) hasNext = false;
                return elemNext;
            }
        }
        else throw new NoSuchElementException("Lacking next values");
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    public static void shuffle(){
        ArrayList<Integer> arrayList1 = new ArrayList<>();
        arrayList1.add(1);
        arrayList1.add(2);
        arrayList1.add(3);

        Iterator<Integer> iterator1 = arrayList1.iterator();
        ArrayList<Integer> arrayList2 = new ArrayList<>();
        arrayList2.add(11);
        arrayList2.add(12);
        arrayList2.add(13);
        arrayList2.add(14);
        arrayList2.add(15);

        Iterator<Integer> iterator2 = arrayList2.iterator();

        ShuffleIterator<Integer> shuffleIterator = new ShuffleIterator<>(iterator1, iterator2);

        while(shuffleIterator.hasNext()){
            System.out.println(shuffleIterator.next());
        }
    }
}
