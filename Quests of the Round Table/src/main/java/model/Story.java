package model;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Story implements Card, Serializable{

	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 1815140237857098763L;
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
