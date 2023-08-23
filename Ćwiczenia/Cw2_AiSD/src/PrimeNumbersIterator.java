import java.util.Iterator;
import java.util.NoSuchElementException;

public class PrimeNumbersIterator implements Iterator {
    final private int range;
    private int currentNum = 1;
    private int tmp;

    public PrimeNumbersIterator(int range){
        this.range = range;
    }

    @Override
    public boolean hasNext() {
        return currentNum < range;
    }

    @Override
    public Integer next() throws NoSuchElementException {
        tmp = currentNum;

        if(hasNext()){
            while(!isPrimeNumber(tmp) && hasNext()){
                currentNum++;
                tmp = currentNum;
            }
            currentNum++;

            return tmp;
        }

        else{
            throw new NoSuchElementException("Lacking next elements");
        }
    }

    private boolean isPrimeNumber(int num){
        if(num==1 || num==4) return false;

        for(int i=2; i<num/2; i++){
            if(num%i==0) return false;
        }
        return true;
    }

    public static void primeIterator(){
        PrimeNumbersIterator primeNumbersIterator = new PrimeNumbersIterator(40);

        while (primeNumbersIterator.hasNext()){
            System.out.println(primeNumbersIterator.next());
        }
    }
}
