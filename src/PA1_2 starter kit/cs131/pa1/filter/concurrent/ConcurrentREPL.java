package cs131.pa1.filter.concurrent;

import cs131.pa1.filter.Message;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ConcurrentREPL {

	static String currentWorkingDirectory;
	static List<BackGround> runningCommands;
	
	public static void main(String[] args){
		currentWorkingDirectory = System.getProperty("user.dir");
		Scanner s = new Scanner(System.in);
		System.out.print(Message.WELCOME);
		String command;
		runningCommands = new LinkedList<BackGround>();
		while(true) {
			//obtaining the command from the user
			System.out.print(Message.NEWCOMMAND);
			command = s.nextLine();
			if(command.equals("exit")) {
				break;
			} else if (command.equals("repl_jobs")) {
				int index = 0;
				for (int i = runningCommands.size() - 1; i >= 0; i--) {
					BackGround current = runningCommands.get(index);
					if (current.thread.getState() == Thread.State.TERMINATED) {
						runningCommands.remove(index);
						continue;
					}
					index += 1;
					System.out.println("\t" + index + ". " + current.command.trim());
				}
			}else if(!command.trim().equals("")) {
				//building the filters list from the command
				ConcurrentFilter filterlist = ConcurrentCommandBuilder.createFiltersFromCommand(command);
				Thread current = null;
				while(filterlist != null) {
					current = new Thread(filterlist);
					current.start();
					filterlist = (ConcurrentFilter) filterlist.getNext();
				}
				if(current!=null) {
					runningCommands.add(new BackGround(current, command));
					if (!command.substring(command.length() - 1).trim().equals("&")) {
							try {
								current.join();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}
				}
			}
		}
		s.close();
		System.out.print(Message.GOODBYE);
	}

}
