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
//import java.io.IOException;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//
//public class Main{
//    public static void main(String[] args) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        int N = Integer.parseInt(br.readLine());
//
//        String[] title = new String[20];
//        title[1] = "666";
//        for(int i = 2; i < 20; i++){
//
//        }
//    }
//}
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.StringTokenizer;

public class Main{

    static class Z {
        private static int[] cMove = new int[]{1, -1, 1};
        private static int[] rMove = new int[]{0, 1, 0};

        private static int r;
        private static int c;

        public Z(int r, int c){
            this.r = r;
            this.c = c;
        }

        static int solution(int rStart, int cStart, int conorstone, int N){
            // Base-Case: N = 1. 찾고자 하는 r행 c열을 포함한 2*2 타일까지 좁힌 후 시작점으로부터 문제에 정의된 움직임대로 움직이며 순차탐색.
            // 최대 네 번의 탐색을 실시한다.
            if(N == 1){
                int rNext = rStart;
                int cNext = cStart;
                if(rNext == r && cNext == c){return conorstone;}
                for(int i = 0; i < 3; i++){
                    cNext += cMove[i]; // 열 이동(가로 방향 이동)
                    rNext += rMove[i]; // 행 이동(세로 방향 이동)
                    conorstone ++;
                    if(rNext == r && cNext == c){break;}
                }
                return conorstone;
            }

            int rNewStart = (r >= rStart + (1 << N-1)) ? rStart + (1 << N-1) : rStart;
            int cNewStart = (c >= cStart + (1 << N-1)) ? cStart + (1 << N-1) : cStart;

            // r행 c열이 1사분면 혹은 4사분면에 존재하는 경우
            if(rNewStart == cNewStart){
                // 4사분면 탐색
                if(rNewStart == rStart + (1 << N-1)){
                    return solution(rNewStart, cNewStart, conorstone + ((1 << ((2 * N) - 2)) * 3), N-1);
                }
                // 1사분면 탐색
                else {
                    return solution(rNewStart, cNewStart, conorstone, N-1);
                }
            }

            // r행 c열이 2사분면 혹은 3사분면에 존재하는 경우
            // 3사분면 탐색
            if(rNewStart == rStart + (1 << N - 1)){return solution(rNewStart, cNewStart, conorstone + ((1 << ((2 * N) - 2)) * 2), N-1);}

            // 2사분면 탐색
            return solution(rNewStart, cNewStart, conorstone + (1 << (2 * N) - 2), N - 1);
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int r = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        Z board = new Z(r, c);
        int result = board.solution(0, 0, 0, N);
        System.out.println(result);
    }
}