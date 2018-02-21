package model;

public class Tournament extends Story{
	private String name;
	private int bonus;
	private int state;
	
	public Tournament(String n, int b) {
		name = n;
		bonus = b;
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
