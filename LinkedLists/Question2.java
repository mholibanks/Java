import java.io.*;
import java.util.*;

public class Question2{ 
   
   public static void main(String[] args)throws IOException 
   {  
      LinkedList<String> lst = new LinkedList<String>();
      LinkedList<String> sortedLst = new LinkedList<String>();
      String sCurrentLine;
      int currentValue=100;
      int index = 0; //keep track of the line of item
      int noChar = 0; 
      try{
          Scanner input = new Scanner(args[0]);
          String fileName = input.next();
          //String fileName = "testing.txt"; //denugging purposes
          BufferedReader br = new BufferedReader(new FileReader(fileName));//this line reads the text file
		
			while ((sCurrentLine = br.readLine()) != null) { //checks if the line is not null
                  lst.add(sCurrentLine);  //adds index with object into linked list
                  //System.out.println(sCurrentLine.length());  //debugging purposes 
         }
         Collections.sort(lst,new sorting());                  
         for(int i=0;i<lst.size();i++)
         {
            index++;
            String lstItem = (String)lst.get(i); //get data of the item in linked list
            noChar = 0; //after every count no. of char are set to zero for new count
            for(char c: lstItem.toCharArray())
            {
               if(!Character.isWhitespace(c))
               {
                  noChar++;
               }
		      }
        System.out.println(index+"/"+noChar+":"+" "+lst.get(i));
       } 
      } catch (IOException e) {
			e.printStackTrace();
		}
    } 
}