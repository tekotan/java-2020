import java.util.ArrayList;
import java.util.Iterator;

public class Cereal {
    public static void main(String[] args){
        System.out.println("Test One");
        String testOne = "kellogs,  c, 10";
        RowData rowOne = new RowData(testOne, 1);
        System.out.println(rowOne.index(0));
        for (String s : rowOne){
            System.out.println(s);
        }
        System.out.println(rowOne);

        System.out.println("Test Two");
        String testTwo = "cranberry cereal,  h, 20,34,2.32";
        RowData rowTwo = new RowData(testTwo, 2);
        System.out.println(rowTwo.index(0));
        for (String s : rowTwo) {
            System.out.println(s);
        }
        System.out.println(rowTwo);

    }

}
class RowData implements Iterable<String>{
    ArrayList<String> data;
    int numData;
    int rowNumber;
    public RowData(String csvRow, int rowNumber){
        data = new ArrayList<>();
        String[] dataSplit = csvRow.split(" *, *");
        int i = 0;
        for (String s : dataSplit){
            data.add(s);
            i++;
        }
        numData = i;
        this.rowNumber = rowNumber;
    }
    public Iterator<String> iterator(){
        return this.data.iterator();
    }

    public int getNumData(){
        return numData;
    }
    public String index(int index){
        return data.get(index);
    }
    public String toString(){
        return String.format("Row %s data, data: %s", rowNumber, String.join(", ", data));
    }
}