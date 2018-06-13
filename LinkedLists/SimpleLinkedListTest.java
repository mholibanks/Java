import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class SimpleLinkedListTest {
   private SimpleLinkedList s;
   private String[] lst = {"Thunders","Chicago Bulls","Wizzards","Warriors","Cavaliers","Spurs","Clippers","Lakers", "Miami Heat"};
   private SimpleLinkedList<String> list = new SimpleLinkedList<String>(lst);

   @Test 
   public void listTest(){
      for(int i=0;i<list.size();i++){
         assertEquals(list.get(i),lst[i]); //result expected
      }
   }
   
   @Test 
   
   public void indexTest(){
      int i = list.indexOf("Cavaliers");
      //System.out.println(i); debugging purposes
      assertEquals(i, 4);
   }
   
   @Test 
   public void positionTest(){
      list.insert(2,"Knicks");
      assertEquals(list.indexOf("Knicks"),2); //checks the index of inserted item
      assertEquals(list.get(2),"Knicks"); //checks the value of inserted item
   }
   
   @Test 
   public void truncateTest(){
      list.trimToSize(3);
      assertEquals(list.size(),3);
   }
}
