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

}
