package model;

public class Foe extends Adventure{
	private String name;
	private int bp1;
	private int bp2;
	private int state;
	
	public Foe(String n, int b1, int b2) {
		name = n;
		bp1 = b1;
		bp2 = b2;
		state = CardStates.FACE_DOWN;
	}
	
	public void mordred() {
		// do mordred ability if name = "mordred"?
	}
	
	public String getName() {
		return name;
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
