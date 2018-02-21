package model;

import java.util.ArrayList;

public class Story implements Card{

	private String name;
	private int state;
	
	// new changes I (Ben) made, trying to reduce code for Quest/Tournament through inheritance
	/*private ArrayList<Player> participants;
	
	public void addParticipant(Player p) {
		participants.add(p);
	}*/
	// end changes -----------------------------
	
	public String getName() {
		return name;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int s) {
		state = s;
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
