package model;

import java.util.ArrayList;
import java.util.Collections;

public class AdventureDeck extends ArrayList<Adventure>{

	/**
	 * added this to scare off warnings and to make it serial as well
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * During initialization the deck is setup
	 */
	public AdventureDeck() {
		//setup the adventure deck with Weapons, Allies, Foes, Tests and Amour
		add(new Weapon());
		add(new Foe());
		add(new Ally());
		add(new Test());
		add(new Amour());
	}
	
	public void shuffle() {
		Collections.shuffle(this);
	}
	
}
