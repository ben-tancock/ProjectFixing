package control;

import model.Event;
import model.Foe;
import model.Player;
import model.Players;
import model.Weapon;
import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Ally;
import model.CardStates;
import control.PlayGame.PlayGameControlHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventHandler {
	private static final Logger logger = LogManager.getLogger(EventHandler.class);
	//Note that the Player in this case is the one that drew the card.
	private static List<ControlHandler> listeners = new ArrayList<ControlHandler>();
	public EventHandler(Event e, Player p, Players pr, AdventureDeck aDeck, AdventureDiscard aDiscard) {
		//Make a case for each possible event and call the necessary method. 
		switch(e.getName()) {
			case "king's_recognition":
				kingsRecognition();
				break;
			case "plague":
				plague(p);
				break;
			case "chivalrous_deed":
				chivalrousDeed(pr);
				break;
			case "pox":
				pox(pr, p);
				break;
			case "prosperity_throughout_the_realm":
				prosperity(pr, aDeck);
				break;
			case "queen's_favor":
				queensFavor(pr, aDeck);
				break;
			case "court_called_to_camelot":
				courtCalled(pr, aDiscard);
				break;
			case "king's_call_to_arms":
				kingsCallToArms(pr, aDiscard);
				break;
		}
		listeners.add(new PlayGameControlHandler());
	}
	
	public void kingsRecognition() {// notify that the next player to finish a quest gets 2 bonus shields
		//listeners.get(0).onKingsRecognition();
	}
	
	public void plague(Player p) { // drawer loses 2 shields if possible
		if(p.getShields() > 2) {
			p.setShields(p.getShields() -2);
		}
		else {
			p.setShields(0);
		}
	}
	
	public void chivalrousDeed(Players p) { // player(s) with BOTH lowest rank and least amount of shields receives 3 shields 
		logger.info("Chivalrous Deed triggered, player(s) with BOTH lowest rank and least amount of shields receive 3 shields.");
		Integer pShields[] =  p.getPlayers().stream().map(Player::getShields).toArray(Integer[]::new);
		int minS = Collections.min(Arrays.asList(pShields));
			
		Integer pRanks[] =  p.getPlayers().stream().map(Player::getRank).toArray(Integer[]::new);
		int minR = Collections.min(Arrays.asList(pRanks));
			
		int min = minR + minS;
			
		for(Player pr : p.getPlayers()) { // scenario: squire w/ 4 shields and knight w/ 3 shields --> squire, not knight
			if(pr.getRank() + pr.getShields() <= min) {
				logger.info(pr.getName() + " receives 3 shields.");
				pr.setShields(pr.getShields() + 3);
			}
		}
	}
	
	public void pox(Players p, Player pr) { // all players except drawer lose a shield
		for (Player ele : p.getPlayers()) {
			if(!ele.equals(pr)) {
				if(ele.getShields() > 0) {
					ele.setShields(ele.getShields()-1);
				}
			}
		}
	}
	
	public void prosperity(Players p, AdventureDeck d) { // all players must draw two cards
		logger.info("Prosperity throughout the realm triggered, all players must draw 2 cards.");
		PlayGame pg = PlayGame.getInstance();
		ArrayList<Player> prClone = new ArrayList<>(); // had to do this to avoid concurrent modification exception
		prClone.addAll(pg.getPlayers().getPlayers());
		for(int i = 0; i < prClone.size(); i++) {
			prClone.get(i).drawCard(2, d);
			prClone.get(i).setHandState(CardStates.FACE_DOWN);
			pg.getView().update(null, pg.getPlayers(), pg.getSDeck(), pg.getSDiscard(), null);
		}
		pg.getPlayers().setPlayers(prClone);
	}
	
	public void queensFavor(Players p, AdventureDeck d) { // player(s) with lowest rank receive 2 cards
		//boolean lower = false; 
		
		List<Player> per = new ArrayList<Player>();
		Integer pranks[] =  p.getPlayers().stream().map(Player::getRank).toArray(Integer[]::new);
		int minR = Collections.min(Arrays.asList(pranks));
				
		logger.info("Queen's Favor drawn, all lowest ranked players receive 2 cards.");
		PlayGame pg = PlayGame.getInstance();
		ArrayList<Player> prClone = new ArrayList<>(); // had to do this to avoid concurrent modification exception
		prClone.addAll(pg.getPlayers().getPlayers());
		for(int i = 0; i < prClone.size(); i++) {
			if(prClone.get(i).getRank() == minR) {
				prClone.get(i).drawCard(2, d);
			}
		}
		pg.getPlayers().setPlayers(prClone);
	}
	
	public void courtCalled(Players p, AdventureDiscard d) { // all allies in play must be discarded
		logger.info("Court Called to Camelot drawn, all allies in play must be discarded.");
		PlayGame pg = PlayGame.getInstance();
		ArrayList<Player> prClone = new ArrayList<>(); // had to do this to avoid concurrent modification exception
		prClone.addAll(pg.getPlayers().getPlayers());
		for(int i = 0; i < prClone.size(); i++) {
			prClone.get(i).getAllies().removeAll(prClone.get(i).getAllies());
		}
		pg.getPlayers().setPlayers(prClone);
	}
	
	public void kingsCallToArms(Players p, AdventureDiscard d) {
		Integer pranks[] =  p.getPlayers().stream().map(Player::getRank).toArray(Integer[]::new);//map ranks of players to integer array
		int maxR = Collections.max(Arrays.asList(pranks));
				
		for(Player pr : p.getPlayers()) { // scenario: squire w/ 4 shields and knight w/ 3 shields --> squire, not knight
			if(pr.getRank() == maxR) {
				//this is just a workaround to force cards out, but in reality we would notify the view.
				boolean weaponFound = false;
				for(Adventure a : pr.getHand()) {
					if(a instanceof Weapon) {
						weaponFound = true;
						pr.remove(pr.getHand(), d, a);
						break;
					}
				}
				
				
				if(!weaponFound) {
					int count = 0;
					for(Adventure a : pr.getHand()) {
						if(count < 2 && a instanceof Foe) {
							pr.remove(pr.getHand(), d, a);
							count++;
						}
					}
					//notify that player must discard 2 foes
				} else {
					//notify that player must discard 1 weapon
				}
				
			}
		}	
	}
	
	
}
