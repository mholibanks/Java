import java.util.*; 
public class sorting implements Comparator<String> {
   public int compare(String a, String b) {
      return a.length() - b.length();
   }
}

//reference for the code http://stackoverflow.com/questions/12642720/how-to-sort-linkedliststring