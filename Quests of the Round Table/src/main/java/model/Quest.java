package model;

import java.util.ArrayList;

public class Quest extends Story {
	private String name;
	private ArrayList<Stage> stages;
	private final int numStages;
	private int state;
	
	public Quest(String n, int numStgs) {
		name = n;
		state = CardStates.FACE_DOWN;
		numStages = numStgs;
		stages = new ArrayList<Stage>();
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumStages() {
		return numStages;
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
}