public class Tile implements Comparable<Tile> {
	Utils.Coordinates coords;
	int distance;
	dir startingDir;
	Utils.Coordinates lastCoords; 
	
	public Tile(Utils.Coordinates coords, dir startingDir, Utils.Coordinates lastCoords) {
		this.coords= coords;
		this.distance= 1;
		this.startingDir= startingDir;
		this.lastCoords= lastCoords;
	}
	
	public Tile(Utils.Coordinates coords, Utils.Coordinates lastCoords) {
		this.coords= coords;
		this.lastCoords= lastCoords;
	}	
	public Tile(Utils.Coordinates coords) {
		this.coords= coords;
	}
	
	public Tile(Utils.Coordinates adj, Tile tile) {
		this.coords= adj;
		this.distance= tile.distance+1;
		this.startingDir= tile.startingDir;
		this.lastCoords= tile.coords;
	}
	
	@Override
	public int compareTo(Tile o) {
		if(this.distance > o.distance)
			return 1;
		else if (this.distance < o.distance)
			return -1;
		return 0;
	}
	
}
