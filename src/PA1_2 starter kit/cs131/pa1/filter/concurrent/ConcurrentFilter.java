package cs131.pa1.filter.concurrent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import cs131.pa1.filter.Filter;


public abstract class ConcurrentFilter extends Filter implements Runnable{
	
	static final String terminate = "TERMINATE";
	protected LinkedBlockingQueue<String> input;
	protected LinkedBlockingQueue<String> output;
	protected boolean finish = false;
	
	@Override
	public void setPrevFilter(Filter prevFilter) {
		prevFilter.setNextFilter(this);
	}
	
	@Override
	public void setNextFilter(Filter nextFilter) {
		if (nextFilter instanceof ConcurrentFilter){
			ConcurrentFilter sequentialNext = (ConcurrentFilter) nextFilter;
			this.next = sequentialNext;
			sequentialNext.prev = this;
			if (this.output == null){
				this.output = new LinkedBlockingQueue<String>();
			}
			sequentialNext.input = this.output;
		} else {
			throw new RuntimeException("Should not attempt to link dissimilar filter types.");
		}
	}
	
	public void process(){
		String line = null;
		while(line!=terminate) {
			if (line != null) {
				String processedLine = processLine(line);
				if (processedLine != null) {
					try {
						output.put(processedLine);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					line = input.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		kill();
	}
	
	public Filter getNext() {
		return next;
	}
	
	@Override
	public boolean isDone() {
		return finish;
	}
	
	public void Run(){
		this.process();
	}
	
	public void kill() {
		try {
			output.put(terminate);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finish=true;
	}
	
	protected abstract String processLine(String line);
	
}
