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
	public void addCard(int j, Adventure c) {
		for (int i = 0; i < j; i++) {
			add(c);
		}
	}
	
	public AdventureDeck() {
		//setup the adventure deck with Weapons, Allies, Foes, Tests and Amour
		addCard(11, (new Weapon("Horse", 10)));
		addCard(16, (new Weapon("Sword", 10)));
		addCard(6, (new Weapon("Dagger", 5)));
		addCard(2, (new Weapon("Excalibur", 30)));
		addCard(6, (new Weapon("Lance", 20)));
		addCard(8, (new Weapon("Battle-ax", 15)));
		
		
		addCard(7, (new Foe("Robber_Knight", 15, 15)));
		addCard(5, (new Foe("Saxons", 10, 20)));
		addCard(4, (new Foe("Boar", 5, 15)));
		addCard(8, (new Foe("Thieves", 5, 5)));
		addCard(2, (new Foe("Green_Knight", 25, 40)));
		addCard(3, (new Foe("Black_Knight", 25, 35)));
		addCard(6, (new Foe("Evil_Knight", 20, 30)));
		addCard(8, (new Foe("Saxon_Knight", 15, 15)));
		addCard(1, (new Foe("Dragon", 50, 70)));
		addCard(2, (new Foe("Giant", 40, 40)));
		addCard(4, (new Foe("Mordred", 30, 30)));
		
		addCard(1, (new Ally("Sir_Gawain", 10)));
		addCard(1, (new Ally("Sir_Pellinore", 10)));
		addCard(1, (new Ally("Sir_Percival", 5)));
		addCard(1, (new Ally("Sir_Tristan", 10)));
		addCard(1, (new Ally("King_Arthur", 10)));
		addCard(1, (new Ally("Queen_Guinevere", 0)));
		addCard(1, (new Ally("Merlin", 0)));
		addCard(1, (new Ally("Sir_Galahad", 15)));
		addCard(1, (new Ally("Sir_Lancelot", 15)));
		addCard(1, (new Ally("Queen_Iseult", 0)));
		
		addCard(2, (new Test("Questing_Beast", 4)));
		addCard(2, (new Test("Temptation", 0)));
		addCard(2, (new Test("Valor", 0)));
		addCard(2, (new Test("Morgan", 3)));

		addCard(8, (new Amour("Amour", 10)));
	}
	
	public void shuffle() {
		Collections.shuffle(this);
	}
	
}
