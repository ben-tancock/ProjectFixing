package model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Amour.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Amour extends Adventure implements Serializable{
	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 877658934314322713L;
	private String name;
	private int battlePoints;
	private int state;
	public boolean amour;
	
	public Amour() {
		name = "";
		battlePoints = 0;
		state = 0;
	}
	
	public Amour(String n, int b, int state) {
		name = n;
		battlePoints = b;
		state = CardStates.FACE_DOWN;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int s) {
		state = s;
	}
	
	public void setBattlePoints(int battlePoints) {
		this.battlePoints = battlePoints;
	}
	
	public int getBattlePoints() {
		return battlePoints;
	}
}