package control;

import java.util.ArrayList;

import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Amour;
import model.Player;
import model.Player.Person;
import model.Quest;
import model.Stage;
import model.Weapon;

public class QuestHandler {
	
	private Quest card;
	private AdventureDeck deck;
	private AdventureDiscard discard;
	private Player players;
	
	public QuestHandler(Quest c, Player p, AdventureDeck d, AdventureDiscard di) {
		card = c;
		deck = d;
		discard = di;
		players = p;
	}

	public boolean playQuest() throws Exception {
		//Ask for the sponsor and move focus to them.
		Person sponsor = askForSponsor();
		if(sponsor == null) {
			return true; //nobody sponsored so go back
		}
		card.setSponsor(sponsor);
		int sponsorIndex = players.getPlayers().indexOf(sponsor);
		
		for(int i = 0; i < card.getNumStages(); i++) {
			try {
				card.addStage(setupStage());
			} catch (Exception e) {
				throw new Exception(e); // Exception for a quest only being able to have 1 test card in it.
			}
		}
		
		ArrayList<Person> participants = askForParticipants();
		
		for(int i = 0; i < card.getNumStages(); i++) {
			//All participants draw a card for the first stage
			for(Person p : participants) {
				p.drawCard(1, deck);
			}
			
			if(card.getStages().get(i).getFoe() != null) {
				//Focus moves to participants who choose their cards one at a time.
				for(Person p : participants) {
					promptPersonToFightFoe(p);
				}
				
				//Check player's battle points against Foe's battlepoints, if they are lower, they are kicked out
				for(Person p : participants) {
					if(p.getBattlePoints() < card.getStages().get(i).getBattlePoints()) {
						participants.remove(p);
						for(Weapon w : p.getWeapons()) {
							p.Remove(p.getWeapons(), discard, w);
						}
					}	
				}
				//remaining participants discard the weapons in their playing field
				for(Person p : participants) {
					for(Weapon w : p.getWeapons()) {
						p.Remove(p.getWeapons(), discard, w);
					}
				}
			} else if (card.getStages().get(i).getTest() != null){
				//It's a test
				int minBid = card.getStages().get(i).getBids();
				int currBid = 0;
				for(Person p : participants) {
					promptPersonToBid(p, participants, minBid, currBid);
				}
			}
		}
		
		// Quest is over, Amours are all discarded.
		for(Person p : players.getPlayers()) {
			for(Amour a : p.getAmour()) {
				p.Remove(p.getAmour(), discard, a);
			}
		}
		
		return true;
	}
	
	public Person askForSponsor() {
		return null;
	}
	
	public Stage setupStage() {
		return null;
	}
	
	public void promptPersonToFightFoe(Person p) {
		//Force person to only choose Weapon, Ally, or Amour
	}
	
	public void promptPersonToBid(Person p, ArrayList<Person> ppts, int minBid, int currBid) {
		//Force person to make their bid, if person cannot make the bid, remove them from the ppts list 
		
		//players discard all bided cards.
		for(Person player : players.getPlayers()) {
			for(Adventure a : player.getBidCards()) {
				player.Remove(player.getBidCards(), discard, a);
			}
		}
	}
	
	public ArrayList<Person> askForParticipants() {
		return null;
	}
}
