import java.util.Iterator;

public class FibonacciIterator implements Iterator{
    private int amount;

    private int tmp;

    private int previousElement1 = 1;

    private int previousElement2 = 1;

    private int pos = 1;
    public FibonacciIterator(int amount){
        this.amount = amount;
    }

    @Override
    public boolean hasNext() {
        return pos <= amount;
    }

    @Override
    public Integer next() {

        if(hasNext()){
            if(pos == 1){
                pos++;
                return previousElement1;
            }
            if(pos == 2){
                pos++;
                return previousElement2;
            }

            if(pos>=2){
                tmp = previousElement1 + previousElement2;
                previousElement1 = previousElement2;
                previousElement2 = tmp;
                pos++;
            }
        }
        return tmp;
    }

    public static void fibonacci(){
        FibonacciIterator fibonacciIterator = new FibonacciIterator(1);

        while(fibonacciIterator.hasNext()){
            System.out.println(fibonacciIterator.next());
        }
    }
}
