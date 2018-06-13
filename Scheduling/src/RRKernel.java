import simulator.CPU;
import simulator.Config;
import simulator.IODevice;
import simulator.Kernel;
import simulator.ProcessControlBlock;
import simulator.ProcessControlBlock.State;
import simulator.ProcessControlBlockImpl;//
import java.io.FileNotFoundException;
import java.io.IOException;
//
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Concrete Kernel type
 * 
 * @author Stephan Jamieson
 * @version 8/3/15
 */
public class RRKernel implements Kernel {

    private Deque<ProcessControlBlock> readyQueue;
    private ProcessControlBlock outProcess;
    private int timeSlice;
    
    public RRKernel(int timeSlice){ 
    	// Set up the ready queue.
    	readyQueue = new ArrayDeque<ProcessControlBlock>();
    	this.timeSlice = timeSlice;
    }
    
    private ProcessControlBlock dispatch() {

    	
    	///schedule timeout interrupts on the current process
    	
    	if (readyQueue.isEmpty()){
    		outProcess = Config.getCPU().contextSwitch(null);
    		return outProcess;
    	}
    	
    	else{
    		
    		ProcessControlBlock pro = readyQueue.pollFirst();
    		outProcess = Config.getCPU().contextSwitch(pro);
    		pro.setState(ProcessControlBlock.State.RUNNING);
    		Config.getSystemTimer().scheduleInterrupt(timeSlice, this, pro.getPID());
    		
    		return outProcess;
    	}
	}
                
    public int syscall(int number, Object... varargs) {
        int result = 0;
        switch (number) {
             case MAKE_DEVICE:
                {
                    IODevice device = new IODevice((Integer)varargs[0], (String)varargs[1]);
                    Config.addDevice(device);
                }
                break;
             case EXECVE: 
                {
                    ProcessControlBlock pcb = this.loadProgram((String)varargs[0]);
                    if (pcb!=null) {
                        // Loaded successfully.
						// Now add to end of ready queue.
						// If CPU idle then call dispatch.
                    	pcb.setState(ProcessControlBlock.State.READY);
                    	readyQueue.addLast(pcb);    // adding to the readyQueue
                    	
                    	if(Config.getCPU().isIdle()){
                    		this.dispatch();        // if there is currently nothing on the cpu
                    	}             	
                    }
                    else {
                        result = -1;
                    }
                }
                break;
             case IO_REQUEST: 
                {

                	
                	ProcessControlBlock pcb = Config.getCPU().getCurrentProcess();
                	IODevice device = Config.getDevice((Integer)varargs[0]);
                	device.requestIO((Integer)varargs[1], pcb, this);
                	pcb.setState(State.WAITING);
                	//cancel the inturrupt
                	Config.getSystemTimer().cancelInterrupt(pcb.getPID());
                	this.dispatch();
                
                	
                	
                }
                break;
             case TERMINATE_PROCESS:
                {
					// Process on the CPU has terminated.
					// Get PCB from CPU.
					// Set status to TERMINATED.
                    // Call dispatch().
                	
                	ProcessControlBlock pcb = Config.getCPU().getCurrentProcess();
                	pcb.setState(State.TERMINATED);
                	Config.getSystemTimer().cancelInterrupt(pcb.getPID());
                	this.dispatch();
                	
                
                	
                	
                }
                break;
             default:
                result = -1;
        }
        return result;
    }
   
    
    public void interrupt(int interruptType, Object... varargs){
        switch (interruptType) {
            case TIME_OUT:
            	ProcessControlBlock pcbImp = Config.getCPU().getCurrentProcess();   // actually i have a process id at possition 0, now how do i get the process?
            	if(pcbImp!=null && !readyQueue.isEmpty()){
            	pcbImp.setState(ProcessControlBlock.State.READY);
            	}
            	readyQueue.add(pcbImp);
            	this.dispatch();
            	break;	
           
            case WAKE_UP:
				// IODevice has finished an IO request for a process.
				// Retrieve the PCB of the process (varargs[1]), set its state
				// to READY, put it on the end of the ready queue.
				// If CPU is idle then dispatch().
            	
            	ProcessControlBlock pcb = (ProcessControlBlock)varargs[1];
            	pcb.setState(State.READY);
            	readyQueue.addLast(pcb);
            	
            	if(Config.getCPU().isIdle()){
            		this.dispatch();
            	}
            	

                break;
            default:
                throw new IllegalArgumentException("FCFSKernel:interrupt("+interruptType+"...): unknown type.");
        }
    }
    
    private static ProcessControlBlock loadProgram(String filename) {
        try {
            return ProcessControlBlockImpl.loadProgram(filename);
        }
        catch (Exception fileExp) {
            return null;
        }
    }
}
