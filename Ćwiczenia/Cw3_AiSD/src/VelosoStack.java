import java.util.EmptyStackException;
import java.util.Stack;

public class VelosoStack<T> extends Stack<T> {

    private int currentIndex;
    public VelosoStack() {
         this.currentIndex=0;
    }

    public void top() throws EmptyStackException{
        if(this.isEmpty()) throw new EmptyStackException();
        else currentIndex = this.size()-1;
    }

    public void down() throws BottomStackExeption{
        if(currentIndex==0) throw new BottomStackExeption();

        else currentIndex-=1;
    }

    @Override
    public T push(T item) {
        currentIndex = this.size();
        return super.push(item);
    }

    @Override
    public synchronized T pop() throws EmptyStackException {
        if(this.size()==0) throw new EmptyStackException();
        else currentIndex=this.size()-2;
        return super.pop();
    }

    @Override
    public synchronized T peek() throws EmptyStackException {
        if(this.isEmpty()) throw new EmptyStackException();

        return this.get(currentIndex);
    }

    public static void velsoStackDemo() throws BottomStackExeption{
        VelosoStack<Integer> velosoStack = new VelosoStack<>();
        velosoStack.push(1);
        velosoStack.push(2);
        velosoStack.push(3);
        velosoStack.push(4);
        velosoStack.push(5);

        System.out.println(velosoStack.peek());
        velosoStack.down();
        velosoStack.down();
        System.out.println(velosoStack.peek());
        velosoStack.top();
        System.out.println(velosoStack.peek());
        velosoStack.pop();
        System.out.println(velosoStack.peek());
    }
}
