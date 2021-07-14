public class Main{
  public static void main (String[] args){
    Book book1 = new Book("My title", "Bobby Fisher", 2000);
    Book book2 = new Book("My second title");
    Book book3 = new Book();
    System.out.println(book1);
    System.out.println(book2);
    System.out.println(book3);

  }
}
class Book{
  String title;
  String author;
  int year;
  
  public Book(String title, String author, int year){
    this.setTitle(title);
    this.setAuthor(author);
    this.setYear(year);
  }
  public Book(String title){
    this(title, "unknown", 1450);
  }
  public Book(){
    this("none", "unknown", 1450);
  }
  public void setTitle(String title){
    this.title = title.toUpperCase();
  }
  public void setAuthor(String author){
    this.author = author;
  }
  public void setYear(int year){
    this.year = (year >= 1450) ? year : 2000;
  }
  public String toString(){
    return title + " by " + author + " " + year;
  }
}
