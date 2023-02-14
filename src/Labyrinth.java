import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;


public class Labyrinth {

	boolean gameGoing= true; 
	int width= 4, height= 5;
	int[] count;
	Quest quest= new Quest(0 ,0);
	Maze maze;
	
	List<Character> objects= new ArrayList<Character>();
	Riddles riddles;
	
	Character player;
	EnumMap<type, Integer> map;
	
	
	public Labyrinth() {
		map = new EnumMap<type, Integer>(type.class);
		count = new int[]{ 	1,	//SPHYNX
							2,	//MARCHAND
							1,	//ALTRUISTE
							1,	//FOU
							3,	//PARCHEMIN
							3,	//JOYAU
							1}; 	//DESTINATION

		// game parameters
		map.put(type.SPHYNX, count[0]);
		map.put(type.MARCHAND, count[1]);
		map.put(type.ALTRUISTE, count[2]);
		map.put(type.FOU, count[3]);
		map.put(type.PARCHEMIN, count[4]);
		map.put(type.JOYAU, count[5]);
		map.put(type.DESTINATION, count[6]);
		
		int n = IntStream.of(count).sum() + 2;
		
		List<Utils.Coordinates> coords= Utils.get_n_random_distinct_coordinates(n, width, height);
		
		
		player= new Character(type.CHAMPION, coords.get(0));
		
		int index= 1;
		for(type t :map.keySet()) {
			for(int j= 0; j<map.get(t); j++) {
				objects.add(new Character(t, coords.get(index)));
				index++;
			}
		}

		maze = new Maze(player, objects, width, height);
		riddles= new Riddles();
		
	}
	
	public void play() {
		Scanner in = new Scanner(System.in);
		
		// start of game
		// get number of objects / caracters
		
		maze.GenerateMaze();

		// uncomment to see what the map looks like
//		maze.Print();
		maze.ClosestDirection();
		
		int choice;
		while(gameGoing) {
			System.out.println("1: Look Around 2: Move 3: Check Stats");
			try {
				choice= Integer.parseInt(in.nextLine());
				switch(choice) {
					case 1:
						maze.Print(player.coords.x, player.coords.y);
						break;
					case 2:
						System.out.println("Choose Direction: \n0: UP, 1: DOWN, 2: RIGHT, 3: LEFT");
						choice= Integer.parseInt(in.nextLine());
						if(choice < 0 || choice > 3) {
							System.out.println("Pick a correct choice");
							continue;
						}
						// move character if it's possible
						if(! maze.Move(dir.values()[choice])) {
							System.out.println("There is a wall in front of you !");
							continue;
						}
						Character collision;
						collision= maze.PlayerCollision();
						
						if(collision != null) {
							collision.interact(this, quest);
						}
						
						break;
					case 3:
						System.out.println(	"Gold: " + quest.or_amasse + 
											" Parchments: " + quest.parchemins_recoltes + 
											" Jewels: " + quest.joyaux_recoltes + 
											" People Met: " + quest.personnages_rencontres);
						if(quest.parchemins_recoltes>0)
						{
							while(true) {
								System.out.println("Would you like to use a Parchment ?\n0: Yes 1: No");
								try {
									choice= Integer.parseInt(in.nextLine());
									if(choice == 0) {
											quest.parchemins_recoltes --;
											System.out.println(maze.GetHint(false, quest));
										}
									break;
								}
								catch(Exception e){
									System.out.println("Pick a correct choice");
								}
							}
						}
						if(quest.joyaux_recoltes>0)
						{
							while(true) {
								System.out.println("Would you like to use a Jewel ?\n0: Yes 1: No");
								try {
									choice= Integer.parseInt(in.nextLine());
									if(choice == 0) {
											quest.joyaux_recoltes --;
											int randGold= ThreadLocalRandom.current().nextInt(1, 10); 
											quest.or_amasse+= randGold; 
											System.out.println("You got " + randGold + " Gold !");
										}
									break;
								}
								catch(Exception e){
									System.out.println("Pick a correct choice");
								}
							}
						}
						break;
					default:
						System.out.println("Pick a correct choice");
						continue;
				}
			}
			catch (NumberFormatException  e){
				System.out.println("Pick a correct choice");
				continue;
			}
		    
		}
	}

}
