package cs131.pa1.filter.concurrent;

public class BackGround {
	Thread thread;
	String command;
	public BackGround(Thread thread, String command) {
		this.thread = thread;
		this.command = command;
	}

}
