package model;

import java.util.ArrayList;
import model.Player.Person;

public class Quest extends Story {
	private String name;
	private ArrayList<Stage> stages;
	private Person sponsor;
	private ArrayList<Person> participants;
	private final int numStages;
	private int state;
	
	public Quest(String n, int numStgs) {
		name = n;
		state = CardStates.FACE_DOWN;
		numStages = numStgs;
		sponsor = null;
		stages = new ArrayList<Stage>();
		participants = new ArrayList<Person>();
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
	
	public void addStage(Stage s) throws Exception {
		if(stages.size() < numStages) {
			stages.add(s);
		} else {
			throw new Exception("Trying to add too many stages.");
		}
	}
	
	public void setState(int s) throws Exception {
		if(s != CardStates.FACE_DOWN || s!= CardStates.FACE_UP) {
			throw new Exception("Card State is invalid.");
		} else {
			state = s;
		}
	}
	
	public void setSponsor(Person s) {
		sponsor = s;
	}
	
	public void addParticipant(Person p) {
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