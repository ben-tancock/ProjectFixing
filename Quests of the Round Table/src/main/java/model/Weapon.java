package model;

public class Weapon extends Adventure{
	private String name;
	private int battlepoints;
	private int state;
	
	public Weapon(String n, int bp) {
		name = n;
		battlepoints = bp;
		state = CardStates.FACE_DOWN;
	}
		
	public String getName() {
		return name;
	}
	
	public int getBattlePoints() {
		return battlepoints;
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