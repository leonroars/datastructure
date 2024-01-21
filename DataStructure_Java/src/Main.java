import java.util.LinkedList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        String[] strArr = br.readLine().split(" ");
        LinkedList<String> stk = new LinkedList<>(Arrays.asList(strArr));
        LinkedList<String> wait = new LinkedList<>();
        int current = 1;

        while(!stk.isEmpty()){
            String turn = stk.peekFirst();
            if(Integer.parseInt(turn) == current){
                stk.removeFirst();
                current++;
            } else {
                if(!wait.isEmpty() && Integer.parseInt(wait.peekLast()) == current){
                    wait.removeLast();
                    current++;
                } else {
                    wait.addLast(stk.removeFirst());
                }
            }
        }

        for(int i = current; i < N+1; i++){
            String check = wait.removeLast();
            if(Integer.parseInt(check) != i){
                System.out.println("Sad");
                break;
            }
        }
        if(wait.isEmpty()){System.out.println("Nice");}
    }
}
