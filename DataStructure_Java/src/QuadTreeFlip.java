public class QuadTreeFlip {
    private Character[] qt;

    public QuadTreeFlip(String input){
        qt = new Character[input.length()];
        for(int i = 0; i < input.length(); i++){
            qt[i] = input.charAt(i);
        }
    }

    public void flip(int start, int end){
        int recursionCount = 0;
        int clear = 0; // The number of quartiles before another division.

        int currentStart = start;
        int currentEnd = end;

        for(int i = 1; i < end + 1; i++){
            if(recursionCount == 0 && (qt[0] == 'b' || qt[i] == 'w')){return;}
            if(qt[i] == 'x'){
                // 바꾸기를 어떻게 구현할 것인가? substring이라면 간결할텐데,,!?
                if(clear == 0){recursionCount++; flip(i+1, end);}
                if(clear != 0){recursionCount++; flip(i+1, end + (clear - 3));}

            }
            else{clear++;}
        }


    }

}
