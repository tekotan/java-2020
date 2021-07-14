import java.lang.Math;
public class Assignment2 {
    public static void main(String[] args){
        EvaluateInfix expression = new EvaluateInfix("1-(2/4)+(2+1)^2-9");
        double result = expression.evaluate();
        System.out.println(expression.getInfix());
        System.out.println(expression.getPostfix());
        System.out.println(result);
    }
}

class Stack<E> {

    StackNode<E> root;

    static class StackNode<E> {
        E data;
        StackNode<E> next;

        StackNode(E data) {
            this.data = data;
        }
    }

    public boolean isEmpty() {
        if (root == null) {
            return true;
        } else
            return false;
    }

    public void push(E data) {
        StackNode<E> newNode = new StackNode<>(data);

        if (root == null) {
            root = newNode;
        } else {
            StackNode<E> temp = root;
            root = newNode;
            newNode.next = temp;
        }
    }

    public E pop() {
        E popped = null;
        if (root == null) {
        } else {
            popped = root.data;
            root = root.next;
        }
        return popped;
    }

    public E peek() {
        if (root == null) {
            return null;
        } else {
            return root.data;
        }
    }

    public static void main(String[] args) {

        Stack<Integer> stack = new Stack<>();

        stack.push(10);
        stack.push(20);
        stack.push(30);

        System.out.println(stack.pop() + " popped from stack");

        System.out.println("Top element is " + stack.peek());
    }
}

class InfixToPostfixConverter {
    static int precedence(char ch) {
        switch (ch) {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
            default:
                return -1;
        }
        
    }

    static String convert(String expression){
        String returnString = new String("");
        Stack<Character> stack = new Stack<>();

        for (int i=0; i<expression.length(); i++){
            char c = expression.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                returnString += c;
            } else if (c == '('){
                stack.push(c);
            } else if (c == ')'){
                while (!stack.isEmpty() && stack.peek() != '('){
                    returnString += stack.pop();
                }
                if (!stack.isEmpty() && stack.peek() != '('){
                    return "Expression is Invalid";
                } else {
                    stack.pop();
                }
            } else {
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())){
                    if (stack.peek() == '('){
                        return "Expression is Invalid";
                    }
                    returnString += stack.pop();
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()){
            if (stack.peek() == '('){
                return "Expression is Invalid";
            }
            returnString += stack.pop();
        }
        return returnString;
    }
}
class EvaluateInfix{
    String infixExpression;
    String postfixExpression;
    EvaluateInfix(String infixExpression){
        this.infixExpression = infixExpression;
        this.postfixExpression = InfixToPostfixConverter.convert(infixExpression);
    }
    double evaluate(){
        Stack<Double> stack = new Stack<>();
        for (int i=0; i<postfixExpression.length(); i++){
            char c = postfixExpression.charAt(i);
            if (Character.isDigit(c)){
                stack.push((double) Character.getNumericValue(c));
            } else {
                double num1, num2;
                switch (c){
                    case '+':
                        stack.push(stack.pop() + stack.pop());
                        break;
                    case '-':
                        num2 = stack.pop();
                        num1 = stack.pop();
                        stack.push(num1-num2);
                        break;
                    case '*':
                        stack.push(stack.pop() * stack.pop());
                        break;
                    case '/':
                        num2 = stack.pop();
                        num1 = stack.pop();
                        stack.push(num1 / num2);
                        break;
                    case '^':
                        num2 = stack.pop();
                        num1 = stack.pop();
                        stack.push(Math.pow(num1, num2));
                        break;
                    default:
                        break;
                }
            }
        }
        return stack.pop();
    }
    String getInfix(){
        return this.infixExpression;
    }
    String getPostfix(){
        return this.postfixExpression;
    }
}