package model;

import java.util.ArrayList;
import java.util.List;

public class Ally extends Adventure{
	String name;
	int bp;
	int bids;
	int state;
	
	public Ally(String n, int b, int bs) {
		name = n;
		bp = b;
		bids = bs;
		state = CardStates.FACE_DOWN;
	}
	/*
	public void playAlly() {
		switch(name) {
			case "king_pellinore":
				break;
			case "merlin":
				break;
			case "queen_iseult":
				break;
			case "sir_gawain":
				break;
			case "sir_lancelot":
				break;
			case "sir_percival":
				break;
			case "sir_tristan":
				break;
		}
	}*/
	
	public String getName() {
		return name;
	}
	
	public int getBattlePoints() {
		return bp;
	}
	
	public int getBids() {
		return bids;
	}
	
	public int getState() {
		return state;
	}
	
	public void setState(int s) {
		state = s;
	}
	
	public void setBattlePoints(int b) {
		bp = b;
	}
	
	public void setBids(int b) {
		bids = b;
	}
	
	public void ability(String Qname, List<Ally> a) {
		
		if(this.getName() == "sir_gawain") {
			if(Qname == "test_of_the_green_knight") {
				System.out.println("test gawain");
			}
		}
		else if(this.getName() == "sir_percival") {
			if(Qname == "search_for_the_holy_grail") {
				System.out.println("test percival");
			}
		}
			
	}
}