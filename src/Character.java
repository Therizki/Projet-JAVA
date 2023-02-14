import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;


public class Character {
	
	public type t;
	public Utils.Coordinates coords;
	
	public Character(type t, Utils.Coordinates coords) {
		this.t= t;
		this.coords= coords;
	}
	
	public void interact(Labyrinth labyrinth, Quest quest) {
		switch(this.t) {
		case DESTINATION:
			if(labyrinth.quest.IsFinished()) {
				System.out.println("You Won !");
				labyrinth.gameGoing= false;
			}
			else {
				System.out.println("You are on the right cell but you haven't finished the quest yet !");
			}
			break;
		case SPHYNX:
			System.out.println("You found a Sphynx !");
			Riddles.Riddle riddle= labyrinth.riddles.pop();
			while(true) {
				
				System.out.println(riddle);
				Scanner in = new Scanner(System.in);
				int choice= Integer.parseInt(in.nextLine());
				try {
					if(riddle.isCorrect(choice)) {
						System.out.println("Correct Answer !");
						System.out.println(labyrinth.maze.GetHint(false, quest));
					}
					else{
						System.out.println("Wrong Answer !");
					}
					break;
				}
				catch(Exception e){
					System.out.println("Pick a correct choice");
				}
			}
			labyrinth.quest.personnages_rencontres ++;
			break;
		case MARCHAND:
			System.out.println("You found a Merchant !");
			System.out.println("Do you want to buy a hint for 1 Gold ?\n0: Yes 1: No");

			Scanner in = new Scanner(System.in);
			int choice= Integer.parseInt(in.nextLine());
			try {
				if(choice == 0) {
					if(labyrinth.quest.or_amasse >= 1) {
						labyrinth.quest.or --;
						System.out.println(labyrinth.maze.GetHint(false, quest));
					}
					else
						System.out.println("Not Enough Gold !");
				}
				break;
			}
			catch(Exception e){
				System.out.println("Pick a correct choice");
			}
			labyrinth.quest.personnages_rencontres ++;
			break;
		case ALTRUISTE:
			System.out.println("You found an Altruist !");
			System.out.println(labyrinth.maze.GetHint(false, quest));
			labyrinth.quest.personnages_rencontres ++;
			break;
		case FOU:
			System.out.println("You found an Altruist !");
			System.out.println(labyrinth.maze.GetHint(true, quest));
			labyrinth.quest.personnages_rencontres ++;
			break;
		case PARCHEMIN:
			System.out.println("You found a Parchment !");
			labyrinth.quest.parchemins_recoltes ++;
			break;
		case JOYAU:
			System.out.println("You found a Jewel !");
			labyrinth.quest.joyaux_recoltes ++;
			break;
		default:
			break;
		}
	}
	
	@Override
	public String toString() {
		switch(t) {
			case CHAMPION:
				return "C";
			case SPHYNX:
				return "S";
			case MARCHAND:
				return "M";
			case ALTRUISTE:
	//			return "A";
			case FOU:
				return "A"; // Fou and Altruiste will have the same look
			case PARCHEMIN:
				return "P";
			case JOYAU:
				return "J";
			case DESTINATION:
				return "D";
				
		}
		return null;
	}
}
