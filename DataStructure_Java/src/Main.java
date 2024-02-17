//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.BufferedReader;
//import java.util.StringTokenizer;
//import java.util.BitSet;
//// 배열 쓰는 건 어때? 저장하고 갱신. 아니면 삭제 요런 식으로?
//public class Main{
//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//        String input = br.readLine();
//        int target = Integer.parseInt(input);
//        int targetLength = input.length();
//
//        int numOfBroken = Integer.parseInt(br.readLine());
//
//        BitSet brokenNums = new BitSet(10);
//        StringTokenizer st = new StringTokenizer(br.readLine());
//
//        while(st.nextToken() != null){
//            brokenNums.set(Integer.parseInt(st.nextToken()));
//        }
//
//        if(target != 100 && numOfBroken != 0){
//            StringBuilder sb = new StringBuilder();
//
//            for(int i = 0; i < targetLength; i++){
//                int current = Character.valueOf(input.charAt(i));
//                if(!brokenNums.get(current)){sb.append(current);}
//                else{
//                    for(int j = 1;;j++){
//                        if(!brokenNums.get(current - j) && !brokenNums.get(current + j)){
//                            if(Character)
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    static StringBuilder decide(StringBuilder ByFar, int target, BitSet brokenNum, int lower, int upper){
//        int lowerDiff = (10 + (lower - target)) % 10;
//        int upperDiff = (10 + (upper - target)) % 10;
//
//        if(target == lower || ((lowerDiff < upperDiff) && !brokenNum.get(lower))){
//            ByFar.append(lower); return ByFar;}
//        if(target == upper || (upperDiff < lowerDiff)){
//            ByFar.append(upper); return ByFar;}
//
//
//    }
//
//}