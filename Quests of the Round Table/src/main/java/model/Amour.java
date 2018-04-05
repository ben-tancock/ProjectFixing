package model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Amour extends Adventure{
	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 877658934314322713L;
	private String name;
	private int bp;
	private int state;
	boolean amour;
	
	public Amour() {
		name = "";
		bp = 0;
		state = 0;
	}
	
	public Amour(String n, int b, int state) {
		name = n;
		bp = b;
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
	
	@Override
	public int getBattlePoints() {
		return bp;
	}
}