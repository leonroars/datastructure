import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class CTTest{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        int idx = Integer.parseInt(br.readLine());

        System.out.printf("%s", s.charAt(idx));
    }
}