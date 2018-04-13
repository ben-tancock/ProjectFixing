package control;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;

import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
//import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Ally;
import model.Amour;
import model.Card;
import model.CardStates;
import model.Event;
import model.Foe;
import model.Player;
import model.Players;
import model.Quest;
import model.Tournament;
import model.Weapon;
import model.Story;
import model.StoryDeck;
import model.StoryDiscard;
import model.Test;
import view.View;

@Controller
public class PlayGame extends Application{
	
	//Variables that the Controller needs
	private static final Logger logger = LogManager.getLogger(PlayGame.class);
	private static AdventureDeck aDeck;
	private static AdventureDiscard aDiscard;
	private static StoryDeck sDeck;
	private static StoryDiscard sDiscard;
	private static Players players;
	private static View view;
	private static List<Player> winners;
	private static Stage primStage;
	private static final PlayGame instance = new PlayGame();
	private static boolean KINGS_RECOGNITION;
	
	private static boolean isTournament;
	private static boolean isTie; // tournament tie
	private static boolean isQuest;
	private static boolean isSettingUpStage;
	private static boolean overflow;
	
	private static boolean isFoe;
	
	private static boolean isBidding;
	private static boolean isPlaying;
	
	public PlayGame() {
		aDeck = new AdventureDeck();
		aDiscard = new AdventureDiscard();
		sDeck = new StoryDeck();
		sDiscard = new StoryDiscard();
		players = new Players();
		view = new View();
		winners = new ArrayList<Player>();
		aDeck.shuffle();
		sDeck.shuffle();
		KINGS_RECOGNITION = false;
	}
	
	
	//Constructor built for running test cases.
	public PlayGame(Players riggedPlayers, AdventureDeck riggedDeck, AdventureDiscard riggedDiscard, StoryDeck storyDeck, StoryDiscard storyDiscard ) {
		players = riggedPlayers;
		
		aDeck = riggedDeck;
		aDiscard = riggedDiscard;
		sDeck = storyDeck;
		sDiscard = storyDiscard;
		view = new View();
		winners = new ArrayList<Player>();
		
	}
	
	
	public static void main(String[] args) {
		logger.info("Game Menu starting!");
		//need to move this to happen later.
		Application.launch(args);
	}
	
	public static void setKingsRecognition(boolean kingsRecognition) {
		logger.info("Setting King's Recognition to: " + kingsRecognition);
		KINGS_RECOGNITION = kingsRecognition;
	}
	
	public static boolean isKingsRecognition() {
		return KINGS_RECOGNITION;
	}
	
	public static PlayGame getInstance() {
		return instance;
	}
	
	int currentPlayer = 0;

	@Override
	public void start(Stage arg0) throws Exception {
		primStage = arg0; // for restarting the game after win.
		
		/* this changes the amount of actual cards in story deck...
		for(int i = 0; i < sDeck.size(); i++) {
			if(sDeck.get(i) instanceof Quest && sDeck.get(i).getName().equals("test_of_the_questing_beast")) {
				sDeck.set(0, sDeck.get(i));
				sDeck.remove(i);
			}
		}*/
		logger.info("Shuffled the decks.");
		logger.info("Story Deck Count: " + sDeck.size());
		logger.info("Adventure Deck Count: " + aDeck.size());
		view.start(arg0);
		logger.info("Started the view.");
		// a lot of this code is no longer going to be this way.
		view.twoPlayerButton.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				players.addListener(new PlayGameControlHandler());
				logger.info("Started the 2 player game.");
				
				for(int i = 0; i < 2; i++) {
					players.addHuman();
				}
				players.getPlayers().get(0).setName("Player 1");
				players.getPlayers().get(1).setName("Player 2");
				players.getPlayers().get(0).setShields(1);
				players.getPlayers().get(1).setShields(1);
				players.getPlayers().get(0).setRank("squire");
				players.getPlayers().get(1).setRank("squire");
				players.getPlayers().get(0).setShieldName("shield_1");
				players.getPlayers().get(1).setShieldName("shield_2");
				
				// commented this out because drawing the players story card for them on the initial setup caused null pointer exceptions
				// have to wait for everything to be set up before we can start rotating i think
				//view.notifyStoryCardClicked(arg0, sDeck.get(view.getCurrentTopStoryCardIndex()));
				 /*
				 try {
				 	players.getPlayers().get(0).drawCard(aDeck, "test_of_the_questing_beast");
				 } catch (Exception e) {
				 	// TODO Auto-generated catch block
				 	e.printStackTrace();
				 }
				 players.getPlayers().get(0).drawCard(11, aDeck);
				 players.getPlayers().get(1).drawCard(12, aDeck);*/
				 
				
				for(Player p : players.getPlayers()) {
					p.drawCard(12, aDeck);
				}
				 // current player is the dealer
				view.update(arg0, players, sDeck, sDiscard, null);
				currentPlayer = 0;//(currentPlayer + 1) % 2;
				//focusPlayer(players.getPlayers().get(currentPlayer));
				// ADD A THING HERE WHICH WILL DO THE TURN THING THAT FOCUS DID
			   // doTurn(players.getPlayers().get(0));
				
			}
			
		});
		
		view.threePlayerButton.setOnMouseClicked(new javafx.event.EventHandler <MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				players.addListener(new PlayGameControlHandler());
				for(int i = 0; i < 3; i++) {
					players.addHuman();
				}
				players.getPlayers().get(0).setName("Player 1");
				players.getPlayers().get(1).setName("Player 2");
				players.getPlayers().get(2).setName("Player 3");
				for(Player p : players.getPlayers()) {
					p.setRank("squire");
					p.setShields(1);
				}
				players.getPlayers().get(0).setShieldName("shield_1");
				players.getPlayers().get(1).setShieldName("shield_2");
				players.getPlayers().get(2).setShieldName("shield_3");
				
				//view.notifyStoryCardClicked(event, sDeck.get(view.getCurrentTopStoryCardIndex()));
				for(Player p : players.getPlayers()) {
					p.drawCard(12, aDeck);
				}
				
				currentPlayer = 0;
				view.update(event, players, sDeck, sDiscard, null);
				doTurn(players.getPlayers().get(0));
			}
			
		});
		
		view.fourPlayerButton.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				players.addListener(new PlayGameControlHandler());
				
				
				for(int i = 0; i < 4; i++) {
					players.addHuman();
				}
				players.getPlayers().get(0).setName("Player 1");
				players.getPlayers().get(1).setName("Player 2");
				players.getPlayers().get(2).setName("Player 3");
				players.getPlayers().get(3).setName("Player 4");
				
				for(Player p : players.getPlayers()) {
					p.setRank("squire");
					p.setShields(1);
				}
				players.getPlayers().get(0).setShieldName("shield_1");
				players.getPlayers().get(1).setShieldName("shield_2");
				players.getPlayers().get(2).setShieldName("shield_3");
				players.getPlayers().get(3).setShieldName("shield_4");
				
				//view.notifyStoryCardClicked(event, sDeck.get(view.getCurrentTopStoryCardIndex()));
				for(Player p : players.getPlayers()) {
					p.drawCard(12, aDeck);
				}
				
				//players.getPlayers().get(0).getHand().set(0, element);
				currentPlayer = 0;
				view.update(event, players, sDeck, sDiscard, null);
				doTurn(players.getPlayers().get(0));
			}
			
		});
		
		//ViewController viewController = new ViewController();
		//viewController.start(arg0);
	}
	
	public Players getPlayers(){
		return players;
	}
	
	public AdventureDeck getADeck(){
		return aDeck;
	}
	
	public AdventureDiscard getADiscard(){
		return aDiscard;
	}
	
	public StoryDeck getSDeck(){
		return sDeck;
	}
	
	public StoryDiscard getSDiscard(){
		return sDiscard;
	}
	
	public View getView(){
		return view;
	}
	
	public void setTie(boolean b) {
		isTie = b;
	}
	
	// VARIOUS GAME STATES: BIDDING, PLAYING AGAINST FOE, TOURNAMENT, TIED TOURNAMENT
	public boolean getTie() {
		return isTie;
	}
	
	public void setBidding(boolean b) {
		isBidding = b;
	}
	
	public boolean getBidding() {
		return isBidding;
	}
	
	public void setFoe(boolean b) {
		isFoe = b;
	}
	
	public boolean getFoe() {
		return isFoe;
	}
	
	public void setPlaying(boolean b) {
		isPlaying = b;
	}
	
	public boolean getPlaying() {
		return isPlaying;
	}
	
	
	public static void setTournament(boolean b) {
		isTournament = b;
	}
	
	public static void setSettingUpStage(boolean b) {
		isSettingUpStage = b;
	}
	
	public boolean getSettingUpStage() {
		return isSettingUpStage;
	}
	
	public boolean isTournament() {
		return isTournament;
	}
	
	// --------------------------------------------------------------
	
	
	
	// DO TURN ------------------------------------------------------------------------------------------------------------
	public static void doTurn(Player p) { // a repurposed focus method 
		logger.info(p.getName() + "'s turn.");
		QuestHandler qh = QuestHandler.getInstance();
		TournamentHandler th = TournamentHandler.getInstance();
		//
		for(int i = 0; i < sDeck.size(); i++) {
			if(sDeck.get(i) instanceof Quest && sDeck.get(i).getName().equals("search_for_the_questing_beast")) {
				sDeck.set(0, sDeck.get(i));
			}
		}	
		
		setHand(p);
		
		for(Player pl : players.getPlayers()) {
			pl.setHandState(CardStates.FACE_DOWN);
			if(qh != null && qh.getCard() != null) {
				//view.update(null, players, sDeck, sDiscard, qh.getCard());
			} else { 
				//view.update(null, players, sDeck, sDiscard, null);
			}
		}
		view.update(null, players, sDeck, sDiscard, null);
		
		
		/*boolean seeCards = view.seeCardPrompt(p);
		if(seeCards) {
			p.setHandState(CardStates.FACE_UP);
			logger.info("setting " + p.getName() + "'s cards to face up.");
		} else {
			doTurn(p);
		}*/
		for(Player pr : players.getPlayers()) {
			logger.info(pr.getName() + "'s number of cards: " + pr.getHand().size());
			logger.info(pr.getName() + "'s rank: " + pr.getRankString());
			logger.info(pr.getName() + "'s shields: " + pr.getShields());
			logger.info(pr.getName() + "'s hand: " + pr.getHand());
		}
		
		//we want to make sure that allies have the right BP/Bids
		System.out.println("bp before ally check: " + p.getBattlePoints());
		allyCheck(p);
		
		if(qh != null && qh.getCard() != null) {
			view.update(null, players, sDeck, sDiscard, qh.getCard());
		} else { 
			view.update(null, players, sDeck, sDiscard, null);
		}
		
		if(qh != null) {
			for(Ally a : p.getAllies()) {
				
					a.ability(qh.getCard().getName(), p.getAllies()); // TO DO: handle for null (no story card drawn)
			}
		}
		
		
		if(overflow) {
			System.out.println("test overflow");
			view.cardOverflowPrompt(p, p.getHand().size() - 12);
		}
		else if(isTournament) {
			try {
				th.playTournament();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(isQuest) {
			try {
				qh.playQuest();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	
	}
	
	
	public static void cardClicked(Adventure a, Player p) {
		System.out.println("bp before ally check: " + p.getBattlePoints());
		
		if(overflow) {
			if(a instanceof Weapon) {
				p.remove(p.getHand(), aDiscard, a);
				view.update(null, players, sDeck, sDiscard, null);
			}
			else if (a instanceof Foe) {
				p.remove(p.getHand(), aDiscard, a);
				view.update(null, players, sDeck, sDiscard, null);
			}
			else if (a instanceof Amour) {
				p.remove(p.getHand(), p.getAmour(), a);
				view.update(null, players, sDeck, sDiscard, null);
			}
			else if(a instanceof Ally) {
				p.remove(p.getHand(), p.getAllies(), a);
				view.update(null, players, sDeck, sDiscard, null);
				allyCheck(p);
			}
			
			// when an overflow is done, a prompt MAY follow it, but the prompt needs to wait until the overflow is done
			// for now the only solution i can think of is moving all the prompts to happen after overflow and on doTurn
			if(p.getHand().size() - 12 <= 0) {
				System.out.println("test end overflow");
				overflow = false;
				if(isTournament) {
					//prompt 
				}
				else if (isQuest) {
					//prompt
				}
				else if (isBidding) {
					
				}
				//doTurn(p);
			}
		}
		else if(a.getName().equals("mordred")) {
			if(view.mordredPrompt() == true) {
				p.remove(p.getHand(), aDiscard, a);
				view.update(null, players, sDeck, sDiscard, null);
				doMordred(p);
			}
		}
		else if(isPlaying) {
			System.out.println("test is playing execute");
			if(a instanceof Weapon) {
				p.remove(p.getHand(), p.getWeapons(), a);
				view.update(null, players, sDeck, sDiscard, null);
			}
			else if (a instanceof Amour) {
				p.remove(p.getHand(), p.getAmour(), a);
				view.update(null, players, sDeck, sDiscard, null);
			}
			else if(a instanceof Ally) {
				p.remove(p.getHand(), p.getAllies(), a);
				view.update(null, players, sDeck, sDiscard, null);
				allyCheck(p);
			}
			else {
				
			}
			
		}
		else if(isBidding) {
			
		}
		else if(isSettingUpStage) {
			
			QuestHandler qh = QuestHandler.getInstance();
			//qh.getCard().getStages().get(0).displayStage();
			//model.Stage currStage = new model.Stage();
			model.Stage currStage = qh.getCard().getStages().get(qh.getCurrentStage());
			if(currStage.getTest() == null && currStage.getFoe() != null && a instanceof Weapon) { // if a Test hasn't been selected and a Foe has, weapons can be added
				if(currStage.getFoe().getWeapons().size() > 0) {
					if(!isDuplicate(a, currStage.getFoe().getWeapons())) {
						stageCardSelected(a, qh, p);
						currStage.getFoe().addWeapon((Weapon)a);
					}
				}
				else {
					stageCardSelected(a, qh, p);
					currStage.getFoe().addWeapon((Weapon)a);
				}
			}
			else if(currStage.getTest() == null && currStage.getFoe() == null) { // if neither a test nor a foe have been selected, you can only select those cards
				if(a instanceof Test) {
					stageCardSelected(a, qh, p);
					currStage.setTest((Test)a);
				}
				else if (a instanceof Foe) {
					stageCardSelected(a, qh, p);
					currStage.setFoe((Foe)a);
				}
			}
			
			/*if(qh.getCard().getStages().size() == qh.getCurrentStage() && qh.getCard().getStages().size() < qh.getCard().getNumStages() && !qh.getFoeSelected()) {
				if(a instanceof Foe) {
					//stage counter increased only after the foe has chosen weapons
					qh.setSelectedFoe((Foe)a);
					qh.setFoeSelected(true);
					((HBox)view.getPlayerCards().getChildren().get(p.getHand().indexOf(a))).setTranslateY(-50);
					((HBox)view.getPlayerCards().getChildren().get(p.getHand().indexOf(a))).translateYProperty();
				} else if(a instanceof Test) {
					p.getHand().remove(a);
					model.Stage stage = new model.Stage((Test)a);
					qh.getCard().addStage(stage);
					view.update(null, players, sDeck, sDiscard, qh.getCard());
					qh.nextStage();
					qh.onEnd();
				}
			} else if(qh.getCard().getStages().size() == qh.getCurrentStage() && qh.getCard().getStages().size() < qh.getCard().getNumStages() && qh.getFoeSelected()){
				if(a instanceof Weapon) {
					((HBox)view.getPlayerCards().getChildren().get(p.getHand().indexOf(a))).setTranslateY(-50);
					((HBox)view.getPlayerCards().getChildren().get(p.getHand().indexOf(a))).translateYProperty();
					qh.getSelectedWeapons().add((Weapon)a);
				}
			}*/
		}
		else {
			if(a instanceof Ally) {
				System.out.println("card bp: " + ((Ally) a).getBattlePoints());
				p.remove(p.getHand(), p.getAllies(), a);
				view.update(null, players, sDeck, sDiscard, null);
				allyCheck(p);
			} else if (a instanceof Foe && a.getName().equals("mordred")) {
				//play mordred
			}
			else {
				//p.remove(p.getHand(), p.getAllies(), a);
				//view.update(null, players, sDeck, sDiscard, null);
			}
		}
		allyCheck(p);
		System.out.println("bp after ally check: " + p.getBattlePoints());
	}
	
	public static void stageCardSelected(Adventure c, QuestHandler qh, Player p) {
		if(c instanceof Foe) {
			qh.setSelectedFoe((Foe)c);
			qh.setFoeSelected(true);
			((HBox)view.getPlayerCards().getChildren().get(p.getHand().indexOf(c))).setTranslateY(-50);
			((HBox)view.getPlayerCards().getChildren().get(p.getHand().indexOf(c))).translateYProperty();
			
		}
		else if (c instanceof Weapon) {
			((HBox)view.getPlayerCards().getChildren().get(p.getHand().indexOf(c))).setTranslateY(-50);
			((HBox)view.getPlayerCards().getChildren().get(p.getHand().indexOf(c))).translateYProperty();
			qh.getSelectedWeapons().add((Weapon)c);
		}
		else if (c instanceof Test) {
			qh.setSelectedTest((Test)c);
			((HBox)view.getPlayerCards().getChildren().get(p.getHand().indexOf(c))).setTranslateY(-50);
			((HBox)view.getPlayerCards().getChildren().get(p.getHand().indexOf(c))).translateYProperty();
			/*p.getHand().remove(c);
			model.Stage stage = new model.Stage((Test)c);
			qh.getCard().addStage(stage);
			view.update(null, players, sDeck, sDiscard, qh.getCard());
			qh.nextStage();
			qh.onEnd();*/
		}
	}
	
	public static boolean isDuplicate(Adventure a, ArrayList<Weapon> list) {
		for (Adventure d : list) {
			if(d.getName().equals(a.getName())) {
				System.out.println("DUPLICATE WEAPON");
				return true;
			}
		}
		return false;
	}
	
	public static void doMordred(Player p) {
		// TO DO: have event handlers for ALL players Ally cards in play
		// have all players ally cards do that bring to front thing to make them easier to see
		
		for(int i = 0; i < PlayGame.getInstance().getPlayers().getPlayers().size(); i++) {
			Player q = PlayGame.getInstance().getPlayers().getPlayers().get(i);
			
			if(i == 1) {
				setBehaviour(view.getPlayer2Surface(), q);
			}
			
			else if(i == 2) {
				setBehaviour(view.getPlayer3Surface(), q);
			}
			
			else if(i == 3) {
				setBehaviour(view.getPlayer4Surface(), q);
			}
			
		}
		
	}
	

	
	public static void setBehaviour(HBox field, Player p) {
		for(Node n : field.getChildren()) {
			n.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
				
				@Override
				public void handle(MouseEvent arg0) {
					System.out.println("test field card clicked: " + p.getName());
					int i = field.getChildren().indexOf(n);
					System.out.println("test field card clicked: " + p.getHand().get(i).getName());
					p.remove(p.getAllies(), aDiscard, p.getAllies().get(i));
					view.update(null, players, sDeck, sDiscard, null);
				}
				
			});
			
			n.setOnMouseEntered(new javafx.event.EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					System.out.println("test mouse enter");
					((HBox)n).setPadding(new Insets(0, 0, 0, 0));
				}
			});
			
			n.setOnMouseExited(new javafx.event.EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					((HBox)n).setPadding(new Insets(0, -50, 0, 0));	
				}
			});
		}
		
		
	}
	
	public static void setBehaviour(VBox field, Player p) {
		for(Node n : field.getChildren()) {
			n.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent arg0) {
					System.out.println("test field card clicked: Player " + p.getName());
					int i = field.getChildren().indexOf(n);
					System.out.println("test field card clicked: " + p.getHand().get(i).getName());
					p.remove(p.getAllies(), aDiscard, p.getAllies().get(i));
					view.update(null, players, sDeck, sDiscard, null);
				}
			});
			
		/*	n.setOnMouseEntered(new javafx.event.EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					((VBox)n).setPadding(new Insets(0, 0, 0, 0));
				}
			});
			
			n.setOnMouseExited(new javafx.event.EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					((VBox)n).setPadding(new Insets(0, -50, 0, 0));	
				}
			});*/
		}
	}
	
	public static void setHand(Player p) {
		while(p.getHand().size() < 12) {
			p.drawCard(1, aDeck);
		}
		
		Test qBeast = new Test("test_of_the_questing_beast", 0, CardStates.FACE_DOWN);
		Ally pellinore = new Ally ("king_pellinore", 10, 0, CardStates.FACE_DOWN);
		Ally percival = new Ally ("sir_percival", 5, 0, CardStates.FACE_DOWN);
		Ally tristan = new Ally ("sir_tristan", 10, 0, CardStates.FACE_DOWN);
		Ally arthur = new Ally ("king_arthur", 10, 2, CardStates.FACE_DOWN);
		Ally guinevere = new Ally ("queen_guinevere", 0, 3, CardStates.FACE_DOWN);
		Ally merlin = new Ally ("merlin", 0, 0, CardStates.FACE_DOWN);
		Ally galahad = new Ally ("sir_galahad", 15, 0, CardStates.FACE_DOWN);
		Ally laneclot = new Ally ("sir_lancelot", 15, 0, CardStates.FACE_DOWN);
		Ally iseult = new Ally ("queen_iseult", 0, 2, CardStates.FACE_DOWN);
		Foe mordred = new Foe ("mordred", 30, 30, CardStates.FACE_DOWN);
		
		p.getHand().set(0, qBeast);
		/*p.getHand().set(1, pellinore);
		p.getHand().set(2, percival);
		p.getHand().set(3, tristan);
		p.getHand().set(4, arthur);
		p.getHand().set(5, guinevere);
		p.getHand().set(6, merlin);
		p.getHand().set(7, galahad);
		p.getHand().set(8, laneclot);
		p.getHand().set(9, iseult);
		p.getHand().set(10, mordred);*/
		//p.getHand().set(11, gawain);
		
	}
	
	
	public static void allyClicked(Ally a) {
		if(a.getName() == "merlin") {
			System.out.println("test merlin click");
		}
		else if(a.getName() == "mordred") {
			System.out.println("test mordred click");
		}
	}
	
	public static void allyCheck(Player p) {
		//System.out.println("test ally check");
		QuestHandler qh = QuestHandler.getInstance();
		for(Ally a : p.getAllies()) {
			if(qh != null) {
				//System.out.println("test ally ability");
				a.ability(qh.getCard().getName(), p.getAllies()); // TO DO: handle for null (no story card drawn)
			}
			else {
				//System.out.println("test ally ability quest null");
				a.ability(p.getAllies());
			}
		}
		
	}
	
	
	public static class PlayGameControlHandler extends ControlHandler {
		
		@Override
		public void onCardOverflow(Player p) {
			QuestHandler qh = QuestHandler.getInstance();
			/*while(p.getName() != players.getPlayers().get(0).getName()) {
				view.rotate(PlayGame.getInstance());
			}*/
			
			
			overflow = true;
			doTurn(p);
			//view.cardOverflowPrompt(p, p.getHand().size() - 12);
			//int numDiscard = p.getHand().size() - 12;
			
			
			/*boolean seeCards = view.seeCardPrompt(p);
			if(seeCards) {
				p.setHandState(CardStates.FACE_UP);
				if(qh != null && qh.getCard() != null) {
					view.update(null, players, sDeck, sDiscard, qh.getCard());
				} else {
					view.update(null, players, sDeck, sDiscard, null);
				}
			} else {
				view.seeCardPrompt(p);
			}
			logger.info("card overflow notification received by controller. Invoking the View.");
			boolean cardsRemoved = view.cardOverflowPrompt(p, p.getHand().size() - 12);
			if(!cardsRemoved) {
				onCardOverflow(p);
			}
			//p.setHandState(CardStates.FACE_DOWN);
			if(qh != null && qh.getCard() != null) {
				view.update(null, players, sDeck, sDiscard, qh.getCard());
			} else {
				view.update(null, players, sDeck, sDiscard, null);
			}*/
		}
		
		@Override
		public void onAdventureCardPlayed(Player p, Adventure card, MouseEvent event) {
			QuestHandler qh = QuestHandler.getInstance();
			if(card instanceof Ally) {
				p.remove(p.getHand(), p.getAllies(), card);
				//had to add this so that when the card is played, the change is instantly made.
				if(qh != null && qh.getCard() != null) {
					if(card.getName().equals("sir_percival") && qh.getCard().getName().equals("search_for_the_holy_grail")) {
						((Ally) card).setBattlePoints(20);
						System.out.println(card.getName() + "'s battlepoints are set to: " + ((Ally)card).getBattlePoints() + "(should be 20)");
					} else if(card.getName().equals("sir_gawain") && qh.getCard().getName().equals("test_of_the_green_knight")) {
						((Ally) card).setBattlePoints(20);
						System.out.println(card.getName() + "'s battlepoints are set to: " + ((Ally)card).getBattlePoints() + "(should be 20)");
					} //needs rest of allies that are changed by quests.
				}
				//needs allies that are not changed by quests.
			} else if (card instanceof Amour) {
				if(p.getAmour().size() >= 1) {
					card.setState(CardStates.FACE_UP);
					view.promptTooManyAmour();
				}else {
					p.remove(p.getHand(), p.getAmour(), card);
				} 
			} else if (card instanceof Weapon) {
				boolean dup = false;
				for(Weapon w : p.getWeapons()) {
					if(w.getName().equals(card.getName())) {
						dup = true;
					}
				}
				if(dup) {
					card.setState(CardStates.FACE_UP);
					view.promptWeaponDuplicate(p.getName() + "'s playing field");
				} else {
					p.remove(p.getHand(), p.getWeapons(), card);
				}
			}
			logger.info(p.getName() + " played " + card.getName() + " to their playing surface.");
			logger.info(p.getName() + "'s hand count: " + p.getHand().size() + " Adventure Deck Count: " + aDeck.size());
			if(qh != null && qh.getCard() != null) {
				view.update(event, players, sDeck, sDiscard, qh.getCard());
			} else {
				view.update(event, players, sDeck, sDiscard, null);
			}
		}
		
		@Override
		public void onPlayerVictory(Player p) {
			System.out.println(p.getName() + " has become knight of the round table!");
			winners.add(p);
		}
		
		@Override
		public void onMordredPicked(Player perp, Foe mordred) {
		//	perp.discard(mordred, discardPile, onPlaySurface);
			onDiscardCard(perp, mordred, false);
			view.promptToKillAlly(players, perp);
		}
		
		@Override
		public void onStoryCardDraw(MouseEvent event) {
			//players.getPlayers().get(0).drawCard(sDeck, sDiscard, "boar_hunt");
			System.out.println("test");
			players.getPlayers().get(0).drawCard(sDeck, sDiscard);
			if(sDeck.isEmpty()) {
				onStoryDeckEmpty();
			}
		}
		
		@Override
		public void onStoryDeckEmpty() {
			QuestHandler qh = QuestHandler.getInstance();
			Story topCard = sDiscard.get(sDiscard.size() - 1);
			sDeck = new StoryDeck();
			sDiscard = new StoryDiscard();
			sDiscard.add(topCard);
			sDeck.shuffle();
			if(qh != null && qh.getCard() != null) {
				view.update(null, players, sDeck, sDiscard, qh.getCard());
			} else { 
				view.update(null, players, sDeck, sDiscard, null);
			}
		}
		
		@Override
		public void onAdventureDeckEmpty(Player p, int cardsLeftToDraw) {
			if(aDiscard.size() > 0) {
				aDeck.addAll(aDiscard);
				aDeck.shuffle();
				aDiscard.removeAll(aDiscard);
				
				System.out.println("Adventure Deck emptied, cards left to draw: " + cardsLeftToDraw);
				p.drawCard(cardsLeftToDraw, aDeck);
			}
			else {
				/* For Testing only
				int allyCount = 0;
				for(Adventure a : p.getHand()) {
					if(a instanceof Ally) {
						allyCount++;
						System.out.println(a.getName());
					}
				}
				System.out.println("number of allies in " + p.getName() + "'s hand: " + allyCount);
				//--------------------------------------------------------------------------------*/
				
				view.promptNoAdventureCardsLeft();
			}
		}
		
		@Override
		public void onQuestCardDraw(Player p) {
			isQuest = true;
			Story topCard = sDiscard.get(sDiscard.size() - 1);
			QuestHandler questHandler = new QuestHandler((Quest)topCard, players, p, PlayGame.getInstance(), aDeck, aDiscard);
			view.update(null, players, sDeck, sDiscard, (Quest) topCard);
			System.out.println("Quest: " + topCard.getName());
			try {
				questHandler.playQuest();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void onEventCardDraw(Player p) {
			PlayGame pg = PlayGame.getInstance();
			Story topCard = sDiscard.get(sDiscard.size() - 1);
			EventHandler eventHandler = new EventHandler((Event)topCard, p, players, aDeck, aDiscard);
			view.update(null, players, sDeck, sDiscard, null);
			System.out.println("Event: " + topCard.getName());
			//so that the view rotates back to the player who drew the tournament card for the game to resume to the player on the left.
			while(!pg.getPlayers().getPlayers().get(0).equals(p)) {
				pg.getView().rotate(pg);
			}
		}
		
		@Override
		public void onTournamentCardDraw(Player p) {
			//System.out.println("test tourn draw");
			isTournament = true;
			PlayGame pg = PlayGame.getInstance();
			view.update(null, players, sDeck, sDiscard, null);
			Story topCard = sDiscard.get(sDiscard.size() - 1);
			TournamentHandler tourneyHandler = new TournamentHandler((Tournament)topCard, PlayGame.getInstance(), p);
			System.out.println("Tournament: " + topCard.getName());
			try {
				tourneyHandler.playTournament();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//so that the view rotates back to the player who drew the tournament card for the game to resume to the player on the left.
			/*while(!pg.getPlayers().getPlayers().get(0).equals(p)) {
				pg.getView().rotate(pg);
			}*/
			//isTournament = false;
		}
		
		@Override
		public void onDiscardCard(Player p, Adventure card, boolean onPlayingSurface) {
			QuestHandler qh = QuestHandler.getInstance();
			if(onPlayingSurface) {
				if(card instanceof Ally) {
					p.remove(p.getAllies(), aDiscard, card);
				} else if(card instanceof Amour) {
					p.remove(p.getAmour(), aDiscard, card);
				} else if(card instanceof Weapon) {
					p.remove(p.getWeapons(), aDiscard, card);
				}
			} else {
				p.remove(p.getHand(), aDiscard, card);
			}
			card.setState(CardStates.FACE_DOWN);
			p.setHandState(CardStates.FACE_DOWN);
			if(qh != null && qh.getCard() != null) {
				view.update(null, players, sDeck, sDiscard, qh.getCard());
			} else {
				view.update(null, players, sDeck, sDiscard, null);
			}
			logger.info(p.getName() + " discarded a card.");
			logger.info(p.getName() + "'s hand count: " + p.getHand().size() + " Adventure Deck Count: " + aDeck.size() + " Adventure Discard Count: " + aDiscard.size());
		}
		
		
		@Override
		public void onRankSet(Player p) {
			view.update(null, players, sDeck, sDiscard, null);
		}
		
		@Override
		public void onKingsRecognition() {
			logger.info("King's Recognition set by controller to true, the next player to finish a quest will receive 2 bonus shields.");
			PlayGame.setKingsRecognition(true);
		}
		
		public void onPlaying() {
			System.out.println("test playing");
			isPlaying = true;
		}
		
		@Override
		public void onUpdate() {
			System.out.println("update");
			
			for(Node n : view.getPlayerSurface().getChildren()) {
				n.setOnMouseEntered(new javafx.event.EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						((HBox)n).setPadding(new Insets(0, 0, 0, 0));
					}
				});
				n.setOnMouseExited(new javafx.event.EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						((HBox)n).setPadding(new Insets(0, -50, 0, 0));	
					}
				});
			}
			
			for(Node n : view.getPlayerCards().getChildren()) {
				n.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						System.out.println("this is a " + players.getPlayers().get(0).getHand().get(view.getPlayerCards().getChildren().indexOf(n)).getName());
						Player p = players.getPlayers().get(0);
						Adventure a = players.getPlayers().get(0).getHand().get(view.getPlayerCards().getChildren().indexOf(n));
						cardClicked(a, p);						
					}
				});
				
				n.setOnMouseEntered(new javafx.event.EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						((HBox)n).setPadding(new Insets(0, 0, 0, 0));
					}
				});
				n.setOnMouseExited(new javafx.event.EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						((HBox)n).setPadding(new Insets(0, -50, 0, 0));	
					}
				});
			}
			
			for(Node n : view.getPlayerSurface().getChildren()) {
				n.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						//System.out.println("test field card clicked");
					}
					
				});
			}
	
			view.endButton.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					for(Weapon w : players.getPlayers().get(0).getWeapons()) {
						w.setState(CardStates.FACE_DOWN);
					}
					for(Amour a : players.getPlayers().get(0).getAmour()) {
						a.setState(CardStates.FACE_DOWN);
					}
					System.out.println("test end turn button");
					isPlaying = false;
					
					if(isTournament) {
						TournamentHandler th = TournamentHandler.getInstance();
						th.onEnd();
					}
					else if (isQuest) {
						QuestHandler qh = QuestHandler.getInstance();
						qh.onEnd();
					}
					else {
						System.out.println("NORMAL ROTATE");
						view.rotate(PlayGame.getInstance());
						//doTurn(players.getPlayers().get(0));
					}
					doTurn(players.getPlayers().get(0));
				}
			});
			
			QuestHandler qh = QuestHandler.getInstance();
			if(sDeck.size() > 0) {
				view.getStoryCards().getChildren().get(view.getCurrentTopStoryCardIndex()).setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						view.notifyStoryCardClicked(arg0, sDeck.get(view.getCurrentTopStoryCardIndex()));
						for(Player p : players.getPlayers()) {
							//p.setHandState(CardStates.FACE_DOWN);
						}
						if(winners.size() == 1) {
							System.out.println("Congratulations " + winners.get(0).getName() + "! You Win!");
							boolean gameRestart = view.promptGameEnd(winners.get(0));
							if(gameRestart) {
								primStage.close();
								try {
									PlayGame playGame = new PlayGame();
									playGame.start(primStage);
									return;
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								primStage.close();
								return;
							}
						} else if (winners.size() > 1) {
							//begin final tournament!
						}
						if(qh != null && qh.getCard() != null) {
							view.update(null, players, sDeck, sDiscard, qh.getCard());
						} else { 
							view.update(null, players, sDeck, sDiscard, null);
						}
						//view.rotate(PlayGame.getInstance());
						
						//doTurn(players.getPlayers().get(0));
					}
				}); 
			}
		}
	}
	// END PGCONTROL HANDLER --------------------------------------------
}

