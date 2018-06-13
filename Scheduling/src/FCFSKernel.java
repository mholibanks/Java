import simulator.Config;
import simulator.IODevice;
import simulator.Kernel;
import simulator.ProcessControlBlock;
import simulator.ProcessControlBlock.State;
import simulator.ProcessControlBlockImpl;//*/
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Concrete Kernel type
 * 
 * @author Stephan Jamieson
 * @version 8/3/15
 */
public class FCFSKernel implements Kernel {
    

    private Deque<ProcessControlBlock> readyQueue;
    private ProcessControlBlock outProcess;
    
    public FCFSKernel(){ 
    	
    	// Set up the ready queue.
    	readyQueue = new ArrayDeque<>();
    }
    
    private ProcessControlBlock dispatch() {

    	if (readyQueue.isEmpty()){
    		outProcess = Config.getCPU().contextSwitch(null);
    		return outProcess;
    	}
    	
    	else{
    		ProcessControlBlock pcb = readyQueue.pollFirst();
    		
    		outProcess = Config.getCPU().contextSwitch(pcb);
    		pcb.setState(ProcessControlBlock.State.RUNNING);
    		
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
                	
                    ProcessControlBlock pcb = FCFSKernel.loadProgram((String)varargs[0]);
                    if (pcb!=null) {
                    	
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
                	
                	pcb.setState(ProcessControlBlock.State.WAITING);
                	device.requestIO((Integer)varargs[1], pcb, this);
                	this.dispatch();
                }
                break;
                
             case TERMINATE_PROCESS:
                {
                	ProcessControlBlock pcb = Config.getCPU().getCurrentProcess();
                	pcb.setState(ProcessControlBlock.State.TERMINATED);
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
                throw new IllegalArgumentException("FCFSKernel:interrupt("+interruptType+"...): this kernel does not suppor timeouts.");
            case WAKE_UP:
            	
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
    
    private static ProcessControlBlockImpl loadProgram(String filename) {
        try {
            return ProcessControlBlockImpl.loadProgram(filename);
        }
        catch(Exception ex) {
            return null;
        }
    }
}
