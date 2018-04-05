package model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = AdventureDeserializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class Adventure implements Card, Serializable{
	
	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 7893632617414867861L;
	private String name;
	private int state;
	private int battlePoints;
	
	public String getName() {
		return name;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int s){
		state = s;
	}
	
	public void displayAttributes() {
		
	}
	
	public void displayName() {
		System.out.println("Name:" + name + "\n");
	}
	
	public int getBattlePoints() {
		return battlePoints;
	}
	
	public String toString() {
		return this.getName();
	}
	
}
