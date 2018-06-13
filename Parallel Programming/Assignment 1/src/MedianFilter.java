import java.util.ArrayList;
import java.util.Collections;


/*
 * name: mholi mncube
 * student no: mncmho001
 */
public class MedianFilter {
	
	ArrayList<Double> x = new ArrayList<Double>();
	ArrayList<Double> y = new ArrayList<Double>();
	ArrayList<Double> helpArr = new ArrayList<Double>();
	
	int filterSize = 0;
	

	public MedianFilter(ArrayList<Double> x, int filterSize){
		
		this.x = x;
		this.filterSize = filterSize;
		int count = 0;
		y.add(x.get(0));
		
		for(int i = 0; i < x.size(); i++){
			count = i;
			if(filterSize + count>x.size()){break;}
			for(int j = 0;  j < filterSize; j++){
				helpArr.add(x.get(count));
				count += 1;
			}Collections.sort(helpArr);
			y.add(helpArr.get(filterSize/2));
			helpArr.clear();
		}
		y.add(x.get(x.size()-1));
		//for(int i = 0; i< y.size();i++){ debugging purposes
			//System.out.println(y.get(i));
			//}
		
	}

}