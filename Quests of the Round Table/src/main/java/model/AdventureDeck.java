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
	public void addWeapon(int j, String name, int bp) {
		for (int i = 0; i < j; i++) {
			add(new Weapon(name, bp));
		}
	}
	
	public void addFoe(int j, String name, int lbp, int hbp) {
		for (int i = 0; i < j; i++) {
			add(new Foe(name, lbp, hbp));
		}
	}
	
	public void addAlly(int j, String name, int lbp, int hbp) {
		for (int i = 0; i < j; i++) {
			add(new Ally(name, lbp, hbp));
		}
	}
	
	public void addTest(int j, String name, int b) {
		for (int i = 0; i < j; i++) {
			add(new Test(name, b));
		}
	}
	
	public void addAmour(int j, String name, int bp) {
		for (int i = 0; i < j; i++) {
			add(new Amour(name, bp));
		}
	}
	
	public AdventureDeck() {
		//setup the adventure deck with Weapons, Allies, Foes, Tests and Amour
		addWeapon(11, "horse", 10);
		addWeapon(16, "sword", 10);
		addWeapon(6, "dagger", 5);
		addWeapon(2, "excalibur", 30);
		addWeapon(6, "lance", 20);
		addWeapon(8, "battle-ax", 15);
		
		
		addFoe(7, "robber_knight", 15, 15);
		addFoe(5, "saxons", 10, 20);
		addFoe(4, "boar", 5, 15);
		addFoe(8, "thieves", 5, 5);
		addFoe(2, "green_knight", 25, 40);
		addFoe(3, "black_knight", 25, 35);
		addFoe(6, "evil_knight", 20, 30);
		addFoe(8, "saxon_knight", 15, 15);
		addFoe(1, "dragon", 50, 70);
		addFoe(2, "giant", 40, 40);
		addFoe(4, "mordred", 30, 30);
		
		addAlly(1, "sir_gawain", 10, 0);
		addAlly(1, "king_pellinore", 10, 0);
		addAlly(1, "sir_percival", 5, 0);
		addAlly(1, "sir_tristan", 10, 0);
		addAlly(1, "king_arthur", 10, 2);
		addAlly(1, "queen_guinevere", 0, 3);
		addAlly(1, "merlin", 0, 0);
		addAlly(1, "sir_galahad", 15, 0);
		addAlly(1, "sir_lancelot", 15, 0);
		addAlly(1, "queen_iseult", 0, 2);
		
		addTest(2, "test_of_the_questing_beast", 0);
		addTest(2, "test_of_temptation", 0);
		addTest(2, "test_of_valor", 0);
		addTest(2, "test_of_morgan_le_fey", 3);

		addAmour(8, "amour", 10);
	}
	
	public Adventure top() {
		Adventure t = this.get(0);
		this.remove(0);
		//System.out.println(t.getName());
		return t;
	}
	
	// for testing purposes of retrieving certain cards
	public Adventure findAndDraw(String name) throws Exception {
		for(Adventure card : this) {
			if(card.getName().equals(name)) {
				this.remove(card);
				return card;
			}
		}
		throw new Exception("Could not find card.");
	}
	
	public void shuffle() {
		Collections.shuffle(this);
	}
	
}
