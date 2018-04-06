package model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Weapon.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weapon extends Adventure implements Serializable{
	/**
	 * serialUID
	 */
	private static final long serialVersionUID = -2865787459237712718L;
	private String name;
	private int battlePoints;
	private int state;
	boolean weapon;
	
	public Weapon() {
		name = "";
		battlePoints = 0;
		state = 0;
	}
	
	public Weapon(String n, int bp, int state) {
		name = n;
		battlePoints = bp;
		state = CardStates.FACE_DOWN;
	}
		
	public String getName() {
		return name;
	}
	
	@Override
	public int getBattlePoints() {
		return battlePoints;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int s) {
		state = s;
	}
}