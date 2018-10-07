package cs131.pa1.filter.concurrent;
import java.io.File;

public class LsFilter extends ConcurrentFilter{
	int counter;
	File folder;
	File[] flist;
	
	public LsFilter() {
		super();
		counter = 0;
		folder = new File(ConcurrentREPL.currentWorkingDirectory);
		flist = folder.listFiles();
	}
	
	@Override
	public void process() {
		finish = false;
		while(counter < flist.length) {
			try {
				output.put(processLine(""));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		kill();
	}
	
	@Override
	public String processLine(String line) {
		return flist[counter++].getName();
	}
	
	public String toString() {
		return "Ls";
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
