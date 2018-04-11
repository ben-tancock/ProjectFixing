package model;

import java.io.Serializable;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = Foe.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Foe extends Adventure implements Serializable{
	/**
	 * serialUID
	 */
	private static final long serialVersionUID = 8675627747171217655L;
	private String name;
	private int bp1;
	private int bp2;
	private int state;
	private ArrayList<Weapon> weapons;
	public boolean foe;
	
	public Foe() {
		name = "";
		bp1 = 0;
		bp2 = 0;
		state = 0;
		weapons = new ArrayList<Weapon>();
	}
	
	public Foe(String n, int b1, int b2, int state) {
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
