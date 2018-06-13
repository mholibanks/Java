import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RecursiveTask;



public class MedianFilterParallel extends RecursiveTask<ArrayList<Double>> {
	
	private ArrayList<Double> x = new ArrayList<Double>();
	private ArrayList<Double> y = new ArrayList<Double>(); 
	private ArrayList<Double> helpArr= new ArrayList<Double>();
	
	private int lo; 
	private int hi;
	private int Filtersize;
	private int SEQUENTIAL_CUTOFF =  10000000;
	
	
	public MedianFilterParallel(ArrayList<Double> x , int FilterSize , int lo , int hi){
		this.x = x  ;
		this.Filtersize = FilterSize ; 
		this.lo = lo ; 
		this.hi = hi ; 
		if(lo == 0 ){ y.add(x.get(0));}
	}
	
	public ArrayList<Double> MedianFilter(ArrayList<Double> data ,int FilterSize){
		
		int count =  0  ;
		for(int i = 0 ; i < data.size() ; i ++){
			count = i ; 
			if(FilterSize+count> data.size()){break; }
			for(int j =  0 ; j < FilterSize ; j++){
			
				helpArr.add(data.get(count));
				count = count + 1 ; 
				}
			Collections.sort(helpArr);
			y.add(helpArr.get((helpArr.size()/2)));
			helpArr.clear(); 
			}
		
		y.add(x.get(x.size()-1));
		return y; 
	}
	
	
	@Override
	protected ArrayList<Double> compute() {
		if((hi - lo)<SEQUENTIAL_CUTOFF){
			return MedianFilter(x,Filtersize) ; 
		}
		else{
			
			MedianFilterParallel left = new MedianFilterParallel(this.x, this.Filtersize, this.lo, ((this.lo + this.hi)/2));
			MedianFilterParallel right = new MedianFilterParallel(this.x, this.Filtersize, ((this.hi + this.lo)/2), this.hi);
			
			
			left.fork() ; 
			
			ArrayList<Double> leftArr = new ArrayList<Double>();
			ArrayList<Double> rightArr = new ArrayList<Double>(); 
			
			rightArr = right.compute();
			leftArr  = left.join()    ;
			
			ArrayList<Double> output = new ArrayList<Double>();
			output.addAll(leftArr);
			output.addAll(rightArr);
			return output ; 
		}
		
	}
	
	

}
