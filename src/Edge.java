import java.util.ArrayList;

public class Edge {
	public int x, y;
	boolean h_v; // true if horizontal edge, false if vertical
	boolean blocked;
	
	public Edge(int x ,int y, boolean h_v) {
		this.x= x;
		this.y= y;
		this.h_v= h_v;
		this.blocked= true;
	}
	
	public int get_next_x() {
		return h_v ? x: x+1;
	}
	public int get_next_y() {
		return h_v ? y+1: y;
	}
	
	@Override
	public String toString() {
		return blocked?(h_v?"---":" | "):"   ";
	}
}
