import java.util.Objects;

public class HashFunctionTest {
    public static void main(String[] args){
        int a = -89;

        System.out.println(Integer.valueOf(a));


        // 1111 1111 1111 1111 1111 1111 1110 0001
        // 0000 0000 0000 0000 0000 0000 0001 1111
        // 1111 1111 1111 1111 1111 1111 1110 0000
        System.out.println(Integer.toBinaryString(a));
//        Double dnum1 = Double.valueOf(1.003f);
//        Double dnum2 = Double.valueOf(1.0054f);
//
//        // Objects.hashCode() : Signed
//        int dnum1oshC = Objects.hashCode(dnum1);
//        int dnum2oshC = Objects.hashCode(dnum2);
//
//        Integer inum1 = 1000089927;
//        Integer inum2 = 36;
//
//        int inum1osh = Objects.hash(inum1);
//        int inum2osh = Objects.hash(inum2);
//
//
//
//        System.out.println(dnum1oshC);
//        System.out.println(Integer.toBinaryString(dnum1oshC));
//        System.out.println(dnum2oshC);
//        System.out.println(Integer.toBinaryString(dnum2oshC));
//
//
//
//        System.out.println(inum1osh);
//        System.out.println(Integer.toBinaryString(inum1osh));
//        System.out.println(inum2osh);
//        System.out.println(Integer.toBinaryString(inum2osh));
//
//        inum2osh = inum2osh ^ (inum2osh >>> 16);
//        System.out.println(inum2osh);
//        System.out.println(Integer.toBinaryString(inum2osh));

//
//        dnum1oshC = (dnum1oshC ^ (dnum1oshC >>> 16)) & 63;
//        System.out.println(inum1osh);
//        System.out.println(Integer.toBinaryString(inum1osh));
//
//        System.out.printf("index : %d", dnum1oshC % 41);



    }
}
