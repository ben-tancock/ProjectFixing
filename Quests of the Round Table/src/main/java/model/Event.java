package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event extends Story{
	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 3242305892572996470L;
	String name;
	int state;
	public boolean event;
	
	public Event(String n) {
		name = n;
		state = CardStates.FACE_DOWN;
	}
	
	public String getName() {
		return name;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int s) {
		state = s;
	}
}