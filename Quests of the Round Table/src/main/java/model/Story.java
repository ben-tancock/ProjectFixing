package model;

public class Story implements Card{

	private String name;
	public String getName() {
		return name;
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
