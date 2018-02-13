package model;

import java.util.ArrayList;

public class Stage {
	
	private Foe foe;
	private Test test;
	private int battlePoints;
	private int bids;
	
	public Stage(Test t) {
		test = t;
		bids = t.getMinbid();
	}
	
	public Stage(Foe f, ArrayList<Weapon> weapons) throws Exception {
		for (Weapon w : weapons) {
			f.addWeapon(w);
		}
		foe = f;
	}
	
	public Test getTest() {
		return test;
	}
	
	public Foe getFoe() {
		return foe;
	}
	
	public int getBattlePoints() {
		return battlePoints;
	}
	
	public int getBids() {
		return bids;
	}
	
	public void setBattlePoints(int b) {
		battlePoints = b;
	}
	
	public void displayStage() {
		if(foe != null) {
			System.out.println(foe.getName() + ": " + foe.getWeapons());
		} else if (test != null) {
			System.out.println(test.getName());
		} else {
			System.out.println("blank");
		}
	}
	
	public String toString() {
		if(foe != null) {
			return foe.getName() + ": " + foe.getWeapons();
		} else if (test != null){
			return test.getName();
		}
		else return "";
	}

}
