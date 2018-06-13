package simulator;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Mncube Mncube
 *
 */

public class ProcessControlBlockImpl implements ProcessControlBlock{
	
	private int PID=0;
	private static int i =1;
	private ArrayList<Instruction> instructions;
	private int priority;
	private int programCounter;
	private String programName;
	private State state;
	
	public ProcessControlBlockImpl( ArrayList<Instruction> instructs,String name){
		
		PID = this.i++;
		instructions = instructs;
		state = State.READY;
		programName = name;
		programCounter=0;
		priority = 0;
	}
	
	public static ProcessControlBlockImpl loadProgram(String fileName){
		
		ArrayList<Instruction> ins = new ArrayList<>();
		
		try {
           
            FileReader fileReader =  new FileReader(fileName);
            String line = null;
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            
            int increment = 1;
            while((line = bufferedReader.readLine()) != null) {  
            	// writing instructions, adding them to the instruction array
            	if(increment%2!=0){
            		CPUInstruction cpuInstruction = new CPUInstruction(Integer.parseInt(line.substring(4)));
            		ins.add(cpuInstruction);
            	}
            
            	else{
            		String[] var = line.split(" ");
            		IOInstruction iOInstruction = new IOInstruction(Integer.parseInt(var[1]),Integer.parseInt(var[2]));  
            		ins.add(iOInstruction);
            	}
            
            	increment++;
            }   

            bufferedReader.close();         
        }              
        
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");                  
        }
		
		return new ProcessControlBlockImpl(ins,fileName);
    }			
	
	@Override
	public int getPID() {		
		return PID;
	}

	@Override
	public String getProgramName() {
		return programName;
	}

	@Override
	public int getPriority() {	
		return priority;
	}

	@Override
	public int setPriority(int value) {
		priority = value;
		return priority;
	}

	@Override
	public Instruction getInstruction() {

		return instructions.get(programCounter);
		
	}

	@Override
	public boolean hasNextInstruction() {
		
		if(programCounter>=instructions.size()-1){
			return false;
		}
		else{
			return true;
		}
	}

	@Override
	public void nextInstruction() {
		if(hasNextInstruction()){
			programCounter++;
		}else{
			this.state = state.TERMINATED;
		}
				
		
	}
	
	@Override
	public void setState(State state) {
		this.state = state;
	}

	@Override
	public State getState() {	
		return state;
	}

	
	public String toString(){
		return "process(pid=" + PID + ", state=" + this.state + ", name="+ '"'+this.programName+'"'+ ")";
		
	}
	
}
