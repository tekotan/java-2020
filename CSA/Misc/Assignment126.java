public class Assignment126{
    public static void main(String[] args){
        System.out.println("I love you California . . . you're the greatest state of all . . .\nI love you in the winter, summer, spring, and in the fall.\nI love your fertile valleys, your dear mountains I adore.");
        String stringLiteral1 = "I am a string literal";
        String stringLiteral2 = "I am a string literal";
        String stringObject1 = new String("I am a string object");
        String stringObject2 = new String("I am a string object");
        stringLiteral1.concat("Disappear");
        System.out.println(stringLiteral1 + stringLiteral2);
        stringObject1 += stringObject2;
        System.out.println(stringObject1);
        System.out.println(stringObject2 + 10 + "\\\n" + "\"");
    }
}