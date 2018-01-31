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
		addCard(11, (new Weapon("horse", 10)));
		addCard(16, (new Weapon("sword", 10)));
		addCard(6, (new Weapon("dagger", 5)));
		addCard(2, (new Weapon("excalibur", 30)));
		addCard(6, (new Weapon("lance", 20)));
		addCard(8, (new Weapon("battle-ax", 15)));
		
		
		addCard(7, (new Foe("robber_Knight", 15, 15)));
		addCard(5, (new Foe("saxons", 10, 20)));
		addCard(4, (new Foe("boar", 5, 15)));
		addCard(8, (new Foe("thieves", 5, 5)));
		addCard(2, (new Foe("green_knight", 25, 40)));
		addCard(3, (new Foe("black_knight", 25, 35)));
		addCard(6, (new Foe("evil_knight", 20, 30)));
		addCard(8, (new Foe("saxon_knight", 15, 15)));
		addCard(1, (new Foe("dragon", 50, 70)));
		addCard(2, (new Foe("giant", 40, 40)));
		addCard(4, (new Foe("mordred", 30, 30)));
		
		addCard(1, (new Ally("sir_gawain", 10)));
		addCard(1, (new Ally("king_pellinore", 10)));
		addCard(1, (new Ally("sir_percival", 5)));
		addCard(1, (new Ally("sir_tristan", 10)));
		addCard(1, (new Ally("king_arthur", 10)));
		addCard(1, (new Ally("queen_guinevere", 0)));
		addCard(1, (new Ally("merlin", 0)));
		addCard(1, (new Ally("sir_galahad", 15)));
		addCard(1, (new Ally("sir_lancelot", 15)));
		addCard(1, (new Ally("queen_iseult", 0)));
		
		addCard(2, (new Test("test_of_the_questing_beast", 4)));
		addCard(2, (new Test("test_of_temptation", 0)));
		addCard(2, (new Test("test_of_valor", 0)));
		addCard(2, (new Test("test_of_morgan_le_fey", 3)));

		addCard(8, (new Amour("amour", 10)));
	}
	
	public void shuffle() {
		Collections.shuffle(this);
	}
	
}
