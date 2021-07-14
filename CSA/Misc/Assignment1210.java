import java.util.Scanner;

public class Assignment1210 {
    public static void main(String[] args){
        MadLibs m = new MadLibs();
        // Testing
        m.populate();
        m.printMadLib();
        m.populate();
        m.printMadLib();
        m.populate();
        m.printMadLib();
    }
}

class MadLibs{
    private String[] partsOfSpeech = {"noun", "plural_noun", "number", "adjective", "noun", "adverb", "plural_noun", "verb"};
    private String template = "I went to the animal %s, the %s and the %s beasts were there.\nOnce upon a time in a %s land, a %s %s grew.\nThere were also %s who %s their cattle";
    private String[] userWords = new String[partsOfSpeech.length];
    public MadLibs(){
        System.out.println(template);
    }
    public void populate(){
        // User Input
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to MadLibs, please enter your words for the parts of speech");
        for (int i=0; i<partsOfSpeech.length; i++){
            // String Method, prompts to keep user on track
            String output = String.format("Enter a %s please: ", partsOfSpeech[i]);
            System.out.println(output);
            userWords[i] = sc.nextLine();
        }
    }
    public void printMadLib(){
        // Displays final madlib
        String output = String.format(template, (Object[]) userWords);
        System.out.println(output);
    }
}