package model;

import java.util.ArrayList;

public class Tournament extends Story{
	private String name;
	private int bonus;
	private int state;
	private ArrayList<Player> participants;
	
	public Tournament(String n, int b) {
		name = n;
		bonus = b;
		state = CardStates.FACE_DOWN;
		participants = new ArrayList<Player>();
	}
	
	public String getName() {
		return name;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int s) {
		state = s;
	}
	
	public void addParticipant(Player p) {
		participants.add(p);
	}
	
	//public void askPlayer()
}
