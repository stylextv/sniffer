package io.sniffer.util.system;

public class ThreadUtil {
	
	public static void sleep(long duration) {
		try {
			
			Thread.sleep(duration);
			
		} catch (InterruptedException exception) {
			String message = String.format("An error occurred while trying to sleep thread: %s", exception);
			System.out.println(message);
		}
	}
	
}
