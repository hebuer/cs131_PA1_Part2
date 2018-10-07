package cs131.pa1.filter.concurrent;

public class PrintFilter extends ConcurrentFilter {
	public PrintFilter() {
		super();
	}
	
	public void process() {
		finish = false;
		String line = null;
		while (line != ConcurrentFilter.terminate) {
			if (line != null && !line.equals(ConcurrentFilter.terminate)) {
				processLine(line);
			}
			try {
				line = input.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		kill();
	}
	
	public String processLine(String line) {
		System.out.println(line);
		return null;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
