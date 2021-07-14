public class Assignment7{
    public static void main(String[] args){
        LinkedListSort<Integer> sort = new LinkedListSort<>();
        Node<Integer> tail = new Node<>(3);
        Node<Integer> node4 = new Node<>(2, tail);
        Node<Integer> node3 = new Node<>(1, node4);
        Node<Integer> node2 = new Node<>(3, node3);
        Node<Integer> head = new Node<>(1, node2);
        sort.sort(head);
        Node<Integer> currNode = head;
        while (currNode != null){
            System.out.println(currNode.value);
            currNode = currNode.next;
        }
    }
}
class Node<T>{
    Node<T> next;
    T value;
    Node(){

    }
    Node(T data){
        this.value = data;
    }
    
    Node(T data, Node<T> nextNode) {
        this.value = data;
        this.next = nextNode;
    }
}
class LinkedListSort<T extends Comparable<T>> {
    public Node<T> sort(Node<T> firstNode) {
        if (firstNode.next == null)
            return firstNode;

        Node<T> head = firstNode;
        Node<T> leftNode = head;
        int iterNum = 0;

        while (leftNode != null)
        {
            //Let's start again from the begining
            leftNode = head;
            iterNum = 0;
            Node<T> tailNode = null;

            while (leftNode != null)
            {
                //Let's get the left sublist

                //Let's find the node which devides sublist into two ordered sublists
                Node<T> sentinelNode = getNode(leftNode);
                Node<T> rightNode = sentinelNode.next;

                //If the right node is null it means that we don't have two sublist and the left sublist is ordered already
                //so we just add the rest sublist to the tail
                if (rightNode == null)
                {
                    if (tailNode == null)
                        break;
                    tailNode.next = leftNode;
                    break;
                }

                sentinelNode.next = null;

                //Let's find the node where the right sublist ends
                sentinelNode = getNode(rightNode);
                Node<T> restNode = sentinelNode.next;
                sentinelNode.next = null;

                Node<T> newTailNode = null;

                Node<T> mergedList = merge(leftNode, rightNode, newTailNode);
                if (iterNum == 0)                   
                    head = mergedList;                  
                else               
                    tailNode.next = mergedList;                     

                tailNode = newTailNode;
                leftNode = restNode;
                iterNum++;
            }
            if (iterNum == 0)
                break;
        }
        return head;
    }

    private Node<T> merge(Node<T> leftNode, Node<T> rightNode, Node<T> tailNode){
        Node<T> dummyHead = new Node<T>();
        Node<T> curNode = dummyHead;

        while (leftNode != null || rightNode != null)
        {
            if (rightNode == null)
            {
                curNode.next = leftNode;
                leftNode = leftNode.next;
            }
            else if (leftNode == null)
            {
                curNode.next = rightNode;
                rightNode = rightNode.next;
            }
            else if (leftNode.value.compareTo(rightNode.value) <= 0) {
                curNode.next = leftNode;
                leftNode = leftNode.next;
            } else {
                curNode.next = rightNode;
                rightNode = rightNode.next;
            }
            curNode = curNode.next;
        }
        tailNode = curNode;
        return dummyHead.next;
    }

    private Node<T> getNode(Node<T> firstNode) {
        Node<T> curNode = firstNode;

        while (curNode != null && curNode.next != null && curNode.value.compareTo(curNode.next.value) <= 0)
            curNode = curNode.next;

        return curNode;
    }
}
