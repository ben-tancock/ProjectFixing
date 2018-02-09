package model;

import java.util.ArrayList;
import java.util.Collections;

public class StoryDeck extends ArrayList<Story>{

	/**
	 * added this to scare off warnings and to make it serial as well
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * During initialization the deck is setup
	 */
	public void addCard(int j, Story c) {
		for (int i = 0; i < j; i++) {
			add(c);
		}
	}
	
	public StoryDeck() {
		//add quests
		addCard(1, (new Quest("search_for_the_holy_grail", 5)));
		addCard(1, (new Quest("test_of_the_green_knight", 4)));
		addCard(1, (new Quest("search_for_the_questing_beast", 4)));
		addCard(1, (new Quest("defend_the_queen's_honor", 4)));
		addCard(1, (new Quest("rescue_the_fair_maiden", 3)));
		addCard(1, (new Quest("journey_through_the_enchanted_forest", 3)));
		addCard(2, (new Quest("vanquish_king_arthur's_enemies", 3)));
		addCard(1, (new Quest("slay_the_dragon", 3)));
		addCard(2, (new Quest("boar_hunt", 2)));
		addCard(2, (new Quest("repel_the_saxon_raiders", 2)));
		
		//add tournaments
		addCard(1, (new Tournament("camelot", 3)));
		addCard(1, (new Tournament("orkney", 2)));
		addCard(1, (new Tournament("tintagel", 1)));
		addCard(1, (new Tournament("york", 0)));
		
		//add events
		addCard(2, (new Event("king's_recognition")));
		addCard(2, (new Event("queen's_favor")));
		addCard(2, (new Event("court_called_to_camelot")));
		addCard(1, (new Event("pox")));
		addCard(1, (new Event("plague")));
		addCard(1, (new Event("chivalrous_deed")));
		addCard(1, (new Event("prosperity_throughout_the_realm")));
		addCard(1, (new Event("king's_call_to_arms")));
	}
	
	public Story top() {
		Story t = this.get(0);
		this.remove(0);
		//System.out.println(t.getName());
		return t;
	}
	
	public Story findAndDraw(String name) {
		for(Story card : this) {
			if(card.getName().equals(name)) {
				this.remove(card);
				return card;
			}
		}
		return null;
	}
	
	public void shuffle() {
		Collections.shuffle(this);
	}

}
