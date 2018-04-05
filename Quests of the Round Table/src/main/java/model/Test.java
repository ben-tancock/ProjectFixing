package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Test extends Adventure{
	/**
	 * serialUID
	 */
	private static final long serialVersionUID = -4862374553321058631L;
	private int minBid;
	private String name;
	private int state;
	boolean test;
	
	public Test() {
		name = "";
		minBid = 0;
		state = 0;
	}
	
	public Test(String n, int mb, int state) {
		name = n;
		minBid = mb;
		state = CardStates.FACE_DOWN;
	}
	
	public String getName() {
		return name;
	}
	
	public int getMinbid() {
		return minBid;
	}
	
	public int getState() {
		return state;
	}
	
	public void setMinBid(int b) {
		if(name.equals("test_of_the_questing_beast")) {
			minBid = b;
		}
	}
	
	public void setState(int s) {
		state = s;
	}
}