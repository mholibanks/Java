import java.util.*;

public class Question1{
   public static void main(String[] args){
      Scanner kb = new Scanner(System.in);
      List<SoftDrink> softdrink = new ArrayList<SoftDrink>();
      SoftDrink soda;
      while(true){
         
         System.out.println("Enter option: (1) add soft drink (2) quit:");
         int option = kb.nextInt();
         if  (option==1){
            System.out.println("Enter name, colour and volume in ml separated by space");
            String softD = kb.nextLine();
            String[] splitStr = softD.split("\\s+");
            soda = new SoftDrink(kb.next(),kb.next(),kb.nextInt());
            softdrink.add(soda);
         }
         else if (option==2){
            Collections.sort(softdrink);
            for(int i=0;i<softdrink.size();i++){
               System.out.println(softdrink.get(i).toString());
            }break;
         }
          else{
            System.out.println("Please select one of the options given.");}
      }
   }
}