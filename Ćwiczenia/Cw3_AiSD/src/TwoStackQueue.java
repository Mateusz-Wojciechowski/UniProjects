import java.util.Stack;

public class TwoStackQueue<T> extends Stack<T> {

    // dokladamy na koniec (enqueue) wyciagamy z poczatku (dequeue)
     Stack<T> stack1;
     Stack<T> stack2;

     public TwoStackQueue(){
         stack1 = new Stack<>();
         stack2 = new Stack<>();
     }
    public void enQueue(T x){
        while(!stack1.isEmpty()){
            stack2.push(stack1.pop());
        }

        stack1.push(x);

        while(!stack2.isEmpty()){
            stack1.push(stack2.pop());
        }
    }

    public void deQueue(){

        if(stack1.isEmpty()){
            System.out.println("Queue is empty");
            return;
        }

        while(!stack1.isEmpty()){
            stack2.push(stack1.pop());
        }

        stack2.pop();

        while(!stack2.isEmpty()){
            stack1.push(stack2.pop());
        }
    }

    public static void twoStackQueueDemo(){
        TwoStackQueue<Integer> twoStackQueue = new TwoStackQueue<>();
        twoStackQueue.stack1.push(1);
        twoStackQueue.stack1.push(2);
        twoStackQueue.stack1.push(3);
        twoStackQueue.stack1.push(4);
        twoStackQueue.stack1.push(5);

        twoStackQueue.deQueue();

        System.out.println(twoStackQueue.stack1);

        twoStackQueue.enQueue(9);

        System.out.println(twoStackQueue.stack1);
    }
}
