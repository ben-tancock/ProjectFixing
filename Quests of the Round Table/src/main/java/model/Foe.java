package model;

import java.util.ArrayList;

public class Foe extends Adventure{
	private String name;
	private int bp1;
	private int bp2;
	private int state;
	private ArrayList<Weapon> weapons;
	
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
	
	public void addWeapon(Weapon w) throws Exception {
		boolean distinct = true;
		
		for(Weapon weapon : weapons) {
			if(w.getName().equals(weapon.getName())) {
				distinct = false;
			}
		}
		
		if(!distinct) {
			throw new Exception("Foe can only have distinct weapons.");
		} else {
			weapons.add(w);
		}
	}

	public void setState(int s) throws Exception {
		if(s != CardStates.FACE_DOWN || s!= CardStates.FACE_UP) {
			throw new Exception("Card State is invalid.");
		} else {
			state = s;
		}
	}
}
