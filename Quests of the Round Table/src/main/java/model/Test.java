package model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Test.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Test extends Adventure implements Serializable{
	/**
	 * serialUID
	 */
	private static final long serialVersionUID = -4862374553321058631L;
	private int minBid;
	private String name;
	private int state;
	public boolean test;
	
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
	
	public int getMinBid() {
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