package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weapon extends Adventure{
	/**
	 * serialUID
	 */
	private static final long serialVersionUID = -2865787459237712718L;
	private String name;
	private int battlepoints;
	private int state;
	boolean weapon;
	
	public Weapon() {
		name = "";
		battlepoints = 0;
		state = 0;
	}
	
	public Weapon(String n, int bp, int state) {
		name = n;
		battlepoints = bp;
		state = CardStates.FACE_DOWN;
	}
		
	public String getName() {
		return name;
	}
	
	@Override
	public int getBattlePoints() {
		return battlepoints;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int s) {
		state = s;
	}
}