package model;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tournament extends Story{
	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 7283184923215419552L;
	private String name;
	private int bonus;
	private int state;
	private ArrayList<Player> participants;
	public boolean tournament;
	
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
	
	public ArrayList<Player> getParticipants(){
		return participants;
	}
	
	public void setState(int s) {
		state = s;
	}
	
	public void addParticipant(Player p) {
		participants.add(p);
	}
	
	public int getBonus() {
		return bonus;
	}
	
	//public void askPlayer()
}