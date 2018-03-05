package model;

public class Ally extends Adventure{
	private String name;
	private int bp;
	private int bids;
	private int state;
	
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
	
	public void setBattlePoints(int b) {
		bp = b;
	}
	
	public void setBids(int b) {
		bids = b;
	}
}