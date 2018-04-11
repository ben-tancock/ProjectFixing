package control;

import java.awt.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.layout.HBox;
import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Ally;
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
	private static Foe selectedFoe;
	private static ArrayList<Weapon> selectedWeapons;
	private static boolean foeSelected;
	private static boolean isAskingForParticipants;
	private Quest quest;
	private AdventureDeck deck;
	private AdventureDiscard discard;
	private Players players;
	private ArrayList<Player> participants;
	private Player player;
	//private ArrayList<Adventure> addedCards;
	private ArrayList<Adventure> bidedCards;
	private int currentStage;
	private int numAsked;  //number of times we've asked participants to join
	private PlayGame pg;
	private static QuestHandler instance;
	private boolean isSponsored;
	private static int currentBid;
	private static int numFoughtFoe;
	private boolean questUnderway;
	
	public QuestHandler(Quest c, Players p, Player pr, PlayGame game, AdventureDeck d, AdventureDiscard di) {
		quest = c;
		deck = d;
		discard = di;
		players = p;
		player = pr;
		bidedCards = new ArrayList<>();
		instance = this;
		pg = game;
		isSponsored = false;
		numAsked = 0;
		participants = new ArrayList<Player>();
		selectedWeapons = new ArrayList<Weapon>();
		selectedFoe = null;
	}
	
	public static QuestHandler getInstance() {
		return instance;
	}
	
	public void setFoeSelected(boolean b) {
		foeSelected = b;
	}
	
	public boolean getFoeSelected() {
		return foeSelected;
	}
	
	public void setSelectedFoe(Foe f) {
		selectedFoe = f;
	}
	
	public Foe getSelectedFoe() {
		return selectedFoe;
	}
	
	public ArrayList<Weapon> getSelectedWeapons() {
		return selectedWeapons;
	}
	
	public int getCurrentBid() {
		return currentBid;
	}

	
	
	public boolean playQuest() {
		if(pg.getFoe() && numFoughtFoe < quest.getParticipants().size()) {
			
			pg.getView().announceFoe();
			System.out.println("Current participants: ");
			for(Player part : quest.getParticipants()) {
				System.out.println(part.getName());
			}
			System.out.println("Current participant: " + (numFoughtFoe));
			while(!quest.getParticipants().get(numFoughtFoe).equals(pg.getPlayers().getPlayers().get(0))) {
				pg.getView().rotate(pg);
			}
			quest.getParticipants().get(numFoughtFoe).drawCard(1, deck);
			playCards(quest.getParticipants().get(0));
			
			/*
			getCard().getStages().get(getCurrentStage()).getFoe().setState(CardStates.FACE_UP);
			for(Weapon w : getCard().getStages().get(getCurrentStage()).getFoe().getWeapons()) {
				w.setState(CardStates.FACE_UP);
			}
			pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), quest);*/
		}
		else if(pg.getBidding()) {
			getCard().getStages().get(getCurrentStage()).getTest().setState(CardStates.FACE_UP);
			pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), quest);
			currentStage++;
		}
		else { // if not bidding or playing against a foe, asking to sponsor/participate
			if(!isSponsored) {
				System.out.println("test sponsoring");
				askForSponsor(pg.getPlayers().getPlayers().get(0));
			}
			else if(PlayGame.getInstance().getSettingUpStage()) {
				
			} else if(questUnderway) {
				
			} else {
				System.out.println("TEST ASKING FOR PARTICIPANT");
				askForParticipant();
			}
		}
		return true;
	}
	
	public void onEnd() {
		if (currentStage == getCard().getNumStages() - 1 && PlayGame.getInstance().getSettingUpStage() == true){
			if(foeSelected && selectedFoe != null) {
				((HBox)PlayGame.getInstance().getView().getPlayerCards().getChildren().get(players.getPlayers().get(0).getHand().indexOf(selectedFoe))).setTranslateY(50);
				((HBox)PlayGame.getInstance().getView().getPlayerCards().getChildren().get(players.getPlayers().get(0).getHand().indexOf(selectedFoe))).translateYProperty();
				for(Weapon w : selectedWeapons) {
					((HBox)PlayGame.getInstance().getView().getPlayerCards().getChildren().get(players.getPlayers().get(0).getHand().indexOf(w))).setTranslateY(50);
					((HBox)PlayGame.getInstance().getView().getPlayerCards().getChildren().get(players.getPlayers().get(0).getHand().indexOf(w))).translateYProperty();
				}
				model.Stage stage = new model.Stage(selectedFoe, selectedWeapons);
				players.getPlayers().get(0).getHand().remove(selectedFoe);
				for(Weapon w : selectedWeapons) {
					players.getPlayers().get(0).getHand().remove(w);
				}
				getCard().addStage(stage);
				PlayGame.getInstance().getView().update(null, players, PlayGame.getInstance().getSDeck(), PlayGame.getInstance().getSDiscard(), getCard());
				nextStage();
				foeSelected = false;
				selectedFoe = null;
				selectedWeapons = new ArrayList<>();
			}
			System.out.println("TEST LEAVE STAGE SETUP");
			PlayGame.setSettingUpStage(false);
			currentStage = 0;
			isAskingForParticipants = true;
			pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), quest);
		} else if(PlayGame.getInstance().getSettingUpStage()) {
			System.out.println("Current Stage: " + getCurrentStage());
			System.out.println("TEST STAGE SETUP");
			if(foeSelected && selectedFoe != null) {
				((HBox)PlayGame.getInstance().getView().getPlayerCards().getChildren().get(players.getPlayers().get(0).getHand().indexOf(selectedFoe))).setTranslateY(50);
				((HBox)PlayGame.getInstance().getView().getPlayerCards().getChildren().get(players.getPlayers().get(0).getHand().indexOf(selectedFoe))).translateYProperty();
				for(Weapon w : selectedWeapons) {
					((HBox)PlayGame.getInstance().getView().getPlayerCards().getChildren().get(players.getPlayers().get(0).getHand().indexOf(w))).setTranslateY(50);
					((HBox)PlayGame.getInstance().getView().getPlayerCards().getChildren().get(players.getPlayers().get(0).getHand().indexOf(w))).translateYProperty();
				}
				model.Stage stage = new model.Stage(selectedFoe, selectedWeapons);
				players.getPlayers().get(0).getHand().remove(selectedFoe);
				for(Weapon w : selectedWeapons) {
					players.getPlayers().get(0).getHand().remove(w);
				}
				getCard().addStage(stage);
				PlayGame.getInstance().getView().update(null, players, PlayGame.getInstance().getSDeck(), PlayGame.getInstance().getSDiscard(), getCard());
				nextStage();
				foeSelected = false;
				selectedFoe = null;
				selectedWeapons = new ArrayList<>();
			}
		} else if(pg.getFoe()) {
			System.out.println("TEST FIGHTING FOE");
			numFoughtFoe++;
			if(numFoughtFoe >= quest.getParticipants().size()) {//show resolution of stage
				numFoughtFoe = 0;
				pg.setFoe(false);
				stageResolution();
				weaponCleanup();
				currentStage++;
				pg.getView().rotate(pg);
			}
		} else if(numAsked >= pg.getPlayers().getPlayers().size() - 1 && getCard().getParticipants().size() > 0) {
			//all players but the sponsor have been asked to participate and at least 1 participates.
			questUnderway = true;
			System.out.println("TEST PARTICIPANTS DOING QUEST");
			isAskingForParticipants = false;
			if(getCurrentStage() < getCard().getNumStages()) {
				if(getCard().getStages().get(getCurrentStage()).getFoe() != null) {
					pg.setFoe(true);
					pg.setBidding(false);
				} else if(getCard().getStages().get(getCurrentStage()).getTest() != null) {
					currentBid = getCard().getStages().get(getCurrentBid()).getBids();
					pg.setFoe(false);
					pg.setBidding(true);
				}
			} else {
				System.out.println("TEST ENDING QUEST");
				pg.setFoe(false);
				pg.setBidding(false);
				
				resolveQuest();
			}
		} else {
			System.out.println("NORMAL QUEST ROTATE");
			pg.getView().rotate(PlayGame.getInstance());
		}
	}
	
	
	
	public boolean isValid(Player p, Quest q) { // check if the player has enough cards to sponsor
		int numTests = 0;
		int numFoes = 0;
		
		System.out.println("valid test");
		for(int i = 0; i < p.getHand().size(); i++) {
			if(p.getHand().get(i) instanceof Foe) {
				numFoes += 1;
			}
			else if(p.getHand().get(i) instanceof Test) {
				numTests += 1;
			}
		}
		System.out.println("Num Foes: " + numFoes + ", num tests: " + numTests + ", num stages: " + q.getNumStages());
		if(numFoes >= q.getNumStages() - 1 && numTests >= 1 || numFoes >= q.getNumStages()) {
			System.out.println("player has enough cards to sponsor the quest");
			return true;
		}
		
		System.out.println("player does NOT have enough cards to sponsor this quest");
		pg.getView().notEnoughPrompt();
		return false;
	}
	
	public void nextStage() {
		currentStage += 1;
	}
	
	public int getCurrentStage() {
		return currentStage;
	}
	
	public void askForParticipant() {
		if(!players.getPlayers().get(0).equals(getCard().getSponsor())) {
			askParticipant(0);
		}
	}
	
	public void askParticipant(int i) {
		numAsked += 1;
		System.out.println("test ask " + numAsked);
		boolean join = pg.getView().prompt("Quest Participate");
		if(join) {
			System.out.println(players.getPlayers().get(0) + " has joined the quest.");
			getCard().addParticipant(players.getPlayers().get(0));
		} else {
			System.out.println(players.getPlayers().get(0) + " does not join the quest.");
		}
	}
	
	 public void askForSponsor(Player p){ 
	    	numAsked += 1;
	    	System.out.println("test ask " + numAsked);
	        boolean sponsor = this.pg.getView().prompt("Quest Sponsor"); 
	        if(sponsor && isValid(p, quest)) {
	        	
		            System.out.println(pg.getPlayers().getPlayers().get(0).getName() + " sponsors the Quest");
		            //players.getPlayers().get(0).drawCard(1, deck);
		            //playCards(players.getPlayers().get(0));
		            //isSponsored = true;
		            PlayGame.setSettingUpStage(true);
		            //pg.getView().promptStageSetup();
		            // TO DO: NEED BUTTON FOR SUBMITTING STAGE (change end button functionality?) 
		            isSponsored = true;
		            setupStage(players.getPlayers().get(0));
		            // -------------------------------------------------------------------------
		            getCard().setSponsor(p);
		            numAsked = 0;

	        }
	        else {
	            System.out.println(players.getPlayers().get(0).getName() + " does not sponsor the Quest");
	        }
	        //return pg.getPlayers().getPlayers().get(0);
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
	
	public void playCards(Player p) {
       // p.setAllyBp(p.getAllyBp()); allies are persistent through quest
        p.setHandState(CardStates.FACE_UP);
        pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), null);
       // pg.getView().playPrompt(p.getName(), p, new ArrayList<Adventure>());
        
        // Quest specific prompt?
        pg.getView().playCards();
        
        
    }
	
	public Stage setupStage(Player sponsor) {
		currentStage = 0;
		
		//addedCards = new ArrayList<>();
		/*
		boolean finished = pg.getView().promptAddCardToStage(sponsor);
		
		if(finished) {
			if(addedCards.get(0) instanceof Test) {
				addedCards.get(0).setState(CardStates.FACE_DOWN);
				return new Stage((Test)addedCards.get(0));
			}
			else if(addedCards.get(0) instanceof Foe) {
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
						setStageBP(stage, quest.getSpecialFoes());
						for(Stage s :  quest.getStages()) {
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
						addedCards.removeAll(addedCards);
						pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), quest);
						pg.getView().promptNotEnoughBP();
						setupStage(sponsor);
						return null;
					}
				} 
				return null;
			}
		} else {
		}*/
		return null;
	}
	
	public ArrayList<Adventure> getBidedCards() {
		return bidedCards;
	}

	public Quest getCard() {
		return quest;
	}
	
	public void stageResolution() {
		//need to show resolution of stage for each player and take appropriate action for each
		ArrayList<Adventure> stageCards = new ArrayList<>();
		stageCards.add(quest.getStages().get(getCurrentStage()).getFoe());
		stageCards.addAll(quest.getStages().get(getCurrentStage()).getFoe().getWeapons());
		
		for(Iterator<Player> participantIter = quest.getParticipants().iterator(); participantIter.hasNext();) {
			Player p = participantIter.next();
			if(playerWonStage(p, quest.getStages().get(getCurrentStage()).getFoe())) {
				pg.getView().promptPlayerWon(stageCards, p, quest.getStages().get(getCurrentStage()).getFoe().getFoeBP(quest.getSpecialFoes()));
			} else {
				pg.getView().promptPlayerLost(stageCards, p, quest.getStages().get(getCurrentStage()).getFoe().getFoeBP(quest.getSpecialFoes()));
				participantIter.remove();
			}
		}
	}
	
	public boolean playerWonStage(Player p, Foe f) {
		if(p.getBattlePoints() >= f.getFoeBP(quest.getSpecialFoes())) {
			return true;
		}
		return false;
	}
	
	public void weaponCleanup() {
		for(Player p : pg.getPlayers().getPlayers()) {
			for(Weapon w : p.getWeapons()) {
				p.remove(p.getWeapons(), discard, w);
			}
			
			for(Ally a : p.getAllies()) {
				a.setState(CardStates.FACE_UP);
			}
			
			for(Amour a : p.getAmour()) {
				a.setState(CardStates.FACE_UP);
			}
		}
	}

	public void resolveQuest() {
		
	}
		
	public static class QuestControlHandler extends ControlHandler {
		
		@Override
		public void onBidCardPicked(Player p, Adventure card) {
			PlayGame pg = PlayGame.getInstance();
			QuestHandler qh = QuestHandler.getInstance();
			p.remove(p.getHand(), qh.getBidedCards(), card);
			pg.getView().update(null, pg.getPlayers(), pg.getSDeck(), pg.getSDiscard(), qh.getCard());
		}
	}
 	
}