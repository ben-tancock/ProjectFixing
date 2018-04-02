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
	
	@Override
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
	
	public void ability(List<Ally> pAllies) {
		System.out.println("test ability");
		if (this.getName() == "sir_tristan") {
			setBattlePoints(10);
			for(Ally a : pAllies) {
				if (a.getName() == "queen_iseult") {
					System.out.println("Tristan ability execute");
					setBattlePoints(20);
				}
			}
		}
		else if (this.getName() == "queen_iseult") {
			setBids(2);
			for(Ally a : pAllies) {
				if (a.getName() == "sir_tristan") {
					System.out.println("Iseult ability execute");
					setBids(4);
				}
			}
		}
		else{
			// do nothing
		}
	}
	
	public void ability(String Qname, List<Ally> pAllies) {
		System.out.println("test ability");
		if(this.getName() == "sir_gawain") {
			setBattlePoints(10);
			if(Qname == "test_of_the_green_knight") {
				System.out.println("test gawain");
				setBattlePoints(20);
			}
		}
		else if (this.getName() == "king_pellinore") {
			setBids(0);
			if(Qname == "search_for_the_questing_beast") {
				System.out.println("test pellinore");
				setBids(4);
			}
		}
		else if(this.getName() == "sir_percival") {
			setBattlePoints(5);
			if(Qname == "search_for_the_holy_grail") {
				System.out.println("test percival");
				setBattlePoints(20);
			}
		}
		else if (this.getName() == "sir_lancelot") {
			setBattlePoints(15);
			if(Qname == "defend_the_queen's_honor") {
				setBattlePoints(25);
			}
		}	
		else if (this.getName() == "sir_tristan") {
			setBattlePoints(10);
			for(Ally a : pAllies) {
				if (a.getName() == "queen_iseult") {
					setBattlePoints(20);
				}
			}	
		}
		else if (this.getName() == "queen_iseult") {
			setBids(2);
			for(Ally a : pAllies) {
				if (a.getName() == "sir_tristan") {
					setBids(4);
				}
			}
		}
		else{
			// do nothing
		}
			
	}
}