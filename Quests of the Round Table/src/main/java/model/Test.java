package model;

public class Test extends Adventure{
	private int minbid;
	private String name;
	private int state;
	
	public Test(String n, int mb) {
		name = n;
		minbid = mb;
		state = CardStates.FACE_DOWN;
	}
	
	public String getName() {
		return name;
	}
	
	public int getMinbid() {
		return minbid;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int s) {
		state = s;
	}
}