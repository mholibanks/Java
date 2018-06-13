/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author mholi mncube
 */
public class SimulatorOne {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        ArrayList<String> data = new ArrayList<String>();
        Graph graph = new Graph( );
        int pos;
        
        String line ; 
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                 do
                 {
                     line = bufferedReader.readLine();
                     data.add(line);
                 }while(line!=null && !line.isEmpty());
          
              bufferedReader.close();

   
              
    int Nnodes = Integer.parseInt(data.get(0));
    int noHosp = Integer.parseInt(data.get(Nnodes+1));
    String locHosp = data.get(Nnodes+2);
    int noVict = Integer.parseInt(data.get(Nnodes+3));
    String locVict = data.get(Nnodes+4);
    String[] Target1 = locVict.split(" ");
    String[] Target2 = locHosp.split(" ");
    double cost, To, From;
    String path1,path2;
 
   
    data.remove(0);
    
   
     
     for(int i = 0 ; i < Nnodes;i++){
			String[] arr1 = data.get(i).split(" ");
			String[] yourArray = Arrays.copyOfRange(arr1, 1, arr1.length);
			int s = 1 ;
			for(int p = 0 ; p < (yourArray.length)/2;p++){
                        graph.addEdge(Integer.toString(i), yourArray[2*s-2], Double.parseDouble(yourArray[2*s-1]));
			s = s + 1;
			}
		}
    
    
    ArrayList<Double> totCost = new ArrayList<Double>();
    ArrayList<String> route   = new ArrayList<String>();
    ArrayList<String> routeAdd = new ArrayList<String>();
    ArrayList<String> destination = new ArrayList<String>();
    String finalpath_str ="";
    for(int i = 0 ; i < noVict ; i++){
        System.out.println("victim "+Target1[i]);
        for(int j= 0 ; j < noHosp ; j++){
       
            graph.dijkstra(Target2[j]);
            graph.printPath(Target1[i]);
            To = graph.getCost();
            path1 = graph.getRoute();
            
            graph.dijkstra(Target1[i]);
            graph.printPath(Target2[j]);
            From = graph.getCost() ;
            path2 = graph.getRoute() ;
           
            totCost.add(To+From);
            routeAdd.add(path1+"|"+path2);
        }
        
            for(int a = 0 ; a < routeAdd.size() ; a++){
            String aux = "";
            String[] arr = routeAdd.get(a).split("\\|");
            String[] backTo = arr[1].split(" ");
            for(int d = 1 ; d < backTo.length ; d++){
                aux = aux+" "+backTo[d];
            }
            destination.add((arr[0]+" "+aux).replaceAll("( )+", " "));
            
            }
        
        
        
        pos = totCost.indexOf(Collections.min(totCost));
        double startingCost = Collections.min(totCost);
        for(int k = 0 ; k < totCost.size();k++){
            pos = totCost.indexOf(Collections.min(totCost));
         if(startingCost == totCost.get(pos)){
            System.out.println("hospital "+locHosp.split(" ")[pos]);
            System.out.println(destination.get(pos));
            totCost.set(pos,Double.POSITIVE_INFINITY);
            }
         }
       
        destination.clear();
        totCost.clear();
        routeAdd.clear();
        
    }
    
 
    
}
    
}
