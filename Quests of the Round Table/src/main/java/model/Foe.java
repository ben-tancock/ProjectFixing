package model;

import java.util.ArrayList;

public class Foe extends Adventure{
	private String name;
	private int bp1;
	private int bp2;
	private int state;
	private ArrayList<Weapon> weapons;
	boolean foe;
	
	public Foe(String n, int b1, int b2) {
		name = n;
		bp1 = b1;
		bp2 = b2;
		state = CardStates.FACE_DOWN;
		weapons = new ArrayList<Weapon>();
	}
	
	public void mordred() {
		// do mordred ability if name = "mordred"?
	}
	
	public String getName() {
		return name;
	}
	
	public int getState() {
		return state;
	}
	
	public ArrayList<Weapon> getWeapons() {
		return weapons;
	}
	
	public int getLowerBattlePoints() {
		int weaponBp = 0;
		for(Weapon weapon : weapons) {
			weaponBp += weapon.getBattlePoints();
		}
		return bp1 + weaponBp;
	}
	
	public int getHigherBattlePoints() {
		int weaponBp = 0;
		for(Weapon weapon : weapons) {
			weaponBp += weapon.getBattlePoints();
		}
		return bp2 + weaponBp;
	}
	
	public int getFoeBP(String spfs) {
		if(spfs.equals("all")) {
			return getHigherBattlePoints();
		} else if(spfs.equals("all_saxons")) {
			if(getName().contains("saxon")) {
				return getHigherBattlePoints();
			} else {
				return getLowerBattlePoints();
			}
		} else if(spfs.equals(getName())) {
			return getHigherBattlePoints();
		} else {
			return getLowerBattlePoints();
		}
	}
	
	public void addWeapon(Weapon w){
		weapons.add(w);
	}

	public void setState(int s) {
		state = s;
	}
}
