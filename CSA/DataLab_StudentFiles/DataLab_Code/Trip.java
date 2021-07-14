import core.data.*;
import java.util.ArrayList;
public class Trip {
   public static void main(String[] args) {
      DataSource ds = DataSource.connectAs("csv",
            "https://s3.amazonaws.com/divvy-data/tripdata/Divvy_Trips_2017_Q1Q2.zip");
      ds.setOption("file-entry", "Divvy_Trips_2017_Q1.csv");
      ds.load();

      ArrayList<TripData> trips = ds.fetchList("TripData", "trip_id", "start_time", "usertype", "tripduration", "birthyear");

      int[] durations = new int[trips.size()];
      int[] ages = new int[trips.size()];

      for (int i=0; i<trips.size(); i++){
         durations[i] = trips.get(i).getDuration();
         ages[i] = trips.get(i).getAge();
      }

      double correlation = correlationCoefficient(durations, ages);
      System.out.println("Correlation between a person's age and their trip duration is: " + correlation);
   }
   
   public static double correlationCoefficient(int[] xs, int[] ys) {

      double sx = 0.0;
      double sy = 0.0;
      double sxx = 0.0;
      double syy = 0.0;
      double sxy = 0.0;

      int n = xs.length;

      for (int i = 0; i < n; ++i) {
         double x = xs[i];
         double y = ys[i];

         sx += x;
         sy += y;
         sxx += x * x;
         syy += y * y;
         sxy += x * y;
      }

      double cov = sxy / n - sx * sy / n / n;
      double sigmax = Math.sqrt(sxx / n - sx * sx / n / n);
      double sigmay = Math.sqrt(syy / n - sy * sy / n / n);

      return cov / sigmax / sigmay;
   }

}

class TripData {
   private String id;
   private String start; // date & time
   private String user;
   private int minutes;
   private int personAge;

   public TripData(String id, String start, String user, int minutes, int birthYear) {
      this.id = id;
      this.start = start;
      this.user = user;
      this.minutes = minutes;
      this.personAge = 2021 - birthYear;
   }
   public int getDuration(){
      return minutes;
   }
   public int getAge(){
      return personAge;
   }
   public String toString() {
      return "Trip [id=" + id + ", start=" + start + ", user=" + user + ", minutes=" + minutes + "age=" + personAge + "]";
   }
}