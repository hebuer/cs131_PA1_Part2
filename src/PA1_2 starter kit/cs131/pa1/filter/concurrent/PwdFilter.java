package cs131.pa1.filter.concurrent;

public class PwdFilter extends ConcurrentFilter {
	public PwdFilter() {
		super();
	}
	
	public void process() {
		finish = false;
		try {
			output.put(processLine(""));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		kill();
	}
	
	public String processLine(String line) {
		return ConcurrentREPL.currentWorkingDirectory;
	}

	public String toString() {
		return "Pwd";
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
