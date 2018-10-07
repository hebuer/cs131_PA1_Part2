package cs131.pa1.filter.concurrent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import cs131.pa1.filter.Filter;


public abstract class ConcurrentFilter extends Filter implements Runnable{
	
	protected LinkedBlockingQueue<String> input;
	protected LinkedBlockingQueue<String> output;
	boolean detectBug = false;
	protected String terminate = "needToBeTerminate";
	
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
		while(input.peek()!=terminate && !detectBug) {
			String wait;
			try {
				if(!(input.peek() instanceof String && input.peek()== terminate)) {
					wait = input.take();
					String line = processLine(wait);
					if(line!=null) output.put(line);
				}
			}catch(InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				output.put(terminate);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean isDone() {
		if(input.peek() instanceof String) return input.peek()==terminate;
		else return false;
	}
	
	public void Run(){
		this.process();
	}
	
	protected abstract String processLine(String line);
	
}
