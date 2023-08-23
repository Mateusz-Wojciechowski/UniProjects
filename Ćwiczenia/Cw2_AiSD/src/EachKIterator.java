import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class EachKIterator<T> implements Iterator<T> {

    private Iterator<T> iterator;
    private int k;
    private boolean hasNext = true;
    private T elemNext = null;
    public EachKIterator(Iterator<T> iterator, int k){
        this.iterator = iterator;
        this.k = k;

        if(iterator.hasNext()){
            elemNext = iterator.next();
        }
        else {
            hasNext = false;
        }

        if(k<=0) hasNext = false;

    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public T next() throws NoSuchElementException {

        if(hasNext()){
            T tmp = elemNext;
            for(int i=0; i<k; i++){
                if(iterator.hasNext()){
                    elemNext = iterator.next();
                }

                else{
                    hasNext = false;
                }
            }
            return tmp;
        }
        throw new NoSuchElementException("No elements");
    }

    public static void eachK(){
        ArrayList<Integer> arrayList = new ArrayList<>();
        Iterator<Integer> iterator = arrayList.iterator();

        EachKIterator<Integer> eachKIterator = new EachKIterator<>(iterator, 2);

        while(eachKIterator.hasNext()){
            System.out.println(eachKIterator.next());
        }
    }
}
