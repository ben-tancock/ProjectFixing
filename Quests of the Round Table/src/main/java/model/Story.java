package model;

public class Story implements Card{

	private String name;
	private int state;
	
	public String getName() {
		return name;
	}
	
	public int getState() {
		return state;
	}
	
	public void displayAttributes() {
		
	}
	
	public void displayName() {
		System.out.println("Name:" + name + "\n");
	}
	
	public String toString() {
		return this.getName();
	}

}
