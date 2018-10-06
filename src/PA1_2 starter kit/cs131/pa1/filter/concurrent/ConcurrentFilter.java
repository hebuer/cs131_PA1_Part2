package cs131.pa1.filter.concurrent;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import cs131.pa1.filter.Filter;


public abstract class ConcurrentFilter extends Filter implements Runnable{
	
	protected LinkedBlockingQueue<String> input;
	protected LinkedBlockingQueue<String> output;
	
	public void Run(){
		this.process();
	}
	
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
	
	public Filter getNext() {
		return next;
	}
	
	public void process(){
		while (!input.isEmpty()){
			String line = input.poll();
			String processedLine = processLine(line);
			if (processedLine != null){
				output.add(processedLine);
			}
		}	
	}
	
	@Override
	public boolean isDone() {
		//re-write
		return input.size() == 0;
	}
	
	protected abstract String processLine(String line);
	
}
