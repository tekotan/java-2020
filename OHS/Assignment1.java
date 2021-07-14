import java.util.ArrayList;
import java.util.HashMap;
public class Assignment1 {
  public static void main(String[] args) {
    Tester.test();
    PersonTester.test();
    AddressBookTester.test();
    ThermostatTester.test();
  }
}
//
/*
 * Algorithm: make a running note of the visited areas of the graph, and keep exploring the possible movements through the graph. 
 * if the graph circles around to a point where all areas are explored you go back.
 * Some issues that need to be addressed are that there needs to a record of previously traversed areas and to run a trial and error until the path is found.
*/
// 1.1.19
class Fibonacci {
  static HashMap cache = new HashMap<Integer, Long>();

  public static long F(int N) {
    if (N == 0)
      return 0;
    if (N == 1)
      return 1;
    if (cache.get(N) == null){
      return (long) cache.get(N);
    }
    long value = F(N - 1) + F(N - 2);
    cache.put(N, value);
    return F(N - 1) + F(N - 2);
  }

  public static void main(String[] args) {
    for (int N = 0; N < 100; N++)
      System.out.println(N + " " + F(N));
  }
}

// 1.1.34
/*
 * Print the maximum and minimum numbers
 * Print the sum of the squares of the numbers.
 * Print the average of the N numbers
 * Print the percentage of numbers greater than the average.
 */

class Person {
  String firstName;
  String lastName;
  int id;
  static int lastID = 1000;

  Person() {
    firstName = "";
    lastName = "";
    id = ++lastID;
  }

  Person(String first, String last) {
    firstName = first;
    lastName = last;
    id = ++lastID;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public int getID() {
    return id;
  }

  public String toString() {
    return String.format("Name: %s %s, ID: %d", firstName, lastName, id);
  }
}

class Student extends Person {
  ArrayList<String> classes = new ArrayList<>();
  ArrayList<Double> grades = new ArrayList<>(); // percentages

  Student(ArrayList<String> classes, ArrayList<Double> grades, String first, String last) {
    this.classes = classes;
    this.grades = grades;
    firstName = first;
    lastName = last;
    id = ++lastID;
  }
  
  Student(String first, String last) {
    firstName = first;
    lastName = last;
    id = ++lastID;
  }

  String getClass(int index) {
    if (index < classes.size()) {
      return classes.get(index);
    } else {
      return "";
    }
  }

  Double getGrade(int index) {
    if (index < grades.size()) {
      return grades.get(index);
    } else {
      return 0.0;
    }
  }

  void changeClass(int index, String element) {
    if (index < classes.size()) {
      classes.set(index, element);
    }
  }

  void changeGrade(int index, double element) {
    if (index < grades.size()) {
      grades.set(index, element);
    }
  }

  void addClassGrade(String addClass, double grade) {
    classes.add(addClass);
    grades.add(grade);
  }

  void printTranscript(){
    for (int i=0; i<classes.size(); i++){
      System.out.println(classes.get(i) + ": " + grades.get(i));
    }
  }
}

class HourlyEmployee extends Person{
  String department;
  double hourlyRate;
  double hours;
  HourlyEmployee(String department, double rate, double hours, String first, String last){
    this.department = department;
    this.hourlyRate = rate;
    this.hours = hours;
    firstName = first;
    lastName = last;
    id = ++lastID;

  }
  double getHourlyRate(){
    return hourlyRate;
  }
  double getHours(){
    return hours;
  }
  String getDepartment(){
    return department;
  }
  
  void setHourlyRate(double rate) {
    hourlyRate = rate;
  }

  void setHours(double hours) {
    this.hours = hours;
  }

  double getWages(){
    return hours * hourlyRate;
  }

  double getAvgHours(){
    return hours / 4;
  }
}

class FulltimeEmployee extends Person {
  String department;
  double salary;

  FulltimeEmployee(String department, double salary, String first, String last) {
    this.department = department;
    this.salary = salary;
    firstName = first;
    lastName = last;
    id = ++lastID;

  }

  double getSalary() {
    return salary;
  }

  String getDepartment() {
    return department;
  }

  void setSalary(double salary) {
    this.salary = salary;
  }
}

class AddressBook<E extends Person>{
  ArrayList<E> people = new ArrayList<>();
  void add(E person){
    // check if person is in list
    for (E compPerson : this.people){
      if (compPerson.getID() == person.getID()){
        return;
      }
    }
    people.add(person);
  }
  void delete(int id){
    for (int i=0; i<people.size(); i++){
      if (people.get(i).getID() == id){
        people.remove(i);
        return;
      }
    }
  }
  ArrayList<E> search(int id){
    ArrayList<E> matches = new ArrayList<>();
    for(E person : people){
      if (person.getID() == id){
        matches.add(person);
      }
    }
    return matches;
  }
}

class PersonTester {
  public static void test() {
    Person bobby = new Person("Bobby", "John");
    assert bobby.getLastName().equals("John");
    assert bobby.getFirstName().equals("Bobby");
    assert bobby.getFullName().equals("Bobby John");

    assert bobby.getID() == 1001;
  }
}

class Tester {
  public static void test() {
    Student student = new Student("Bob", "Doe");
    student.addClassGrade("Chem", 100);
    student.addClassGrade("Math", 90);
    student.getClass(0);
    student.printTranscript();

    HourlyEmployee hrEmployee = new HourlyEmployee("tester", 13.45, 40, "Billy", "Doe");
    hrEmployee.getAvgHours();
    hrEmployee.getWages();

    FulltimeEmployee fullEmployee = new FulltimeEmployee("tester", 10000, "Billy", "Doe");
    fullEmployee.getSalary();
  }
}

class AddressBookTester {
  public static void test() {
    AddressBook<Student> book = new AddressBook<>();
    Student student1 = new Student("Bob", "Doe");
    student1.addClassGrade("Chem", 100);
    student1.addClassGrade("Math", 90);
    Student student2 = new Student("Bobby", "Doe");
    student2.addClassGrade("Chem", 100);
    student2.addClassGrade("Math", 90);
    book.add(student1);
    book.add(student2);
    System.out.println(book.search(student1.getID()));
    book.delete(student2.getID());
    System.out.println(book.search(student2.getID()));
  }
}

class TemperatureOutofRange extends RuntimeException{
  public TemperatureOutofRange(String errorMessage){
    super (errorMessage);
  }
}

class TemperatureTooHigh extends TemperatureOutofRange {
  public TemperatureTooHigh() {
    super("Temperature is too high");
  }
}

class TemperatureTooLow extends TemperatureOutofRange {
  public TemperatureTooLow() {
    super("Temperature is too low");
  }
}

class Thermostat{
  int low;
  int high;
  int temperature;
  Thermostat(int low, int high){
    this.low = low;
    this.high = high;
  }
  void setTemp(int temp){
    if (temp < low){
      throw new TemperatureTooLow();
    } else if (temp > high){
      throw new TemperatureTooHigh();
    } else {
      this.temperature = temp;
    }
  }
}

class ThermostatTester{
  public static void test(){
    Thermostat t = new Thermostat(0, 100);
    try {
      t.setTemp(101);
    } catch (TemperatureOutofRange e){
      System.out.println("Temp out of range caught");
    }
    try {
      t.setTemp(101);
    } catch (TemperatureTooHigh e) {
      System.out.println("Temp too high caught");
    }
    try {
      t.setTemp(-10);
    } catch (TemperatureTooLow e) {
      System.out.println("Temp too low caught");
    }
  }
}