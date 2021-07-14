import java.util.ArrayList;
import java.util.Random;

public class Assignment8 {
    public static void main(String[] args){
        Random random = new Random();

        ArrayList<Integer> values = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            values.add(random.nextInt(100));
        }

        PriorityQueue<Integer> heap = new PriorityQueue<>(values);

        System.out.println("HeapSort");
        while (heap.size() > 0){
            System.out.print(heap.deleteMin() + ",");
        }
        System.out.println("HeapSort end");
        
        // Create SchedulersSS
        // FIFO fifo = new FIFO();
        RR rr10 = new RR(10);
        RR rr15 = new RR(15);
        RR rr20 = new RR(20);
        RR rr25 = new RR(25);
        RR rr30 = new RR(30);
        SJF sjf = new SJF();

        AverageTurnAround calculator = new AverageTurnAround(100);
        System.out.println("Printing Average Job Times:");
        // System.out.println(calculator.getAverage(fifo));
        System.out.println(calculator.getAverage(rr10));
        System.out.println(calculator.getAverage(rr15));
        System.out.println(calculator.getAverage(rr20));
        System.out.println(calculator.getAverage(rr25));
        System.out.println(calculator.getAverage(rr30));
        System.out.println(calculator.getAverage(sjf));
    }
}

class PriorityQueue<T extends Comparable<T>>{
    private ArrayList<T> innerArray;
    private final int d = 3;
    PriorityQueue(){
        innerArray = new ArrayList<>();
    }
    PriorityQueue(ArrayList<T> array){
        innerArray = new ArrayList<>();
        innerArray.addAll(array);
        heapify();
    }
    public int size() {
        return innerArray.size();
    }
    
    public void insert(T x) {
        innerArray.add(null);
        swim(x, innerArray.size() - 1);
    }

    public T deleteMin() {
        T root = innerArray.get(0);
        T lastValue = innerArray.get(innerArray.size() - 1);
        // innerArray.set(innerArray.size() - 1, null);
        innerArray.remove(innerArray.size() - 1);
        if (innerArray.size() > 0) {
            sink(lastValue, 0);
        }
        return root;
    }

    private int getParent(int i) {
        return i == 0 ? -1 : (i - 1) / d;
    }
    
    private int getChild(int i, int k) {
        return d * i + k + 1;
    }
    
    private int getMinChild(int i) {
        int firstChild = getChild(i, 0);
        if (firstChild >= innerArray.size()) {
            return -1;
        }
        int minChild = firstChild;

        int maxIndex = Math.min(firstChild + d, innerArray.size());
        for (int j = firstChild + 1; j < maxIndex; j++) {
            // if minChild > jth child
            if (innerArray.get(minChild).compareTo(innerArray.get(j)) > 0) {
                minChild = j;
            }
        }
        return minChild;
    }
    private void sink(T x, int i){
        int child = getMinChild(i);
        while (child > -1 && innerArray.get(child).compareTo(x) < 0){
            innerArray.set(i, innerArray.get(child));
            i = child;
            child = getMinChild(i);
        }
        innerArray.set(i, x);
    }
    
    private void swim(T x, int i) {
        int parent = getParent(i);
        while (parent > -1 && innerArray.get(parent).compareTo(x) > 0) {
            innerArray.set(i, innerArray.get(parent));
            i = parent;
            parent = getParent(i);
        }
        innerArray.set(i, x);
    }
    
    public void heapify() {
        for (int i = getParent(innerArray.size()) - 1; i > -1; i--) {
            sink(innerArray.get(i), i);
        }
    }
}

interface Scheduler{
    void populate(ArrayList<Integer> array);
    int nextJob();
    boolean isEmpty();
}
class FIFO implements Scheduler{
    ArrayList<Integer> innerArray;
    public void populate(ArrayList<Integer> array){
        innerArray = new ArrayList<>();
        innerArray.addAll(array);
    }
    public int nextJob(){
        int nextJob = innerArray.get(0);
        innerArray.remove(0);
        return nextJob;
    }
    public boolean isEmpty(){
        return innerArray.isEmpty();
    }
}

class RR implements Scheduler {
    ArrayList<Integer> innerArray;
    int timeSlice;
    RR(int timeSlice){
        this.timeSlice = timeSlice;
    }
    public void populate(ArrayList<Integer> array) {
        innerArray = new ArrayList<>();
        innerArray.addAll(array);
    }

    public int nextJob() {
        int nextJob = innerArray.get(0);
        if (nextJob <= timeSlice)
            innerArray.remove(0);
        else{
            innerArray.remove(0);
            innerArray.add(nextJob - timeSlice);
        }
        return nextJob;
    }

    public boolean isEmpty() {
        return innerArray.isEmpty();
    }
}

class SJF implements Scheduler {
    PriorityQueue<Integer> innerQueue;

    public void populate(ArrayList<Integer> array) {
        ArrayList<Integer> myArray = new ArrayList<>();
        myArray.addAll(array);
        innerQueue = new PriorityQueue<>(myArray);
    }

    public int nextJob() {
        return innerQueue.deleteMin();
    }
    public boolean isEmpty(){
        return innerQueue.size() <= 0;
    }
}

class AverageTurnAround{
    ArrayList<Integer> jobs;
    final int n;
    int sumTimes;
    int timeSpent;
    Random random = new Random();
    AverageTurnAround(int n){
        jobs = new ArrayList<>();
        this.n = n;
        for (int i=0; i<n; i++){
            jobs.add((random.nextInt(101)));
        }
    }
    double getAverage(Scheduler scheduler){
        scheduler.populate(jobs);
        timeSpent = 0;
        sumTimes = 0;
        while (!scheduler.isEmpty()){
            int job = scheduler.nextJob();
            timeSpent += job;
            sumTimes += timeSpent;
        }
        return sumTimes / (double) n;
    }
}