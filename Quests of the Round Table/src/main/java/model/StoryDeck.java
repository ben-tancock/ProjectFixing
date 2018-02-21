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
	public void addQuest(int j, String name, int ns, String spFs) {
		for (int i = 0; i < j; i++) {
			add(new Quest(name, ns, spFs));
		}
	}
	
	public void addTournament(int j, String name, int b) {
		for (int i = 0; i < j; i++) {
			add(new Tournament(name, b));
		}
	}
	
	public void addEvent(int j, String name) {
		for (int i = 0; i < j; i++) {
			add(new Event(name));
		}
	}
	
	public StoryDeck() {
		//add quests
		addQuest(1, "search_for_the_holy_grail", 5, "all");
		addQuest(1, "test_of_the_green_knight", 4, "green_knight");
		addQuest(1, "search_for_the_questing_beast", 4, "");
		addQuest(1, "defend_the_queen's_honor", 4, "all");
		addQuest(1, "rescue_the_fair_maiden", 3, "black_knight");
		addQuest(1, "journey_through_the_enchanted_forest", 3, "evil_knight");
		addQuest(2, "vanquish_king_arthur's_enemies", 3, "");
		addQuest(1, "slay_the_dragon", 3, "dragon");
		addQuest(2, "boar_hunt", 2, "boar");
		addQuest(2, "repel_the_saxon_raiders", 2, "all_saxons");
		
		//add tournaments
		addTournament(1, "camelot", 3);
		addTournament(1, "orkney", 2);
		addTournament(1, "tintagel", 1);
		addTournament(1, "york", 0);
		
		//add events
		addEvent(2, "king's_recognition");
		addEvent(2, "queen's_favor");
		addEvent(2, "court_called_to_camelot");
		addEvent(1, "pox");
		addEvent(1, "plague");
		addEvent(1, "chivalrous_deed");
		addEvent(1, "prosperity_throughout_the_realm");
		addEvent(1, "king's_call_to_arms");
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
	
	public String toString() {
		String s = "";
		for(Story st : this) {
			s += st.getName() + ",";
		}
		return s;
	}

}
