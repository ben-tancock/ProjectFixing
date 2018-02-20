package model;

public class Ally extends Adventure{
	String name;
	int bp;
	int bids;
	int state;
	
	public Ally(String n, int b, int bs) {
		name = n;
		bp = b;
		bids = bs;
		state = CardStates.FACE_DOWN;
	}
	
	public String getName() {
		return name;
	}
	
	public int getBattlePoints() {
		return bp;
	}
	
	public int getBids() {
		return bids;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int s) {
		state = s;
	}
}