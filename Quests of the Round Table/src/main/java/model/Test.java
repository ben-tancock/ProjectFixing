package model;

public class Test extends Adventure{
	private int minbid;
	private String name;
	public Test(String n, int mb) {
		name = n;
		minbid = mb;
	}
	
	public String getName() {
		return name;
	}
}
