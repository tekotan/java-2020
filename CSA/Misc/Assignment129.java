public class Assignment129 {
    public static void main(String[] args){

    }
}
class Annuity{
    public double getPresentValue(int pmt, int i, int n){
        double decimal = i / 100.0;
        double pv = pmt * (1 - Math.pow(1+decimal, n * -1)) / decimal;
        return pv;
    }
}