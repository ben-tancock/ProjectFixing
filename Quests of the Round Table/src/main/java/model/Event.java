package model;

public class Event extends Story{
	String name;
	int state;
	
	public Event(String n) {
		name = n;
		state = CardStates.FACE_DOWN;
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
}