package model;

public class Weapon extends Adventure{
	private String name;
	private int battlepoints;
	
		public Weapon(String n, int bp) {
			name = n;
			battlepoints = bp;
			
		}
		
		public String getName() {
			return name;
		}
}
