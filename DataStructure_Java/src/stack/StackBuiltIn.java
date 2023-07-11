package stack;
import java.util.Stack;

/**
 * {@code java.util.Stack} is implements Vector<E> class. This provides following features.
 * <p> - Thread Safety : Vector<E> class is synchronized. So it is thread-safe. But thus has inferior performance compare to ArrayList.
 * <p> - Like ArrayList, Vector<E> is also resizable. - I got a bit of idea from inner resizing logic of Vector<E> implementing my own ResizableArrayStack and RAQ.
 * <p> - Generic Class
 */

public class StackBuiltIn {
    public static void main(String[] args){
        Stack<Integer> stack = new Stack<>();

        stack.push(10);
        stack.push(20);
        stack.push(39);

        System.out.printf("1. Popped Item: %d\n", stack.pop());
        System.out.printf("2. Popped Item: %d\n", stack.pop());
        System.out.printf("Peek(): %d\n", stack.peek());
        System.out.printf("Current size: %d\n", stack.size());
        System.out.printf("3. Popped Item: %d\n", stack.pop());
        System.out.printf("Is Empty?: %b\n", stack.isEmpty());

    }
}
