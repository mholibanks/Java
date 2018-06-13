import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class CSC1016STest {
   private CSC1016S c;
   private double delta;  

   @Before public void setUp() {
      c = new CSC1016S();
      delta = 0.00;
   }

   @Test 
   public void testName(){
      c.setName("Mholi");
      assertEquals(c.getName(),"Mholi"); //result expected
   }
   
   @Test 
   public void testZeroMark(){
      c.setMark("tests",0);
      c.setMark("pracs",0);
      c.setMark("exam",0);
      c.setMark("practests",0);
      assertEquals((float)0,c.getFinal(),delta);
   }
   
   @Test 
   public void test100Mark(){
      c.setMark("tests",100);
      c.setMark("pracs",100);
      c.setMark("exam",100);
      c.setMark("practests",100);
      assertEquals((float)100,c.getFinal(),delta);
   }
   
   @Test 
   public void testRandomMark(){
      c.setMark("tests",30);
      c.setMark("pracs",10);
      c.setMark("exam",40);
      c.setMark("practests",20);
      assertEquals((float)32,c.getFinal(),delta);
   }
}
