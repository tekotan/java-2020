public class Assignment3 {
    public static void main(String[] args){
        Deque<Character> deque = new Deque<>();
        deque.insertLast('C');
        deque.insertLast('O');
        deque.insertLast('M');
        deque.insertLast('P');
        deque.insertLast('U');
        deque.insertLast('T');
        deque.insertLast('E');
        deque.displayForward();
        deque.insertFirst('M');
        deque.insertLast('R');
        deque.removeFirst();
        deque.removeLast();
        deque.insertBefore('M', 'P');
        deque.insertBefore('H', 'M');
        deque.insertBefore('B', 'A');
        deque.insertAfter('C', 'P');
        deque.insertAfter('L', 'M');
        deque.removeNode('M');
        deque.removeNode('G');
        deque.moveToFront('P');
        deque.moveToBack('L');
        deque.displayForward();
    }
}

class Deque<T>{
    private DoubleNode<T> head;
    private DoubleNode<T> tail;

    static class DoubleNode<T>{
        T i;
        DoubleNode<T> next;
        DoubleNode<T> prev;
        DoubleNode(T i){
            this.i = i;
        }
        
        public void displayData() {
            System.out.print(i + " ");
        }

    }
    Deque(){
        this.head = null;
        this.tail = null;
    }
    
    boolean isEmpty() {
        return head == null;
    }
    
    void insertFirst(T i) {
        // Create a new node
        DoubleNode<T> newNode = new DoubleNode<>(i);
        // if first insertion tail should
        // also point to this node
        if (isEmpty()) {
            tail = newNode;
        } else {
            head.prev = newNode;
        }
        newNode.next = head;
        head = newNode;
    }
    
    void insertLast(T i) {
        DoubleNode<T> newNode = new DoubleNode<>(i);
        // if first insertion head should
        // also point to this node
        if (isEmpty()) {
            head = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
        }
        tail = newNode;
    }
    
    void removeFirst() {
        if (head == null) {
            throw new RuntimeException("Deque is empty");
        }
        if (head.next == null) {
            tail = null;
        } else {
            // previous of next node (new first) becomes null
            head.next.prev = null;
        }
        head = head.next;
    }

    void removeLast() {
        if (tail == null) {
            throw new RuntimeException("Deque is empty");
        }
        if (head.next == null) {
            head = null;
        } else {
            // next of previous node (new last) becomes null
            tail.prev.next = null;
        }
        tail = tail.prev;
    }

    T getFirst() {
        if (isEmpty()) {
            throw new RuntimeException("Deque is empty");
        }
        return head.i;
    }

    T getLast() {
        if (isEmpty()) {
            throw new RuntimeException("Deque is empty");
        }
        return tail.i;
    }

    // Method for forward traversal
    void displayForward() {
        DoubleNode<T> current = head;
        while (current != null) {
            current.displayData();
            current = current.next;
        }
        System.out.println("");
    }

    // Method to traverse and display all nodes
    void displayBackward() {
        DoubleNode<T> current = tail;
        while (current != null) {
            current.displayData();
            current = current.prev;
        }
        System.out.println("");
    }
    void insertAfter(T data, T after){
        DoubleNode<T> current = head;
        while (current != null){
            if (current.i == after){
                DoubleNode<T> newData = new DoubleNode<>(data);
                newData.next = current.next;
                current.next = newData;
                newData.prev = current;
                if (newData.next != null)
                    newData.next.prev = newData;
                return;
            } else {
                current = current.next;
            }
        }
        insertLast(data);
    }

    void insertBefore(T data, T before){
        DoubleNode<T> current = head;
        while (current != null){
            if (current.i == before){
                DoubleNode<T> newData = new DoubleNode<>(data);
                newData.prev = current.prev;
                newData.next = current;
                current.prev = newData;
                if (newData.prev != null){
                    newData.prev.next = newData;
                } else {
                    head = newData;
                }
                return;
            } else {
                current = current.next;
            }
        }
        insertLast(data);
    }
    
    void removeNode(T node) {
        DoubleNode<T> current = head;
        while (current != null) {
            if (current.i == node) {
                current.prev.next = current.next;
                return;
            } else {
                current = current.next;
            }
        }
    }
    
    void moveToFront(T node) {
        DoubleNode<T> current = head;
        while (current != null) {
            if (current.i == node) {
                current.prev.next = current.next;
                head.prev = current;
                current.prev = null;
                current.next = head;
                head = current;
                return;
            } else {
                current = current.next;
            }
        }
    }
    
    void moveToBack(T node) {
        DoubleNode<T> current = head;
        while (current != null) {
            if (current.i == node) {
                DoubleNode<T> temp = current;
                current.prev.next = current.next;
                tail.next = temp;
                temp.prev = tail;
                tail = tail.next;
                tail.next = null;
                return;
            } else {
                current = current.next;
            }
        }
    }
}