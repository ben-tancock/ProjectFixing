package control;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
//import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Ally;
import model.Amour;
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
import view.View;

public class PlayGame extends Application{
	
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
	}
	
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
		Application.launch(args);
	}
	
	public static PlayGame getInstance() {
		return instance;
	}
	
	int currentPlayer = 0;

	@Override
	public void start(Stage arg0) throws Exception {
		primStage = arg0; // for restarting the game after win.
		
		/*
		for(int i = 0; i < sDeck.size(); i++) {
			if(sDeck.get(i) instanceof Quest) {
				sDeck.set(0, sDeck.get(i));
				sDeck.remove(i);
			}
		}*/
		//logger.info("Shuffled the decks.");
		
		view.start(arg0);
		//logger.info("Started the view.");
		
		view.twoPlayerButton.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				players.addListener(new PlayGameControlHandler());
				//logger.info("Started the 2 player game.");
				
				for(int i = 0; i < 2; i++) {
					players.addHuman();
				}
				players.getPlayers().get(0).setName("Player 1");
				players.getPlayers().get(1).setName("Player 2");
				players.getPlayers().get(0).setShields(1);
				players.getPlayers().get(1).setShields(2);
				players.getPlayers().get(0).setRank("squire");
				players.getPlayers().get(1).setRank("knight");
				
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
			    doTurn(players.getPlayers().get(0));
				
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
				}
				players.getPlayers().get(0).setShields(1);
				players.getPlayers().get(1).setShields(1);
				players.getPlayers().get(2).setShields(1);
				
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
				}
				players.getPlayers().get(0).setShields(1);
				players.getPlayers().get(1).setShields(1);
				players.getPlayers().get(2).setShields(1);
				players.getPlayers().get(3).setShields(22);
				
				//view.notifyStoryCardClicked(event, sDeck.get(view.getCurrentTopStoryCardIndex()));
				for(Player p : players.getPlayers()) {
					p.drawCard(12, aDeck);
				}
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
	
	// DO TURN ------------------------------------------------------------------------------------------------------------
	public void doTurn(Player p) { // a repurposed focus method 
		logger.info(p.getName() + "'s turn.");
		boolean seeCards = view.seeCardPrompt(p);
		if(seeCards) {
			p.setHandState(CardStates.FACE_UP);
			logger.info("setting " + p.getName() + "'s cards to face up.");
		} else {
			doTurn(p);
		}
		for(Player pr : players.getPlayers()) {
			logger.info(pr.getName() + "'s number of cards: " + pr.getHand().size());
			logger.info(pr.getName() + "'s rank: " + pr.getRankString());
			logger.info(pr.getName() + "'s shields: " + pr.getShields());
			logger.info(pr.getName() + "'s hand: " + pr.getHand());
		}
		view.update(null, players, sDeck, sDiscard, null);
		if(sDeck.size() > 0) {
			view.getStoryCards().getChildren().get(view.getCurrentTopStoryCardIndex()).setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					view.notifyStoryCardClicked(arg0, sDeck.get(view.getCurrentTopStoryCardIndex()));
					for(Player p : players.getPlayers()) {
						p.setHandState(CardStates.FACE_DOWN);
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
					view.update(null, players, sDeck, sDiscard, null);
					view.rotate(PlayGame.getInstance());
					
					doTurn(players.getPlayers().get(0));
				}
			}); 
		}
	// ---------------------------------------------------------------------------------------------------------------------	
		
	}
	
	public void selectCards(Player p) {
		System.out.println("test SC");
		for(Node theCard :view.getPlayerCards().getChildren()) {
			int index = view.getPlayerCards().getChildren().indexOf(theCard);
			//if(p.getHand().get(index) instanceof Weapon) {
			theCard.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					System.out.println("test click");
					if(p.getHand().get(index) instanceof Weapon) {
						p.remove(p.getHand(), p.getWeapons(), p.getHand().get(index));
						//view.update(null, players, sDeck, sDiscard, null);
					}
					else if(p.getHand().get(index) instanceof Ally) {
						p.remove(p.getHand(), p.getAllies(), p.getHand().get(index));
						//view.update(null, players, sDeck, sDiscard, null);
					}
					else if(p.getHand().get(index) instanceof Amour) {
						p.remove(p.getHand(), p.getAmour(), p.getHand().get(index));
						//view.update(null, players, sDeck, sDiscard, null);
					}
					view.update(null, players, sDeck, sDiscard, null);
					selectCards(p);
				}
				
			});
			//}
		}
	}
	
	
	public static class PlayGameControlHandler extends ControlHandler {
		
		@Override
		public void onCardOverflow(Player p) {
			QuestHandler qh = QuestHandler.getInstance();
			while(p.getName() != players.getPlayers().get(0).getName()) {
				view.rotate(PlayGame.getInstance());
			}
			boolean seeCards = view.seeCardPrompt(p);
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
			
			boolean cardsRemoved = view.cardOverflowPrompt(p, p.getHand().size() - 12);
			if(!cardsRemoved) {
				onCardOverflow(p);
			}
		}
		
		@Override
		public void onAdventureCardPlayed(Player p, Adventure card, MouseEvent event) {
			QuestHandler qh = QuestHandler.getInstance();
			//card.setState(CardStates.FACE_DOWN);
			if(card instanceof Ally) {
				p.remove(p.getHand(), p.getAllies(), card);
			} else if (card instanceof Amour) {
				if(p.getAmour().size() >= 1) {
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
		public void onMordredPlayed(Player perp, Player targ) {
			Foe mord = null;
			for(Adventure a : perp.getHand()) {
				if(a.getName().equals("mordred")) {
					mord = (Foe)a;
				}
			}
			perp.remove(perp.getHand(), aDiscard, perp.getCard(perp.getHand().indexOf(mord)));
			if(targ.getAllies().get(0) != null) {
				targ.remove(targ.getAllies(), aDiscard, targ.getAllies().get(0));
			}
		}
		
		@Override
		public void onStoryCardDraw(MouseEvent event) {
			//players.getPlayers().get(0).drawCard(sDeck, sDiscard, "search_for_the_questing_beast");
			players.getPlayers().get(0).drawCard(sDeck, sDiscard);
			QuestHandler qh = QuestHandler.getInstance();
			if(sDeck.isEmpty()) {
				onStoryDeckEmpty();
			}
		}
		
		@Override
		public void onStoryDeckEmpty() {
			Story topCard = sDiscard.get(sDiscard.size() - 1);
			sDeck = new StoryDeck();
			sDiscard = new StoryDiscard();
			sDiscard.add(topCard);
			sDeck.shuffle();
			view.update(null, players, sDeck, sDiscard, null);
		}
		
		@Override
		public void onQuestCardDraw(Player p) {
			Story topCard = sDiscard.get(sDiscard.size() - 1);
			QuestHandler questHandler = new QuestHandler((Quest)topCard, players, p, aDeck, aDiscard);
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
			Story topCard = sDiscard.get(sDiscard.size() - 1);
			EventHandler eventHandler = new EventHandler((Event)topCard, p, players, aDeck, aDiscard);
			view.update(null, players, sDeck, sDiscard, null);
			System.out.println("Event: " + topCard.getName());
		}
		
		@Override
		public void onTournamentCardDraw(Player p) {
			//System.out.println("test tourn draw");
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
		}
		
		@Override
		public void onDiscardCard(Player p, Adventure card) {
			QuestHandler qh = QuestHandler.getInstance();
			if(qh != null && qh.getCard() != null) {
				p.remove(p.getHand(), aDiscard, card);
				view.update(null, players, sDeck, sDiscard, qh.getCard());
			} else {
				p.remove(p.getHand(), aDiscard, card);
				view.update(null, players, sDeck, sDiscard, null);
			}
		}
		
		
		@Override
		public void onRankSet(Player p) {
			view.update(null, players, sDeck, sDiscard, null);
		}
	}
}