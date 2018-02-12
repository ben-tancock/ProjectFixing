package model;

import java.util.ArrayList;

public class Stage extends ArrayList<Adventure> {

	/**
	 * added this to scare off warnings and to make it serial as well
	 */
	private static final long serialVersionUID = 1L;
	
	public Stage(Test t) {
		add(t);
	}
	
	public Stage(Foe f, ArrayList<Weapon> weapons) {
		f.getWeapons().addAll(weapons);
		add(f);
	}
	
	public void displayStage() {
		if(get(0) instanceof Foe) {
			System.out.println(get(0).getName() + ": " + ((Foe)get(0)).getWeapons());
		} else {
			System.out.println(toString());
		}
	}
	
	public String toString() {
		if(get(0) instanceof Foe) {
			return get(0).getName() + ": " + ((Foe)get(0)).getWeapons();
		} else {
			return get(0).getName();
		}
	}

}
