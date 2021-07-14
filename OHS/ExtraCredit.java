import java.util.LinkedList;
import java.util.Random;
import java.util.logging.*;
import java.io.IOException;
public class ExtraCredit {
    public static void main(String[] args){
        Random rand = new Random();
        WriteLogToFile logger = new WriteLogToFile();
        BinGetter getter = new BinGetter(200, logger);
        // filling up bins
        logger.log("Filling up bins");
        for(int i=0; i < 10; i++){
            getter.allocate(rand.nextInt(11));
            logger.log("Adding " + i);
            getter.print();
        }
        // randomly removing some bins
        logger.log("Randomly removing some bins");
        for (int i = 0; i < 5; i++) {
            getter.deallocate((double) rand.nextInt(11));
            logger.log("Removing " + i);
            getter.print();
        }
        logger.log("Adding random bins");
        for (int i = 0; i < 20; i++) {
            getter.allocate(rand.nextInt(11));
            logger.log("Adding " + i);
            getter.print();
        }
        logger.log("Removing random bins");
        for (int i = 0; i < 10; i++) {
            getter.allocate(rand.nextInt(11));
            logger.log("Removing " + i);
            getter.print();
        }
    }
}

class BinGetter{
    LinkedList<Pair<Integer, Integer>> allocated;
    LinkedList<Pair<Integer, Integer>> free;
    int[] memory;
    int numBins;
    WriteLogToFile logger;
    BinGetter(int numBins, WriteLogToFile logger){
        memory = new int[numBins];
        for (int i=0; i < numBins; i++)
            memory[i] = 0;
        free = new LinkedList<>();
        allocated = new LinkedList<>();
        free.add(new Pair<Integer, Integer>(0, numBins));
        this.numBins = numBins;
        this.logger = logger;
    }
    void allocate(int space){
        int minSpace = this.numBins;
        for (Pair<Integer, Integer> i : free) {
            if (i.second > space && i.second < minSpace) {
                minSpace = i.second;
            }
        }
        int startIndex = -1;
        for (int i=0; i < free.size(); i++) {
            if (free.get(i).second == minSpace) {
                startIndex = free.get(i).first;
                free.remove(i);
                break;
            }
        }
        if (startIndex == -1){
            logger.log("no space");
            return;
        }
        allocated.add(new Pair<>(startIndex, space));
        for (int i=startIndex; i<startIndex + space; i++){
            memory[i] = 1;
        }
        if (minSpace > space){
            free.add(new Pair<>(startIndex + space, minSpace - space));
        }
    }
    
    void deallocate(int startIndex) {
        int space = -1;
        for (int i = 0; i < allocated.size(); i++) {
            if (allocated.get(i).first == startIndex) {
                space = allocated.get(i).second;
                allocated.remove(i);
                break;
            }
        }
        if (space == -1) {
            logger.log("not found");
            return;
        }
        int totalSpace = space;
        int freeStartIndex = startIndex;
        int removeOne=-1;
        int removeTwo=-1;
        for (int i = 0; i < free.size(); i++) {
            if (free.get(i).first == startIndex + space) {
                totalSpace += free.get(i).second;
                removeOne = i;
            }
            if (free.get(i).first + free.get(i).second == startIndex) {
                freeStartIndex = free.get(i).first;
                totalSpace += free.get(i).second;
                removeTwo = i;
            }
        }
        if (removeOne != -1)
            free.remove(removeOne);
        if (removeTwo != -1)
            free.remove(removeTwo);
        for (int i=startIndex; i<startIndex + space; i++){
            memory[i] = 0;
        }
        free.add(new Pair<>(freeStartIndex, totalSpace));
    }
    
    void deallocate(double _space) {
        int space = (int) _space;
        int startIndex = -1;
        for (int i = 0; i < allocated.size(); i++) {
            if (allocated.get(i).second == space) {
                startIndex = allocated.get(i).first;
                allocated.remove(i);
                break;
            }
        }
        if (startIndex == -1) {
            logger.log("not found");
            return;
        }
        int totalSpace = space;
        int freeStartIndex = startIndex;
        int removeOne = -1;
        int removeTwo = -1;
        for (int i = 0; i < free.size(); i++) {
            if (free.get(i).first == startIndex + space) {
                totalSpace += free.get(i).second;
                removeOne = i;
            }
            if (free.get(i).first + free.get(i).second == startIndex) {
                freeStartIndex = free.get(i).first;
                totalSpace += free.get(i).second;
                removeTwo = i;
            }
        }
        if (removeOne != -1)
            free.remove(removeOne);
        if (removeTwo != -1)
            free.remove(removeTwo);
        for (int i = startIndex; i < startIndex + space; i++) {
            memory[i] = 0;
        }
        free.add(new Pair<>(freeStartIndex, totalSpace));
    }
    void print(){
        logger.log("Allocated:");
        String msg = "";
        for (Pair<Integer, Integer> i: allocated){
            msg += i + "-->";
        }
        logger.log(msg);
        msg = "";
        logger.log("\nFree:");
        for (Pair<Integer, Integer> i : free) {
            msg += (i + "-->");
        }
        logger.log(msg);
        msg = "";
        logger.log("\nMemory Bins: ");
        for (int i=0; i<this.numBins; i++) {
            msg += (memory[i] + "|");
        }
        logger.log(msg);
    }
}
class Pair<First, Second>{
    First first;
    Second second;
    Pair(First first, Second second){
        this.first = first;
        this.second = second;
    }
    public String toString(){
        return "(" + first + ", " + second + ")";
    }
}

class WriteLogToFile {
    Logger logger = Logger.getLogger(WriteLogToFile.class.getName());
    WriteLogToFile(){
        FileHandler fileHandler;
        try{
            fileHandler = new FileHandler("app.log", true);
            logger.addHandler(fileHandler);
        }
        catch (IOException e){}
    }
    public void log(String msg){
        logger.info(msg);
    }
}


/*
 * Algorithm Efficiency and Complexity:
 * 
 * Allocate: Worst case sizes of allocated and free are N, where N is the number
 * of memory boxes. Thus, there are two for loops of size N, and worst case N
 * for filling up the according memory locations Overall the worst case
 * complexity is 3N.
 * 
 * De-Allocate: Worst case sizes of allocated and free are N, where N is the number
 * of memory boxes. Thus, there are two for loops of size N, and worst case N
 * for filling up the according memory locations Overall the worst case
 * complexity is 3N.
 * 
 * Linear complexity is the best case efficiency for this sort of memory 
 * allocations because each bin needs to be checked to find the best fit slot.
 */