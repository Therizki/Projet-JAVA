
public class EdgeSet {
	// either a int or a 
	public EdgeSet parent;
	
	public EdgeSet() {
		parent = null;
	}
	
	public EdgeSet root() {
		return parent != null ? parent.root() : this;
	}
	
	public boolean IsConnected(EdgeSet e) {
		return this.root() == e.root();
		//return (this.parent ==null ? false: parent== e.parent );
	}
	
	public void connect(EdgeSet e) {
		e.root().parent= this;
	}
	
	
}
