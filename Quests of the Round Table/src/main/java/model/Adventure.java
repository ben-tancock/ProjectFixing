package model;

public abstract class Adventure implements Card{
	
	private String name;
	private int state;
	private int battlePoints;
	
	public String getName() {
		return name;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int s){
		state = s;
	}
	
	public void displayAttributes() {
		
	}
	
	public void displayName() {
		System.out.println("Name:" + name + "\n");
	}
	
	public int getBattlePoint() {
		return battlePoints;
	}
	
	public String toString() {
		return this.getName();
	}
	
}
