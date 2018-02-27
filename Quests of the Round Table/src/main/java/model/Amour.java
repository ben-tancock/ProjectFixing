package model;

public class Amour extends Adventure{
	private String name;
	private int bp;
	private int state;
	
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
	
	public int getBattlePoints() {
		return bp;
	}
}