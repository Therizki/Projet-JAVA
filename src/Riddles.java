import java.util.PriorityQueue;
import java.util.Queue;


public class Riddles {
	
	public class Riddle implements Comparable<Riddle>{
		String question;
		String[] options;
		int correctAnswer;
		public Riddle(String question,String[] options,int correctAnswer) {
			this.question= question;
			this.options= options;
			this.correctAnswer= correctAnswer;
		}
		
		public boolean isCorrect(int chosenOption) {
			return chosenOption==correctAnswer;
		}
		
		@Override
		public String toString() {
			String str= "For a Hint, Answer my question:\n" + question +"\n";
			for(int i= 0; i<options.length; i++) {
				str+= i + ": " +options[i] + " ";
			}
			return str;
		}

		@Override
		public int compareTo(Riddles.Riddle o) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
	Queue<Riddle> queue = new PriorityQueue<Riddle>();
	
	public Riddles() {
// here to add new riddles
		queue.add(new Riddle("Do I deserve a tip 1?", new String[]{"Yes", "No"}, 0));
		queue.add(new Riddle("Do I deserve a tip 2?", new String[]{"Yes", "No"}, 0));
		queue.add(new Riddle("Do I deserve a tip 3?", new String[]{"Yes", "No"}, 0));
	}
	
	public Riddle pop() {
		return queue.poll();
	}
	
	
}
