import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        String s1 = "child";
        Object k = "Parent";
        Object s2 = s1; // Parent p = c : Working
        //String k2 = k; _ Child c = p : Not working. => Violates principle of 'Subtype Polymorphism'.

        Integer[] intArr = {0, 1, 2};
        Object[] objArr = intArr;
        objArr[0] = "s";

        Set<String> strSet = new HashSet<>();
        Set intSet = strSet;







        List<Object> strLst = new ArrayList<>();
        strLst.add("Hello");
        strLst.add(30);

        List rawLst = new ArrayList();
        rawLst.add("hello");
        rawLst.add("I'm Raw Usage of Generic Collection");
        rawLst.add(30);

        System.out.println(rawLst.toString()); // Well Compiled. But what pain point is, when it's time to extract!


        String p = "programmers";
        String[] pArr = p.split("");
        String key = "p";
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < pArr.length; i++){
            if(pArr[i] == key){
                ans.append(pArr[i].toUpperCase());
            } else {
                ans.append(pArr[i]);
            }
        }
        System.out.println(ans);



    }
}
