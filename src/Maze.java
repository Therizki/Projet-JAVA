import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;



public class Maze {
	public ArrayList<ArrayList<String>> grid = new ArrayList<ArrayList<String>>();
	public ArrayList<Edge> edges = new ArrayList<Edge>();
	public int width, height;
	Character player;
	List<Character> objects;
	
	public void GenerateMaze() {
		int choice= ThreadLocalRandom.current().nextInt(2);
		switch(choice) {
		case 0:
			Prim();
			break;
		case 1:
			Kruskal();
			break;
		}
	}
	public void Prim() {
		//http://weblog.jamisbuck.org/2011/1/10/maze-generation-prim-s-algorithm
		Scanner in = new Scanner(System.in);
		Utils.Coordinates coords= new Utils.Coordinates(ThreadLocalRandom.current().nextInt(width), ThreadLocalRandom.current().nextInt(height));
		List<Tile> frontier = new ArrayList<Tile>();
		List<Tile> visited = new ArrayList<Tile>();
		List<Tile> adj;
		
		frontier.add(new Tile(coords));
		
		while(frontier.size()!= 0) {
			int index= ThreadLocalRandom.current().nextInt(frontier.size());
			Tile randTile= frontier.remove(index);
			visited.add(randTile);

			
			if(randTile.lastCoords != null)
				edges.get(Utils.get_edge_index(width, height, randTile.coords, randTile.lastCoords)).blocked= false;
			
			adj=  GetAdjacentTiles(randTile);
			


			List<Tile> tmp = new ArrayList<Tile>();
			
			for(int i=0 ; i<adj.size(); i++) {
				boolean found = false;
				// we won t add something already existing in the frontier
				if (!Utils.containsTile(frontier, adj.get(i)) && !Utils.containsTile(visited, adj.get(i)) ) {
					tmp.add(adj.get(i));
				}
			}
			frontier.addAll(tmp);

		}
	}

	public void Kruskal() {
		//http://weblog.jamisbuck.org/2011/1/3/maze-generation-kruskal-s-algorithm
		ArrayList<Edge> shuffled_edges = new ArrayList<>(edges);
		
		Collections.shuffle(shuffled_edges); // we shuffle our edges list

		// we initialize our edgeSets
		ArrayList<ArrayList<EdgeSet>> edgeSets = new ArrayList<ArrayList<EdgeSet>>();
		for (int i= 0; i<width; i++) {
			edgeSets.add(new ArrayList<EdgeSet>());
			for (int j= 0; j<height; j++) {
				edgeSets.get(i).add(new EdgeSet());
			}
		}

		for(int i= 0; i<shuffled_edges.size(); i++) {
			int x, y, nx, ny;
			EdgeSet set1, set2;
			
			x= shuffled_edges.get(i).x;
			y= shuffled_edges.get(i).y;
			nx= shuffled_edges.get(i).get_next_x();
			ny= shuffled_edges.get(i).get_next_y();
			
			set1= edgeSets.get(x).get(y);
			set2= edgeSets.get(nx).get(ny);
			
			if(!set1.IsConnected(set2))
			{
				set1.connect(set2);
				shuffled_edges.get(i).blocked= false;
			}
		}
	}
	
	public Maze(Character player, List<Character> objects, int width, int height) {
		this.player= player;
		this.objects= objects;
		this.width= width;
		this.height= height;
		
		// initialazing the grid 
		for (int i= 0; i<width; i++) {
			grid.add(new ArrayList<String>());
			for (int j= 0; j<height; j++) {
				grid.get(i).add(" ");
			}
		}
//		grid.get(i);
		grid.get(player.coords.x).set(player.coords.y, player.toString());
		for(Character obj : objects){
			grid.get(obj.coords.x).set(obj.coords.y, obj.toString());
		}
		
		// initializing the edges
		for(int k= 0; k<2; k++) {
			for (int i= 0; i<height -k; i++) {
				for (int j= 0; j<width -1 +k ; j++) {
					 // if k == 0 this will go to j< width-1 and i < height  so we talkign abt vertical edges
					 // if k == 1 this will go to j< width and i < height-1  so we talkign abt horizontal edges
					edges.add(new Edge(j, i, !(k==0)));
				}
			}
		}
		// the edges will be a list of the (width-1)*height vertical edges first then the width*(height-1) horizontal edges

		
		
	}
	
	public void Print() {
		for (int i= 0; i<height; i++) {
			
			// printing the horizontal edges.
			for (int j= 0; j<width; j++) {
				System.out.print("+" + ( i==0? "---" : edges.get(Utils.get_edge_index(width, height, j, i-1 , true))) );
			}
			System.out.println("+");
			
			// printing the inside vertical edges and grid value.
			
			for (int j= 0; j<width; j++) {
					System.out.print( (j== 0?"| ": edges.get(Utils.get_edge_index(width, height, j-1, i, false))) + grid.get(j).get(i));

			}
			System.out.println(" | ");
			
		} 
		
		// printing the last line
		for (int j= 0; j<width; j++) {
			System.out.print("+---");
		}
		System.out.println("+");
	}

	public void Print(int x, int y) {
		
		for (int i= Math.max(0, 1-y); i<Math.min(3, height - y + 1 ); i++) {
			
			// this is just to print it better
			if(x > 1)
				System.out.print(" ");
			// printing the horizontal edges.
			for (int j= Math.max(0, 1-x); j< Math.min(3, width + 1 -x); j++) {
				System.out.print("+" + ( y +i -2==-1 ? "---" : edges.get(Utils.get_edge_index(width, height, x- 1 + j, y +i-2 , true))) );
			}
			System.out.println("+");
			
			// printing the inside vertical edges and grid value.
			
			for (int j= Math.max(0, 1-x); j< Math.min(3, width - x +1); j++) {
				int xj, yi;
				xj=  x-2+j;
				yi=  y+i-1;
//				System.out.println(xj+ " " + yi);
				System.out.print( (xj== -1?"| ": edges.get(Utils.get_edge_index(width, height, xj, yi, false))) + grid.get(xj+1).get( yi));
			}
//			System.out.println((x + 1) + " "+ (y+i-1)+ " " + Math.min(3, width - x +1));
			System.out.println( (x>=width -2 )? " | " : edges.get(Utils.get_edge_index(width, height, x + 1, y+i-1, false)));
			
		}

		if(x > 1)
			System.out.print(" ");
		// printing the last line
		for (int j= Math.max(0, 1-x); j< Math.min(3, width - x +1); j++) {
			System.out.print("+" + ( y - 2+ Math.min(3, height - y + 1 ) == height-1 ? "---" : edges.get(Utils.get_edge_index(width, height, x- 1 + j, y - 2+ Math.min(3, height - y + 1 ) , true))) );
		}
		System.out.println("+");
	}

	public boolean Move(dir direction) {
		switch(direction) {
			case UP:
				if(player.coords.y != 0){
					if(! edges.get(Utils.get_edge_index(width, height, player.coords.x, player.coords.y- 1 , true)).blocked) {
						// 
						UpdateUserPos(0, -1);

						return true;
					}
				}
					
				break;
			case DOWN:
				if(player.coords.y != height- 1){
					if(! edges.get(Utils.get_edge_index(width, height, player.coords.x, player.coords.y , true)).blocked) {
						// 
						UpdateUserPos(0, 1);

						return true;
					}
				}
					
				break;
			case RIGHT:
				if(player.coords.x != width- 1){
					if(! edges.get(Utils.get_edge_index(width, height, player.coords.x, player.coords.y , false)).blocked) {
						// 
						UpdateUserPos(1, 0);

						return true;
					}
				}
					
				break;
			case LEFT:
				if(player.coords.x != 0){
					if(! edges.get(Utils.get_edge_index(width, height, player.coords.x- 1, player.coords.y , false)).blocked) {
						// 
						UpdateUserPos(-1, 0);

						return true;
					}
				}
					
				break;
		}
		return false;
	}
	
	public void UpdateUserPos(int nx, int ny) {
		// freeing that pos from the character
		grid.get(player.coords.x).set(player.coords.y, " ");
		
		grid.get(player.coords.x + nx).set(player.coords.y + ny, "C");
		
		player.coords.x= player.coords.x + nx;
		player.coords.y= player.coords.y + ny;
	}
	
	public Character PlayerCollision() {
		for(int i= 0; i<objects.size(); i++) {
			if (player.coords.equals(objects.get(i).coords))
				return objects.remove(i);
		}
		return null;
	}
	
	public String GetHint(boolean fou, Quest quest) {
		int choice= ThreadLocalRandom.current().nextInt(0, 4);
		switch(choice) {
		case 0:
			Tile tile= ClosestDirection();
			if(!fou)
				return "The shortest way to get to the destination is: "+ tile.startingDir;
			else
				return "The shortest way to get to the destination is: "+ Utils.getOppositeDir(tile.startingDir);
		case 1:
			Tile tile1= ClosestDirection();
			if(!fou)
				return "The destination is "+ tile1.distance + " tiles far";
			else
				return "The destination is "+ (tile1.distance + ThreadLocalRandom.current().nextInt(1, 10)) + " tiles far";
		case 2:			
		if(!fou)
			return "The destination is at "+ objects.get(objects.size()- 1)+" and you are at " +player.coords;
		else {
			List<Utils.Coordinates> coords=  Utils.get_n_random_distinct_coordinates(2, width, height);
			return "The destination is at"+ coords.get(0) + " and you are at "+ coords.get(1);
		}
		case 3:	
			if(!fou)
				return "The Quest is to have "+ quest.or+" Gold and meet " +quest.personnages_a_rencontrer+ " People";
			else {
				return "The Quest is to have "+ ThreadLocalRandom.current().nextInt(0, 10)+" Gold and meet " +ThreadLocalRandom.current().nextInt(0, 10)+ " People";
			}
		}
		Tile tile= ClosestDirection();
		return "Hint";
	}
	// BFS algorithm 
	public Tile ClosestDirection() {
		Queue<Tile> queue = new PriorityQueue<Tile>();
		Tile tile;
		// get adj to player 1st
		List<Utils.Coordinates> adj= GetAdjacentCoords(player.coords);
		// we create the 1st adj tiles to make be able to add the dir to it and we push them in the queue;
		for(int i= 0; i<adj.size(); i++) {
			int xdiff, ydiff;
			xdiff= player.coords.x - adj.get(i).x;
			ydiff= player.coords.y - adj.get(i).y;
			if(xdiff== 0 && ydiff == 1) {
				queue.add(new Tile(adj.get(i), dir.UP, player.coords));
			}
			else if (xdiff== 0 && ydiff == -1) {
				queue.add(new Tile(adj.get(i), dir.DOWN, player.coords));
			}
			else if (xdiff== 1 && ydiff == 0) {
				queue.add(new Tile(adj.get(i), dir.LEFT, player.coords));
			}
			else if (xdiff== -1 && ydiff == 0) {
				queue.add(new Tile(adj.get(i), dir.RIGHT, player.coords));
			}
		}
		while(true) {
			tile= queue.poll();
			
//			System.out.println(tile.distance + " " + tile.coords + " " + tile.startingDir);
			if(tile.coords.equals(objects.get(objects.size()- 1).coords)) // we know the destination is the last item in the object list
				break;
			adj= GetAdjacentCoords(tile.coords);
			
			for(int i= 0; i<adj.size(); i++) {
				// we skip the last coord visited to reduce useless iterations
				if(adj.get(i).equals(tile.lastCoords))
					continue;
				// we add the next adj tiles not already visited in the same 
				queue.add(new Tile(adj.get(i), tile));
//				System.out.println(adj);
			}
		}
		return tile;
	}
	
	//gets adjacent coords that are reachable (takes into consideration the edges)
	public List<Utils.Coordinates> GetAdjacentCoords(Utils.Coordinates coords) {
		List<Utils.Coordinates> adj= new ArrayList<Utils.Coordinates>();

		if(coords.y != 0){
			if(! edges.get(Utils.get_edge_index(width, height, coords.x, coords.y- 1 , true)).blocked) {
				// ;
				adj.add(new Utils.Coordinates(coords.x, coords.y -1));
			}
		}
		if(coords.y != height- 1){
			if(! edges.get(Utils.get_edge_index(width, height, coords.x, coords.y , true)).blocked) {
				// 
				adj.add(new Utils.Coordinates(coords.x, coords.y +1));
			}
		}
		if(coords.x != width- 1){
			if(! edges.get(Utils.get_edge_index(width, height, coords.x, coords.y , false)).blocked) {
				// 
				adj.add(new Utils.Coordinates(coords.x +1, coords.y ));
			}
		}
		if(coords.x != 0){
			if(! edges.get(Utils.get_edge_index(width, height, coords.x- 1, coords.y , false)).blocked) {

				adj.add(new Utils.Coordinates(coords.x-1, coords.y ));
			}
		}
		return adj;
	}
	
	// will return adjacent tiles different from the last tile visited
	public List<Tile> GetAdjacentTiles(Tile tile) {
		List<Tile> adj= new ArrayList<Tile>();

		if(tile.coords.y != 0){
			Utils.Coordinates coords= new Utils.Coordinates(tile.coords.x, tile.coords.y -1);
			if(coords != tile.coords)
				adj.add(new Tile(coords, tile.coords));
		}
		if(tile.coords.y != height- 1){
			Utils.Coordinates coords= new Utils.Coordinates(tile.coords.x, tile.coords.y +1);
			if(coords != tile.coords)
				adj.add(new Tile(coords, tile.coords));
		}
		if(tile.coords.x != width- 1){
			Utils.Coordinates coords= new Utils.Coordinates(tile.coords.x +1, tile.coords.y);
			if(coords != tile.coords)
				adj.add(new Tile(coords, tile.coords));
		}
		if(tile.coords.x != 0){
			Utils.Coordinates coords= new Utils.Coordinates(tile.coords.x -1, tile.coords.y);
			if(coords != tile.coords)
				adj.add(new Tile(coords, tile.coords));
		}
		return adj;
	}

	
}
