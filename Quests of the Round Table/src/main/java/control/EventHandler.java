package control;

import model.Event;
import model.Foe;
import model.Player;
import model.Player.Person;
import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Ally;
import model.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class EventHandler {

	private Event event;
	
	public EventHandler(Event e) {
		event = e;
	}
	
	public void Prosperity(Player p, AdventureDeck d) { // all players must draw two cards
		for(Person pr : p.getPlayers()) {
			pr.drawCard(2, d);
		}
	}
	
	public void ChivalrousDeed(Player p) { // player(s) with BOTH lowest rank and least amount of shields receives 3 shields
		boolean lower = false; 
		
		List<Person> per = new ArrayList<Person>();
		Integer pshields[] =  p.getPlayers().stream().map(Person::getShields).toArray(Integer[]::new);
		int minS = Collections.min(Arrays.asList(pshields));
		
		Integer pranks[] =  p.getPlayers().stream().map(Person::getRank).toArray(Integer[]::new);
		int minR = Collections.min(Arrays.asList(pranks));
		
		int min = minR + minS;
		
		for(Person pr : p.getPlayers()) { // scenario: squire w/ 4 shields and knight w/ 3 shields --> squire, not knight
			if(pr.getRank() + pr.getShields() <= min) {
				pr.setShields(pr.getShields() + 3);
			}
		}
	}
	
	public void QueensFavor(Player p, AdventureDeck d) { // player(s) with BOTH lowest rank and least amount of shields receives 3 shields
		boolean lower = false; 
		
		List<Person> per = new ArrayList<Person>();
		Integer pranks[] =  p.getPlayers().stream().map(Person::getRank).toArray(Integer[]::new);
		int minR = Collections.min(Arrays.asList(pranks));
				
		for(Person pr : p.getPlayers()) { // scenario: squire w/ 4 shields and knight w/ 3 shields --> squire, not knight
			if(pr.getRank() == minR) {
				pr.drawCard(2, d);
			}
		}
	}
	
	public void Plague(Person p) { // drawer loses 2 shields if possible
		if(p.getShields() > 2) {
			p.setShields(p.getShields() -2);
		}
		else {
			p.setShields(0);
		}
	}
	
	public void Pox(Player p, Person pr) { // all players except drawer lose a shield
		for (Person ele : p.getPlayers()) {
			if(!ele.equals(pr)) {
				if(ele.getShields() > 0) {
					ele.setShields(ele.getShields()-1);
				}
				else {
					ele.setShields(0);
				}
			}
		}
	}
	
	public void CourtCalled(Player p, AdventureDiscard d) { // all allies in play must be discarded
		for (Person pr : p.getPlayers()) {
			for(Ally a : pr.getAllies()) {
				//new ArrayList<Card>()
				new ArrayList<Foe>();
				pr.Remove(pr.getAllies(), d, a );
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
	// TO DO: INSTEAD OF DISCARD, PLAY, ETC, 
	// MAKE A METHOD FOR CARD REMOVAL, 
	// WHICH TAKES AS INPUT:
	//1: THE CARD BEING REMOVED
	//2: WHERE THE CARD IS BEING REMOVED FROM
	//3: WHERE THE CARD IS BEING SENT TO
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
