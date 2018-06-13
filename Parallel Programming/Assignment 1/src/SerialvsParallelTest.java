import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;


public class SerialvsParallelTest {
	static long startTime = 0;
	
	
	private static void tick(){
			startTime = System.currentTimeMillis();
		}
		private static float toc(){
		
			return (System.currentTimeMillis() - startTime) / 1000.0f; 
		}
		
		static final ForkJoinPool fjPool = new ForkJoinPool();
		
		
		static ArrayList<Double> Parallel(ArrayList<Double> inputs, int FilterSize){
		  return fjPool.invoke(new MedianFilterParallel(inputs,FilterSize,0,inputs.size()));
		}
		
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		String filename  = "/home/banks/CSC2001F/Assignment 1/src/sampleinputfile.txt";
		int FilterSize = 21; 
		int numInputs = 0; 
		ArrayList<Integer> TestSize = new ArrayList<Integer>(); 
		ArrayList<Double> TimedInputs = new ArrayList<Double>();
		ArrayList<Double> inputs = new ArrayList<Double>(); 
		
 		
		try(BufferedReader br = new BufferedReader(new FileReader(filename))){
			for(int i = 0 ; i < 100; i++){
				if(i == 0 ){
					numInputs = Integer.parseInt(br.readLine());
				}else{
					inputs.add(Double.parseDouble(br.readLine().split(" ")[1])); 
					}
				}
			}
 		
 		

		tick();
 		MedianFilter filter = new MedianFilter(inputs,FilterSize);
		float time = toc();
		System.out.println("Time taken for typical filter "+ time +" seconds");
		
		tick();
 		Parallel(inputs,FilterSize);
 		time = toc();
		System.out.println("Time taken for fork/join filter "+ time +" seconds");
	
		try{
		    PrintWriter writer = new PrintWriter("the-file-name.txt", "UTF-8");
		    writer.println("filtered Outputs");
		    //for( int i = 0 ; i < filter.filtered.size();i++){
		    	//writer.println(filter.filtered.get(i));
		    //}
		    
		    //for( int i = 0 ; i < Parallel(data,FilterSize).size();i++){
		    	//writer.println(Parallel(data,FilterSize).get(i));
		    //}
		    writer.close();
		} catch (IOException e) {
		   // cannot write into file
		}
	}

}