public class CSC1016S implements Student{
   //instances
   private String name;
   private int pracs;
   private int tests;
   private int exam;
   private int practests;
   //mutator
   public void setName(String name){this.name =  name;}
   //accessor
   public String getName(){ return name;}
   //mutator
   public void setMark(String category, int mark){
      if (category.equals("pracs")){
      pracs = mark;}
      else if (category.equals("tests")){ 
      tests = mark;}
      else if (category.equals("practests")){ 
      practests = mark;}
      else if(category.equals("exam")){
      exam = mark;}
    }
   //method
   public float getFinal(){
     return (float)(0.15*pracs + 0.15*tests + 0.10*practests + 0.60*exam);
      
   }
}
   