import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Assignment11 {
    HashTree<String, Integer> hashMap;
    String[] alphabet = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    public Assignment11() {
        List<String> list = new ArrayList<>();
        try {
            list = Files.readAllLines(new File("words.txt").toPath(), Charset.defaultCharset());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        hashMap = new HashTree<>();
        for (String s : list){
            hashMap.put(s.toLowerCase(), 0);
        }

    }
    
    public String swap(String str, int i, int j) {
        if (j == str.length() - 1)
            return str.substring(0, i) + str.charAt(j) + str.substring(i + 1, j) + str.charAt(i);

        return str.substring(0, i) + str.charAt(j) + str.substring(i + 1, j) + str.charAt(i)
                + str.substring(j + 1, str.length());
    }

    public void spellCheck(String word){
        String lowerWord = word.toLowerCase();
        if (hashMap.contains(lowerWord)){
            System.out.println("No mistakes found");
            return;
        }
        for (String s: alphabet){
            if (hashMap.contains(lowerWord + s))
                System.out.println(lowerWord + s);
            if (hashMap.contains(s + lowerWord))
                System.out.println(s + lowerWord);
        }
        if (hashMap.contains(lowerWord.substring(1)))
            System.out.println(lowerWord.substring(1));
        if (hashMap.contains(lowerWord.substring(0, lowerWord.length() - 1)))
            System.out.println(lowerWord.substring(0, lowerWord.length() - 1));

            for(int i=0; i<lowerWord.length() - 1; i++){
                if (hashMap.contains(swap(lowerWord, i, i+1))){
                    System.out.println(swap(lowerWord, i, i+1));
                }
            }
    }
    public static void main(String[] args){
        Assignment11 spellChecker = new Assignment11();
        System.out.println("Exact Word: alphabetical");
        spellChecker.spellCheck("alphabetical");
        System.out.println("Adding to front: lphabetical");
        spellChecker.spellCheck("lphabetical");
        System.out.println("Adding to back: alphabetica");
        spellChecker.spellCheck("alphabetica");
        System.out.println("removing from front: balphabetical");
        spellChecker.spellCheck("balphabetical");
        System.out.println("Removing from back: alphabeticall");
        spellChecker.spellCheck("alphabeticall");
        System.out.println("Swapping adjacent letters: laphabetical");
        spellChecker.spellCheck("laphabetical");

    }
}

class HashTree<Key, Value> {

    private static final int INIT_CAPACITY = 64;

    private int n;
    private int m;
    private Key[] keys;
    private Value[] vals;

    public HashTree() {
        this(INIT_CAPACITY);
    }


    public HashTree(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[]) new Object[m];
        vals = (Value[]) new Object[m];
    }


    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    private int hash(Key key) {
        int h = key.hashCode();
        h ^= (h >>> 20) ^ (h >>> 12) ^ (h >>> 7) ^ (h >>> 4);
        return h & (m - 1);
    }

    public void put(Key key, Value val) {

        int i;
        for (i = hash(key); keys[i] != null; i = (i + 1) % m) {
            if (keys[i].equals(key)) {
                vals[i] = val;
                return;
            }
        }
        keys[i] = key;
        vals[i] = val;
        n++;
    }

    public Value get(Key key) {
        for (int i = hash(key); keys[i] != null; i = (i + 1) % m)
            if (keys[i].equals(key))
                return vals[i];
        return null;
    }

    public void delete(Key key) {
        if (!contains(key))
            return;

        int i = hash(key);
        while (!key.equals(keys[i])) {
            i = (i + 1) % m;
        }

        keys[i] = null;
        vals[i] = null;

        i = (i + 1) % m;
        while (keys[i] != null) {
            Key keyToRehash = keys[i];
            Value valToRehash = vals[i];
            keys[i] = null;
            vals[i] = null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }

        n--;

    }
}
