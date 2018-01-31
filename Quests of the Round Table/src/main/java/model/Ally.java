package model;

public class Ally extends Adventure{
	String name;
	int bp;
	
	public Ally(String n, int b) {
		name = n;
		bp = b;
	}
	
	public String getName() {
		return name;
	}
}
