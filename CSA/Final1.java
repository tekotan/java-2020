import java.util.List;

public class Final1{
    public static void main(String[]args){
        System.out.println(scrambleWord("TAN"));
    }
    public static String scrambleWord(String word){
        String scrambleWord = "";
        for (int k=0; k<word.length(); k++){
            if (word.substring(k, k+1).equals("A") && !word.substring(k + 1, k+2).equals("A")){
                scrambleWord += word.substring(k + 1, k + 2) + word.substring(k, k + 1);
                k++;
            } else {
                scrambleWord += word.substring(k, k + 1);
            }
        }
        return scrambleWord;
    }
    public static void scrambleOrRemove(List<String> wordList){
        for (int k=0; k<wordList.size(); k++){
            String word = wordList.get(k);
            String scrambleWord = scrambleWord(word);
            if (scrambleWord.equals(word)){
                wordList.remove(k);
            } else {
                wordList.set(k, scrambleWord);
            }
        }
    }
}
class Spinner {
    int maxSpin;
    int badNum;
    public Spinner(int m, int b){
        this.maxSpin = m;
        this.badNum = b;
    }
    public Spinner(){
        this.maxSpin = 10;
        this.badNum = 0;
    }
    public int spin(){
        int spin = 1 + (int) (Math.random() * 10);
        if (badNum == 0)
            return spin;
        else{
            if (spin==badNum)
                return 0;
            else if (spin % badNum == 0)
                return -1*spin;
            else
                return spin;
        }
    }
}

class SpinnerGame{
    int maxScore;
    int maxSpinner;
    int badValue;

    public SpinnerGame(int mscore, int mspin, int bval){
        maxScore = mscore;
        maxSpinner = mspin;
        badValue = bval;
    }

    public String playGame(String p1, String p2){
        int p1Score = 0;
        int p2Score = 0;
        int spinVal;
        Spinner s = new Spinner(maxSpinner, badValue);
        while (p1Score < this.maxScore || p2Score < this.maxScore){
            int p1Spin = s.spin();
            int p2Spin = s.spin();
            p1Score = (p1Spin != 0)? p1Spin + p1Score:0;
            p2Score = (p2Spin != 0) ? p2Spin + p2Score : 0;
        }
        if (p1Score == p2Score)
            return "tie";
        else
            return (p1Score > p2Score) ? p1 : p2;
    }
}

