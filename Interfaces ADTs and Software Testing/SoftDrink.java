import java.util.*;
public class SoftDrink implements Comparable<SoftDrink>{
   //instances
   private String name;
   private String colour;
   private int volume;
   
   //contructor
   public SoftDrink(String name, String colour, int volume){
      this.name =  name;
      this.colour = colour;
      this.volume =  volume;
   }
   
   //mutators
   public void setName(String name){this.name = name;}
   public void setColour(String colour){this.colour = colour;}
   public void setVolume(int volume){this.volume = volume;}
   
   //accessors
   public String getName(){return name;}
   public String getColour(){return colour;}
   public int getVolume(){return volume;}
   
   //string type of object
   public String toString(){
      return this.name+" "+this.colour+" "+this.volume; 
   }
   public int compareTo(SoftDrink other) {
        if (!this.name.equalsIgnoreCase(other.getName()))
            return this.name.compareTo(other.getName());
        if (!this.colour.equalsIgnoreCase(other.getColour()))
            return this.colour.compareTo(other.getColour());
        return this.volume - other.volume;
    }
}