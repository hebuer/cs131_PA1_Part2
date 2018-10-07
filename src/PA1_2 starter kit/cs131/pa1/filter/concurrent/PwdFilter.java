package cs131.pa1.filter.concurrent;

public class PwdFilter extends ConcurrentFilter {
	public PwdFilter() {
		super();
	}
	
	public void process() {
		output.add(processLine(""));
		finish=true;
	}
	
	public String processLine(String line) {
		return ConcurrentREPL.currentWorkingDirectory;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
