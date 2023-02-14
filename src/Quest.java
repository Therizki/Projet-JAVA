
public class Quest {
	public int or;
	public int personnages_a_rencontrer;
	
	public int or_amasse= 0;
	public int personnages_rencontres= 0;
	
	public int parchemins_recoltes= 0;
	public int joyaux_recoltes= 0;
	
	public Quest(int or, int personnages_a_rencontrer) {
		this.or= or;
		this.personnages_a_rencontrer= personnages_a_rencontrer;
	}
	
	public boolean IsFinished() {
		return (or_amasse >= or && personnages_rencontres >=personnages_a_rencontrer);
	}
}
