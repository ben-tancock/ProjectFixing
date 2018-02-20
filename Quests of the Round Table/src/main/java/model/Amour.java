package model;

public class Amour extends Adventure{
	String name;
	int bp;
	int state;
	
	public Amour(String n, int b) {
		name = n;
		bp = b;
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