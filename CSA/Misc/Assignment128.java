public class Assignment128 {
    public static void main(String[] args){
        // Integer constructor and intValue
        Integer myInt = new Integer(10);
        int myint = myInt.intValue();
        // Double constructor and doubleValue
        Double myDouble = new Double(10.0);
        double mydouble = myDouble.doubleValue();
        double maxValue = Double.MAX_VALUE;
        double minValue = Double.MIN_VALUE;
        System.out.println(maxValue + " " + minValue);
        Double autoBox = mydouble;
        int unbox = myInt;
    }
}
