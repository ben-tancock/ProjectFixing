package model;

public class Event extends Story{
	String name;
	
	public Event(String n) {
		name = n;
	}
	
	public String getName() {
		return name;
	}
}
