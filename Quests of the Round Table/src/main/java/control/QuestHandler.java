package control;

import java.awt.List;
import java.util.ArrayList;

import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Amour;
import model.CardStates;
import model.Foe;
import model.Player;
import model.Players;
import model.Quest;
import model.Stage;
import model.Test;
import model.Weapon;

public class QuestHandler {
	
	private Quest card;
	private AdventureDeck deck;
	private AdventureDiscard discard;
	private Players players;
	private Player player;
	private ArrayList<Adventure> addedCards;
	
	public QuestHandler(Quest c, Players p, Player pr, AdventureDeck d, AdventureDiscard di) {
		card = c;
		deck = d;
		discard = di;
		players = p;
		player = pr;
		addedCards = new ArrayList<>();
	}

	public boolean playQuest() throws Exception {
		//Ask for the sponsor and move focus to them.
		Player sponsor = askForSponsor(player);
		System.out.println("Starting quest...");
		if(sponsor == null) {
			return true; //nobody sponsored so go back
		}
		card.setSponsor(sponsor);
		int sponsorIndex = players.getPlayers().indexOf(sponsor);
		
		PlayGame pg = PlayGame.getInstance();
		boolean seeCards = pg.getView().promptForStageSetup(sponsor.getName());
		if(seeCards) {
			sponsor.setHandState(CardStates.FACE_UP);
		}/*
		for(Adventure a : sponsor.getHand()) {
			if(a instanceof Foe) {
				sponsor.getHand().remove(a);
				Stage stage = new Stage((Foe)a, new ArrayList<Weapon>());
				stage.displayStage();
				card.addStage(stage);
				break;
			}
		}
		for(Adventure a : sponsor.getHand()) {
			if(a instanceof Test) {
				sponsor.getHand().remove(a);
				card.addStage(new Stage((Test)a));
				break;
			}
		}
		for(Adventure a : sponsor.getHand()) {
			if(a instanceof Foe) {
				sponsor.getHand().remove(a);
				ArrayList<Weapon> weapons = new ArrayList<Weapon>();
				for(Adventure ad : sponsor.getHand()) {
					if(ad instanceof Weapon) {
						weapons.add((Weapon)ad);
						break;
					}
				}
				Stage stage = new Stage((Foe)a, weapons);
				stage.displayStage();
				card.addStage(stage);
				break;
			}
		}*/
		
		
		
		for(int i = 0; i < card.getNumStages(); i++) {
			try {
				card.addStage(setupStage(sponsor));
				pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), card);
			} catch (Exception e) {
				throw new Exception(e); // Exception for a quest only being able to have 1 test card in it.
			}
		}
		pg.getView().prompt("Quest");
		/*
		ArrayList<Player> participants = askForParticipants();
		
		for(int i = 0; i < card.getNumStages(); i++) {
			//All participants draw a card for the first stage
			for(Player p : participants) {
				p.drawCard(1, deck);
			}
			
			if(card.getStages().get(i).getFoe() != null) {
				//Focus moves to participants who choose their cards one at a time.
				for(Player p : participants) {
					promptPlayerToFightFoe(p);
				}
				
				//Check player's battle points against Foe's battlepoints, if they are lower, they are kicked out
				for(Player p : participants) {
					if(p.getBattlePoints() < card.getStages().get(i).getBattlePoints()) {
						participants.remove(p);
						for(Weapon w : p.getWeapons()) {
							p.remove(p.getWeapons(), discard, w);
						}
					}	
				}
				//remaining participants discard the weapons in their playing field
				for(Player p : participants) {
					for(Weapon w : p.getWeapons()) {
						p.remove(p.getWeapons(), discard, w);
					}
				}
			} else if (card.getStages().get(i).getTest() != null){
				//It's a test
				int minBid = card.getStages().get(i).getBids();
				int currBid = 0;
				for(Player p : participants) {
					promptPlayerToBid(p, participants, minBid, currBid);
				}
			}
		}
		
		// Quest is over, Amours are all discarded.
		for(Player p : players.getPlayers()) {
			for(Amour a : p.getAmour()) {
				p.remove(p.getAmour(), discard, a);
			}
		}*/
		
		return true;
	}
	
	public Player askForSponsor(Player pr) {
		//pr = player that drew the quest card, so we start there.
		int currentIndex = players.getPlayers().indexOf(pr);
		PlayGame pg = PlayGame.getInstance();
		Player sponsor = null;
		
		for(int i = 0; i < players.getPlayers().size(); i++) {
			
			if(i > 0) {
				pg.getView().rotate(pg);
				if(pg.getView().switchPrompt("Sponsor", players.getPlayers().get((currentIndex + i) % players.getPlayers().size()).getName(), players.getPlayers().get((currentIndex + i) % players.getPlayers().size()))) {
					//this.pg.focusPlayer(players.getPlayers().get(currentIndex + i));
					players.getPlayers().get((currentIndex + 1) % players.getPlayers().size()).setHandState(CardStates.FACE_UP);
					pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), null);
				}
			}
			
			sponsor = askSponsor(0);
			currentIndex += 1 % players.getPlayers().size();
			if(sponsor != null) {
				return sponsor;
			}
		}
		
		return sponsor;
	}
	
	public Player askSponsor(int i) {
		PlayGame pg = PlayGame.getInstance();
		boolean isSponsor =  pg.getView().sponsorPrompt();
		if(isSponsor) {
			System.out.println(players.getPlayers().get(i).getName() + " sponsors.");
			players.getPlayers().get(i).setHandState(CardStates.FACE_DOWN);
			return players.getPlayers().get(i);
		}
		else {
			System.out.println(players.getPlayers().get(i).getName() + " does not sponsor.");
		}
		return null;
	}
	
	public Stage setupStage(Player sponsor) throws Exception {
		PlayGame pg = PlayGame.getInstance();
		
		boolean finished = pg.getView().promptAddCardToStage(addedCards, sponsor);
		if(finished) {
			if(addedCards.get(0) instanceof Test)
				return new Stage((Test)addedCards.get(0));
			else if(addedCards.get(0) instanceof Foe) {
				ArrayList<Weapon> weapons = new ArrayList<>();
				boolean fChoosingWeapons = pg.getView().promptAddWeaponsToFoe(addedCards, sponsor);
				if(fChoosingWeapons) {
					for(Adventure a : addedCards) {
						if(a instanceof Weapon) {
							weapons.add((Weapon)a);
						}
					}
					return new Stage((Foe)addedCards.get(0), weapons);
				}
			}
		}
		return null;
	}
	
	public void promptPlayerToFightFoe(Player p) {
		//Force Player to only choose Weapon, Ally, or Amour
	}
	
	public void promptPlayerToBid(Player p, ArrayList<Player> ppts, int minBid, int currBid) {
		//Force Player to make their bid, if Player cannot make the bid, remove them from the ppts list 
		
		//players discard all bided cards.
		for(Player player : players.getPlayers()) {
			for(Adventure a : player.getBidCards()) {
				player.remove(player.getBidCards(), discard, a);
			}
		}
	}
	
	public ArrayList<Player> askForParticipants() {
		return null;
	}
	
	public static class QuestControlHandler extends ControlHandler {
		@Override
		public void onStageCardPicked() {
			
		}
	}
 	
}
