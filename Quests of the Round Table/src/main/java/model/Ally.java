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
	
	public void playAlly() {
		switch(name) {
			case "king_pellinore":
				break;
			case "merlin":
				break;
			case "queen_iseult":
				break;
			case "sir_gawain":
				break;
			case "sir_lancelot":
				break;
			case "sir_percival":
				break;
			case "sir_tristan":
				break;
		}
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