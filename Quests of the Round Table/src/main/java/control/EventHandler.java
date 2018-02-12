package control;

import model.Event;
import model.Player;
import model.Player.Person;
import model.AdventureDeck;
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
		
		//List<Person> per = new ArrayList<Person>();
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
	
	public void Plague(Person p) {
		//p.discard(i);
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
