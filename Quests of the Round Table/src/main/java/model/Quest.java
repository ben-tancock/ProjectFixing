package model;

import java.util.ArrayList;

public class Quest extends Story {
	private String name;
	private ArrayList<Stage> stages;
	private Player sponsor;
	private ArrayList<Player> participants;
	private String specialFoes;
	private final int numStages;
	private int state;
	
	public Quest(String n, int numStgs, String spFs) {
		name = n;
		state = CardStates.FACE_DOWN;
		numStages = numStgs;
		sponsor = null;
		stages = new ArrayList<Stage>();
		participants = new ArrayList<Player>();
		specialFoes = spFs;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumStages() {
		return numStages;
	}
	
	public ArrayList<Stage> getStages() {
		return stages;
	}
	
	public int getState() {
		return state;
	}
	
	public String getSpecialFoes() {
		return specialFoes;
	}
	
	public void addStage(Stage s) {	
		//Check to see if the test of the questing beast is played in the search for the questing beast.
		if(s.getTest() != null && s.getTest().getName().equals("test_of_the_questing_beast") && name.equals("search_for_the_questing_beast")) {
			s.getTest().setMinBid(4);
			s = new Stage(s.getTest());
		}
		
		stages.add(s);
	}
	
	public void setState(int s) {
		state = s;
	}
	
	public void setSponsor(Player s) {
		sponsor = s;
	}
	
	public void addParticipant(Player p) {
		participants.add(p);
	}
	
	public String toString() {
		String s = "";
		s += getName() + ": " + System.getProperty("line.separator");
		for(int i = 1; i <= numStages; i++) {
			s += "Stage: " + i + " " + stages.get(i-1).toString() + System.getProperty("line.separator");
		}
		return s;
	}
}