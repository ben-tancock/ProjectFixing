package model;

public class Quest extends Story {
	private String name;
	private int stages;
	
	public Quest(String n, int s) {
		name = n;
		stages = s;
	}
}
