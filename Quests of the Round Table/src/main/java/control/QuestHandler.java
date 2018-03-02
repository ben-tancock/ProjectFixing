package control;

import java.awt.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
	private static final Logger logger = LogManager.getLogger(QuestHandler.class);
	private Quest card;
	private AdventureDeck deck;
	private AdventureDiscard discard;
	private Players players;
	private Player player;
	private ArrayList<Adventure> addedCards;
	private ArrayList<Adventure> bidedCards;
	private static QuestHandler instance;
	
	public QuestHandler(Quest c, Players p, Player pr, AdventureDeck d, AdventureDiscard di) {
		card = c;
		deck = d;
		discard = di;
		players = p;
		player = pr;
		addedCards = new ArrayList<>();
		bidedCards = new ArrayList<>();
		instance = this;
	}
	
	public static QuestHandler getInstance() {
		return instance;
	}

	public boolean playQuest() {
		//Ask for the sponsor and move focus to them.
		Player sponsor = askForSponsor(player);
		System.out.println("Starting quest...");
		if(sponsor == null) {
			return true; //nobody sponsored so go back
		}
		
		getCard().setSponsor(sponsor);
		
		PlayGame pg = PlayGame.getInstance();
		
		int oldSize = sponsor.getHand().size();
		for(int i = 0; i < getCard().getNumStages(); i++) {
			try {
				getCard().addStage(setupStage(sponsor));
				logger.info("Stage " + (i+1) + " added: " + getCard().getStages().get(i).toString());
				pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), getCard());
			} catch (NullPointerException e) {
				//Sponsor dropped sponsoring, so give the sponsor their cards back before continuing
				logger.error("Sponsor didn't finish sponsoring, they have been awarded back their cards.");
				for(Stage stage : getCard().getStages()) {
					if(stage != null) {
						if(stage.getFoe() != null) {
							stage.getFoe().setState(CardStates.FACE_UP);
							sponsor.getHand().add(stage.getFoe());
							if(stage.getFoe().getWeapons() != null) {
								for(Weapon w : stage.getFoe().getWeapons()) {
									w.setState(CardStates.FACE_UP);
								}
								sponsor.getHand().addAll(stage.getFoe().getWeapons());
							}
						} else if(stage.getTest() != null) {
							stage.getTest().setState(CardStates.FACE_UP);
							sponsor.getHand().add(stage.getTest());
						}
					}
				}
				return false;
			}
		}
		
		int newSize = sponsor.getHand().size();
		int numCardsUsed = oldSize - newSize;
		sponsor.setHandState(CardStates.FACE_DOWN);
		pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), getCard());
		ArrayList<Player> participants = askForParticipants(sponsor, players.getPlayers().get(0));
		for(int i = 0; i < card.getNumStages(); i++) {
			//All participants draw a card for the first stage
			for(Player p : participants) {
				p.drawCard(1, deck);
				p.setHandState(CardStates.FACE_DOWN);
				pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), card);
			}
			System.out.println("Iter: " + i);
			
			if(card.getStages().get(i).getFoe() != null) {
				//Focus moves to participants who choose their cards one at a time.
				for(Player p : participants) {
					promptPlayerToFightFoe(p);
					p.setHandState(CardStates.FACE_DOWN);
					pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), card);
				}
				//Check player's battle points against Foe's battlepoints, if they are lower, they are kicked out
				for(Iterator<Player> participantIterator = participants.iterator(); participantIterator.hasNext();) {
					Player p = participantIterator.next();
					if(p.getBattlePoints() < card.getStages().get(i).getBattlePoints()) {
						participantIterator.remove();
						ArrayList<Adventure> stageCards = new ArrayList<>();
						stageCards.add(card.getStages().get(i).getFoe());
						stageCards.addAll(card.getStages().get(i).getFoe().getWeapons());
						pg.getView().promptPlayerLost(stageCards, p, card.getStages().get(i).getBattlePoints());
						pg.getADiscard().addAll(p.getWeapons());
						p.getWeapons().removeAll(p.getWeapons());
					} else {
						//player won
						for(Iterator<Adventure> playerSurfaceIterator = p.getPlayingSurface().iterator(); playerSurfaceIterator.hasNext();) {
							Adventure a = playerSurfaceIterator.next();
							a.setState(CardStates.FACE_UP);
						}
						ArrayList<Adventure> stageCards = new ArrayList<>();
						stageCards.add(card.getStages().get(i).getFoe());
						stageCards.addAll(card.getStages().get(i).getFoe().getWeapons());
						System.out.println(card.getStages().get(i).getFoe().getName());
						pg.getView().promptPlayerWon(stageCards, p, card.getStages().get(i).getBattlePoints());
						pg.getADiscard().addAll(p.getWeapons());
						p.getWeapons().removeAll(p.getWeapons());
					}
					
				}
				pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), card);
				//remaining participants discard the weapons in their playing field
				for(Iterator<Player> participantIterator1 = participants.iterator(); participantIterator1.hasNext();) {
					Player p1 = participantIterator1.next();
					p1.getWeapons().removeAll(p1.getWeapons());
					discard.addAll(p1.getWeapons());
					pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), card);
				}
				
				
			} else if (card.getStages().get(i).getTest() != null){
				//It's a test
				int minBid = card.getStages().get(i).getBids();
				
				Player bidWinner = promptPlayerToBid(participants, minBid);
				for(Iterator<Player> participantIterator = participants.iterator(); participantIterator.hasNext();) {
					Player p = participantIterator.next(); //remove everyone else that isn't the winner of the bid.
					if(!p.equals(bidWinner)) {
						participantIterator.remove();
					}
				}
				System.out.println("Amount of participants: (Should be 1 if player won bid)" + participants.size());
			}
		}
		//Stage cards are all discarded
		for(Stage stage : getCard().getStages()) {
			if(stage.getTest() != null) {
				//we only add to discard pile since entire quest instance is scrapped after.
				pg.getADiscard().add(stage.getTest());
			} else if(stage.getFoe() != null) {
				pg.getADiscard().addAll(stage.getFoe().getWeapons());
				pg.getADiscard().add(stage.getFoe());
			}
		}
		
		//Winning participants get shields
		for(Iterator<Player> participantIterator = participants.iterator(); participantIterator.hasNext();) {
			Player p = participantIterator.next();
			p.setShields(p.getShields() + card.getNumStages());
		}
		//sponsor then draws back num cards used + numstages
		sponsor.drawCard(numCardsUsed + card.getNumStages(), deck);
		
		// Quest is over, Amours are all discarded.
		for(Iterator<Player> playerIterator = players.getPlayers().iterator(); playerIterator.hasNext();) {
			Player p = playerIterator.next();
			pg.getADiscard().addAll(p.getAmour());
			p.getAmour().removeAll(p.getAmour());
		}
		
		logger.info("Tacking all adventure card amounts:");
		int totalAmountOfCards = 0;
		for(Iterator<Player> playerIterator = players.getPlayers().iterator(); playerIterator.hasNext();) {
			Player p = playerIterator.next();
			logger.info(p.getName() + "'s hand card count: " + p.getHand().size());
			logger.info(p.getName() + "'s surface card count: " + p.getPlayingSurface().size());
			totalAmountOfCards += p.getHand().size();
			totalAmountOfCards += p.getPlayingSurface().size();
		}
		logger.info("total player card count: " + totalAmountOfCards);
		totalAmountOfCards += pg.getADeck().size();
		logger.info("Adventure Deck card count: " + pg.getADeck().size());
		totalAmountOfCards += pg.getADiscard().size();
		logger.info("Adventure Discard card count: " + pg.getADiscard().size());
		logger.info("Total amount of Adventure Cards accounted for: " + totalAmountOfCards);
		pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), null);
		return true;
	}
	
	public Player askForSponsor(Player pr) {
		//pr = player that drew the quest card, so we start there.
		PlayGame pg = PlayGame.getInstance();
		Player sponsor = null;
		
		for(int i = 0; i < players.getPlayers().size(); i++) {
			
			if(i > 0) {
				pg.getView().rotate(pg);
				pg.doTurn(players.getPlayers().get(0));
			}
			sponsor = askSponsor(0);
			if(sponsor != null) {
				logger.info(card.getName() + "'s sponsor is: " + sponsor.getName());
				return sponsor;
			}
		}
		
		return sponsor;
	}
	
	public Player askSponsor(int i) {
		PlayGame pg = PlayGame.getInstance();
		logger.info(players.getPlayers().get(i).getName() + " asked to sponsor.");
		boolean isSponsor =  pg.getView().sponsorPrompt();
		if(isSponsor) {
			return players.getPlayers().get(i);
		}
		else {
			System.out.println(players.getPlayers().get(i).getName() + " does not sponsor.");
			logger.info(players.getPlayers().get(i).getName() + " does not sponsor");
			return null;
		}
	}
	
	public Stage setupStage(Player sponsor) {
		PlayGame pg = PlayGame.getInstance();
		addedCards = new ArrayList<>();
		
		boolean finished = pg.getView().promptAddCardToStage(sponsor);
		
		if(finished) {
			if(addedCards.get(0) instanceof Test) {
				addedCards.get(0).setState(CardStates.FACE_DOWN);
				return new Stage((Test)addedCards.get(0)); 
			}
			else if(addedCards.get(0) instanceof Foe) {
				Stage stage = setupFoe(sponsor);
				System.out.println(stage.getBattlePoints());
				System.out.println(finished);
				return stage;
			}
		} else {
		}
		return null;
	}
	
	public Stage setupFoe(Player sponsor) {
		PlayGame pg = PlayGame.getInstance();
		ArrayList<Weapon> weapons = new ArrayList<Weapon>();
		boolean fChoosingWeapons = pg.getView().promptAddWeaponsToFoe(sponsor, new ArrayList<Weapon>());
		if(fChoosingWeapons) {
			for(Adventure a : addedCards) {
				if(a instanceof Weapon) {
					weapons.add((Weapon)a); 
				}
			}
			Stage stage = new Stage((Foe)addedCards.get(0), weapons);
			
			boolean enoughBP = true;
			if(stage.getFoe() != null) {
				setStageBP(stage, card.getSpecialFoes());
				for(Stage s :  card.getStages()) {
					if(s.getFoe() != null && s.getBattlePoints() >= stage.getBattlePoints()) {
						enoughBP = false;
						logger.info("battle points not high enough detected!");
					}
					
				}
				
			}
			if(enoughBP) {
				System.out.println(stage.getBattlePoints());
				return stage;
			} else {
				for(Adventure a : addedCards) {
					a.setState(CardStates.FACE_UP);
				}
				sponsor.getHand().addAll(addedCards);
				pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), card);
				pg.getView().promptNotEnoughBP();
				setupFoe(sponsor);
				return stage;
			}
		} else {
			return null;
		}
		
	}
	
	public void setStageBP(Stage s, String specialFoes) {
		if(specialFoes.equals("all")) {
			s.setBattlePoints(s.getFoe().getHigherBattlePoints());
		} else if(specialFoes.equals("all_saxons")) {
			if(s.getFoe().getName().contains("saxon")) {
				s.setBattlePoints(s.getFoe().getHigherBattlePoints());
			} else {
				s.setBattlePoints(s.getFoe().getLowerBattlePoints());
			}
		} else if(s.getFoe().getName().equals(specialFoes)) {
			s.setBattlePoints(s.getFoe().getHigherBattlePoints());
		} else {
			s.setBattlePoints(s.getFoe().getLowerBattlePoints());
		}
	}
	
	public ArrayList<Adventure> getAddedCards() {
		return addedCards;
	}
	
	public ArrayList<Adventure> getBidedCards() {
		return bidedCards;
	}
	
	public void promptPlayerToFightFoe(Player p) {
		//Force Player to only choose Weapon, Ally, or Amour
		PlayGame pg = PlayGame.getInstance();
		while(!players.getPlayers().get(0).equals(p)) {
			pg.getView().rotate(pg);
		}
		pg.doTurn(p);
		pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), card);
		pg.getView().playPrompt(p.getName(), p, new ArrayList<Adventure>());
	}
	
	public Player promptPlayerToBid(ArrayList<Player> ppts, int minBid) {
		//Force Player to make their bid, if Player cannot make the bid, remove them from the ppts list 
		PlayGame pg = PlayGame.getInstance();
		int currBid = minBid;
		Player bidWinner = null;
		for(Player p : ppts) {
			while(!players.getPlayers().get(0).equals(p)) {
				pg.getView().rotate(pg);
			}
			pg.doTurn(p);
			pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), card);
			Player bidP = pg.getView().promptBid(currBid, p);
			if(bidP != null) {
				p = bidP;
				currBid = p.getBid();
				p.setHandState(CardStates.FACE_DOWN);
				pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), card);
				bidWinner = p;
			} else {
				ppts.remove(p);
				p.setHandState(CardStates.FACE_DOWN);
				pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), card);
			}
		}
		//winning player discards all bided cards.
		if(bidWinner != null) {
			if(bidWinner.getBid() > 0) {
				pg.doTurn(bidWinner);
				bidedCards = pg.getView().bidDiscardPrompt(bidWinner, bidWinner.getBid(), true);// will get these from a discard prompt.
				if(bidedCards != null) {
					discard.addAll(bidedCards);
					bidedCards.removeAll(bidedCards);
				} else {
					bidWinner = null; // prevent player from winning if they don't bid cards
				}
			}
			return bidWinner;
		}
		return null;
	}
	
	public ArrayList<Player> askForParticipants(Player sponsor, Player start) {
		PlayGame pg = PlayGame.getInstance();
		ArrayList<Player> participants = new ArrayList<>();
		Player participant;
		for(int i = 0; i < (players.getPlayers().size() - 1); i++) {	// skip the quest sponsor
			pg.getView().rotate(pg);	
		
			if(!players.getPlayers().get(0).equals(sponsor)) {
				pg.doTurn(players.getPlayers().get(0));
				participant = askForParticipant(0);
				if(participant != null) {
					participants.add(participant);
				}
			}
		}
		logger.info("List of participants: ");
		for(Player part : participants) {
			logger.info(part.getName());
		}
		return participants;
	}
	
	public Player askForParticipant(int i) {
		PlayGame pg = PlayGame.getInstance();
		boolean isParticipant =  pg.getView().prompt("Quest");
		if(isParticipant) {
			System.out.println(players.getPlayers().get(i).getName() + " participates.");
			players.getPlayers().get(i).setHandState(CardStates.FACE_DOWN);
			return players.getPlayers().get(i);
		}
		else {
			System.out.println(players.getPlayers().get(i).getName() + " does not participate.");
			players.getPlayers().get(i).setHandState(CardStates.FACE_DOWN);
			return null;
		}
	}
	
	public Quest getCard() {
		return card;
	}

	public static class QuestControlHandler extends ControlHandler {
		@Override
		public void onStageCardPicked(Player p, Adventure card) {
			QuestHandler qh = QuestHandler.getInstance();
			PlayGame pg = PlayGame.getInstance();
			p.remove(p.getHand(), qh.getAddedCards(), card);
			pg.getView().update(null, pg.getPlayers(), pg.getSDeck(), pg.getSDiscard(), qh.getCard());
		}
		
		@Override
		public void onStageWeaponPicked(Player p, Weapon card) {
			QuestHandler qh = QuestHandler.getInstance();
			PlayGame pg = PlayGame.getInstance();
			p.remove(p.getHand(), qh.getAddedCards(), card);
			pg.getView().update(null, pg.getPlayers(), pg.getSDeck(), pg.getSDiscard(), qh.getCard());
		}
		
		@Override
		public void onBidCardPicked(Player p, Adventure card) {
			PlayGame pg = PlayGame.getInstance();
			QuestHandler qh = QuestHandler.getInstance();
			p.remove(p.getHand(), qh.getBidedCards(), card);
			pg.getView().update(null, pg.getPlayers(), pg.getSDeck(), pg.getSDiscard(), qh.getCard());
		}
	}
 	
}
