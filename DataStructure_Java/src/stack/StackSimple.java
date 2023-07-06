package stack;

import java.util.Arrays;

public class StackSimple {
    // This implementation of Stack uses array. Therefore, instance of this class is 'input type-sensitive'
    int size;
    int top; // Top refers to the index of end item.
    int[] stack;
    public StackSimple(int size){
        this.size = size;
        this.top = -1;
        stack = new int[size];
    }

    public boolean isEmpty(){
        if (this.top == -1){
            return true;
        }
        else {
            return false;
        }
    }
    public void push(int x) {
        if (top == size-1){
            System.out.println("Stack is full.");
        }
        else {
            this.top++;
            stack[this.top] = x;

        }
    }

    public void pop(){
        if (this.isEmpty()) {
            System.out.println("pop() cannot be processed. : Stack is Empty.");
        }
        else {
            this.stack[this.top] = 0; // I set last item as '0' for convenience. If the input type is set to be Object, then the last item should be set 'null.
            this.top--;
        }
    }

    public int peek() {
        if (this.isEmpty()){
            System.out.println("Stack is Empty.");
            return 0;
        }
        else {
            return this.stack[this.top];
        }
    }

    public int size() {
        if(this.isEmpty()) {
            return 0;
        }
        else {
            return this.top + 1;
        }
    }
 public static void main(String[] args) {
        StackSimple s = new StackSimple(5);
        s.push(0);
        s.push(1);
        s.push(5);
        s.push(6);
        s.pop();
        s.peek();
        s.size();
        s.isEmpty();
        System.out.println("Current Stack: " + Arrays.toString(s.stack));
        s.pop();
        s.pop();
        s.isEmpty();
        s.size();
     System.out.println("Current Stack: " + Arrays.toString(s.stack));
 }
}
