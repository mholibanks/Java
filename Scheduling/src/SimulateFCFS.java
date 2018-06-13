import simulator.Config;
import simulator.Kernel;
import simulator.SystemTimer;
import simulator.TRACE;
import java.util.Scanner; 

public class SimulateFCFS {
	public void runSimulator(){
	}
	
	public static void main(String[] args){
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("*** FCFS Simulator ***");
		System.out.print("Enter configuration file name: ");
		String configFileName = scan.nextLine();
		System.out.print("Enter cost of system call: ");
		int syscallCost = scan.nextInt();
		System.out.print("Enter cost of context switch: ");
		int dispatchCost = scan.nextInt();
		System.out.print("Enter trace level: ");
		int traceLevel = scan.nextInt();	
		
		TRACE.SET_TRACE_LEVEL(traceLevel);
		final Kernel kernel = new FCFSKernel();
		Config.init(kernel, dispatchCost, syscallCost);
		Config.buildConfiguration(configFileName);
		Config.run();
		SystemTimer timer = Config.getSystemTimer();
		System.out.println(timer);
		System.out.println("Context switches: " +Config.getCPU().getContextSwitches());
		System.out.printf("CPU utilization: %.2f\n", ((double)timer.getUserTime())/timer.getSystemTime()*100);
	}

}
