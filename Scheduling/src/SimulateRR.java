import java.util.Scanner;

import simulator.Config;
import simulator.Kernel;
import simulator.SystemTimer;
import simulator.TRACE;

public class SimulateRR {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		System.out.println("*** RR Simulator ***");
		System.out.print("Enter configuration file name: ");
		String configFileName = scan.next();
		System.out.print("Enter slice time: ");
		int sliceTime = scan.nextInt();
		System.out.print("Enter cost of system call: ");
		int sysCallCost = scan.nextInt();
		System.out.print("Enter cost of context switch: ");
		int cSwitchCost = scan.nextInt();
		System.out.print("Enter trace level: ");
		int traceLevel = scan.nextInt();	
		
		TRACE.SET_TRACE_LEVEL(traceLevel);
		final Kernel kernel = new RRKernel(sliceTime);
		Config.init(kernel, cSwitchCost, sysCallCost);
		Config.buildConfiguration(configFileName);
		Config.run();
		SystemTimer timer = Config.getSystemTimer();
		System.out.println(timer);
		System.out.println("Context switches: " +Config.getCPU().getContextSwitches());
		System.out.printf("CPU utilization: %.2f\n", ((double)timer.getUserTime())/timer.getSystemTime()*100);

	}

}
