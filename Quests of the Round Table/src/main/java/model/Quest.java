package model;

public class Quest extends Story {
	private String name;
	private int stages;
	private int state;
	
	public Quest(String n, int s) {
		name = n;
		stages = s;
		state = CardStates.FACE_DOWN;
	}
	
	public String getName() {
		return name;
	}
	
	public int getNumStages() {
		return stages;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int s) throws Exception {
		if(s != CardStates.FACE_DOWN || s!= CardStates.FACE_UP) {
			throw new Exception("Card State is invalid.");
		} else {
			state = s;
		}
	}
}