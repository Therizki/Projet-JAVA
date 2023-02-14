import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {

	public static class Coordinates{
		public int x, y;
		public Coordinates(int x, int y) {
			this.x= x;
			this.y= y;
		}
		@Override
		public boolean equals(Object obj) {
	        if (obj == null) {
	            return false;
	        }
	        final Coordinates other = (Coordinates) obj;
	        
	        if (this.x == other.x && this.y == other.y) {
	            return true;
	        }

	        return false;
		}
		
		@Override
		public String toString() {
			return "(" + x + " " +y+ ")" ;
		}
	}
	
	public static int get_edge_index(int width, int height, int x, int y, boolean h_v) { // gets the index from the list ( static function)
		return (h_v? 1 : 0)* (width -1 )*height + y * (width - (h_v? 0 : 1) ) + x ;
	}
	public static int get_edge_index(int width, int height, Coordinates coord1, Coordinates coord2) { // gets the index from the list ( static function)
		if(coord1.x < coord2.x)
			return get_edge_index(width, height, coord1.x, coord1.y, false);
		else if(coord1.x > coord2.x)
			return get_edge_index(width, height, coord2.x, coord2.y, false);
		else {
			if(coord1.y < coord2.y)
				return get_edge_index(width, height, coord1.x, coord1.y, true);
			else if(coord1.y > coord2.y)
				return get_edge_index(width, height, coord2.x, coord2.y, true);
		}
		return 0;
	}
	public static List<Coordinates> get_n_random_distinct_coordinates(int n, int width, int height){
		List<Coordinates> coords= new ArrayList<Coordinates>();
		for(int i= 0; i<width; i++) {
			for(int j= 0; j<height; j++) {
				coords.add(new Coordinates(i, j));
			}
		}
		Collections.shuffle(coords); // we shuffle our edges list

		return coords.subList(0, n);
		
	}
	
	public static dir getOppositeDir(dir d) {
		if(d== dir.DOWN)
			return dir.UP;
		if(d== dir.UP)
			return dir.DOWN;
		if(d== dir.LEFT)
			return dir.RIGHT;
		if(d== dir.RIGHT)
			return dir.LEFT;
		return null;
	}
	
	public static boolean containsTile(List<Tile> array, Tile tile) {
		for(int i=0 ; i<array.size(); i++) {
//			System.out.println("checking: "+array.get(i).coords+ " " +tile.coords+" : " + (array.get(i).coords == tile.coords));
			if (array.get(i).coords.equals(tile.coords) ) {
				return true;
				
			}
		}
		return false;
	}
}
