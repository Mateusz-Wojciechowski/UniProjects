import java.util.Stack;

public class ReverseStack <T> extends Stack<T> {

    public void reverse(){
        Stack<T> tmpStack = new Stack<>();
        while(!this.isEmpty()){
            tmpStack.push(this.pop());
        }

        Stack<T> tmpStack2 = new Stack<>();

        while(!tmpStack.isEmpty()){
            tmpStack2.push(tmpStack.pop());
        }

        while(!tmpStack2.isEmpty()){
            this.push(tmpStack2.pop());
        }

    }

    public static void reverseStackDemo(){
        ReverseStack<Integer> reverseStack = new ReverseStack<>();

        reverseStack.push(1);
        reverseStack.push(2);
        reverseStack.push(3);
        reverseStack.push(4);

        reverseStack.reverse();

        System.out.println(reverseStack);
    }
}
