package control;

import model.Event;
import model.Foe;
import model.Player;
import model.Players;
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
	
	public void prosperity(Players p, AdventureDeck d) { // all players must draw two cards
		for(Player pr : p.getPlayers()) {
			pr.drawCard(2, d);
		}
	}
	
	public void chivalrousDeed(Players p) { // player(s) with BOTH lowest rank and least amount of shields receives 3 shields
		boolean lower = false; 
		
		List<Player> per = new ArrayList<Player>();
		Integer pShields[] =  p.getPlayers().stream().map(Player::getShields).toArray(Integer[]::new);
		int minS = Collections.min(Arrays.asList(pShields));
		
		Integer pRanks[] =  p.getPlayers().stream().map(Player::getRank).toArray(Integer[]::new);
		int minR = Collections.min(Arrays.asList(pRanks));
		
		int min = minR + minS;
		
		for(Player pr : p.getPlayers()) { // scenario: squire w/ 4 shields and knight w/ 3 shields --> squire, not knight
			if(pr.getRank() + pr.getShields() <= min) {
				pr.setShields(pr.getShields() + 3);
			}
		}
	}
	
	public void queensFavor(Players p, AdventureDeck d) { // player(s) with BOTH lowest rank and least amount of shields receives 3 shields
		boolean lower = false; 
		
		List<Player> per = new ArrayList<Player>();
		Integer pranks[] =  p.getPlayers().stream().map(Player::getRank).toArray(Integer[]::new);
		int minR = Collections.min(Arrays.asList(pranks));
				
		for(Player pr : p.getPlayers()) { // scenario: squire w/ 4 shields and knight w/ 3 shields --> squire, not knight
			if(pr.getRank() == minR) {
				pr.drawCard(2, d);
			}
		}
	}
	
	public void plague(Player p) { // drawer loses 2 shields if possible
		if(p.getShields() > 2) {
			p.setShields(p.getShields() -2);
		}
		else {
			p.setShields(0);
		}
	}
	
	public void pox(Players p, Player pr) { // all players except drawer lose a shield
		for (Player ele : p.getPlayers()) {
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
	
	public void courtCalled(Players p, AdventureDiscard d) { // all allies in play must be discarded
		for (Player pr : p.getPlayers()) {
			for(Ally a : pr.getAllies()) {
				//new ArrayList<Card>()
				new ArrayList<Foe>();
				pr.remove(pr.getAllies(), d, a );
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
