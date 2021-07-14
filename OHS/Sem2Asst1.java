import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Sem2Asst1 {
    public static void main(String[] args){
        Student studentOne = new Student("Tanish", "Baranwal", "97737", "January 1, 2004", "1");
        Student studentTwo = new Student("Sai", "Balakumar", "94343", "December 31, 2003", "2");
        StudentRecords database = new StudentRecords();
        database.addRecord(studentOne);
        database.addRecord(studentTwo);
        System.out.println("get key ID");
        System.out.println(database.getByKeyID(studentOne.studentID).stringRecord());
        System.out.println("get key Composite");
        System.out.println(database.getByKeyComposite(studentOne.firstName + " " 
                            + studentOne.lastName + ", " + studentOne.dob).stringRecord());
        System.out.println("printing records");
        database.printRecords();
        System.out.println("deleting student one and printing records");
        database.removeRecord(studentOne);
        database.printRecords();
    }
}

class Student{
    String firstName;
    String lastName;
    String studentID;
    String dob;
    String data;

    Student(String firstName, String lastName, String studentID, String dob, String data){
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.dob = dob;
        this.data = data;
    }
    String stringRecord(){
        return firstName + " " + lastName + ", " + dob + 
                " Student ID: " + studentID + " Data: " + data;
    }
}
class StudentRecords{
    LinkedList<Student> records;
    HashMap<String, Student> keyID;
    HashMap<String, Student> keyComposite;
    StudentRecords(){
        records = new LinkedList<>();
        keyID = new HashMap<>();
        keyComposite = new HashMap<>();
    }
    void addRecord(Student student) throws RuntimeException{
        if (keyID.containsKey(student.studentID) || 
            keyComposite.containsKey(student.firstName+" "+student.lastName+", "+student.dob)){
            throw new RuntimeException();
        }
        keyID.put(student.studentID, student);
        keyComposite.put(student.firstName+" "+student.lastName+", "+student.dob, student);
        records.add(student);
    }
    
    void removeRecord(Student student) {
        if (!keyID.containsKey(student.studentID)
                && !keyComposite.containsKey(student.firstName + " " + student.lastName + ", " + student.dob)) {
            return;
        }
        keyID.remove(student.studentID);
        keyComposite.remove(student.firstName + " " + student.lastName + ", " + student.dob);
        records.remove(student);
    }
    ArrayList<Student> getByName(String name){
        ArrayList<Student> retArray = new ArrayList<>();
        for (Student s : records){
            if ((s.firstName+" "+s.lastName).contains(name)){
                retArray.add(s);
            }
        }
        return retArray;
    }
    Student getByKeyID(String key){
        return keyID.get(key);
    }
    Student getByKeyComposite(String key){
        return keyComposite.get(key);
    }
    void printRecords(){
        for (Student s: records){
            System.out.println(s.stringRecord());
        }
    }
}