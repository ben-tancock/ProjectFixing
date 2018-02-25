package control;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
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
import model.Tournament;
import model.Weapon;
import model.Story;
import model.StoryDeck;
import model.StoryDiscard;
import view.View;

public class PlayGame extends Application{
	
	//private static final Logger logger = LogManager.getLogger(PlayGame.class);
	private static AdventureDeck aDeck;
	private static AdventureDiscard aDiscard;
	private static StoryDeck sDeck;
	private static StoryDiscard sDiscard;
	private static Players players;
	private static View view;
	private static final PlayGame instance = new PlayGame();
	
	public PlayGame() {
		aDeck = new AdventureDeck();
		aDiscard = new AdventureDiscard();
		sDeck = new StoryDeck();
		sDiscard = new StoryDiscard();
		players = new Players();
		view = new View();
	}
	
	public static void main(String[] args) {
		//logger.info("Game Menu starting!");
		Application.launch(args);
	}
	
	public static PlayGame getInstance() {
		return instance;
	}
	
	int currentPlayer = 0;

	@Override
	public void start(Stage arg0) throws Exception {
		aDeck.shuffle();
		sDeck.shuffle();
		//logger.info("Shuffled the decks.");
		
		view.start(arg0);
		//logger.info("Started the view.");
		
		view.twoPlayerButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

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
				// commented this out because drawing the players story card for them on the initial setup caused null pointer exceptions
				// have to wait for everything to be set up before we can start rotating i think
				//view.notifyStoryCardClicked(arg0, sDeck.get(view.getCurrentTopStoryCardIndex()));
				
				
				for(Player p : players.getPlayers()) {
					p.drawCard(12, aDeck);
				}
				 // current player is the dealer
				view.update(arg0, players, sDeck, sDiscard, aDiscard);
				int iters = 0;
				currentPlayer = 0;//(currentPlayer + 1) % 2;
				//focusPlayer(players.getPlayers().get(currentPlayer));
				// ADD A THING HERE WHICH WILL DO THE TURN THING THAT FOCUS DID
			    doTurn(players.getPlayers().get(0));
				
			}
			
		});
		
		view.threePlayerButton.setOnMouseClicked(new EventHandler <MouseEvent>() {

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
				
				view.notifyStoryCardClicked(event, sDeck.get(view.getCurrentTopStoryCardIndex()));
				for(Player p : players.getPlayers()) {
					p.drawCard(12, aDeck);
				}
				view.update(event, players, sDeck, sDiscard, aDiscard);
				
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
		boolean seeCards = view.seeCardPrompt(p);
		if(seeCards) {
			System.out.println("setting " + p.getName() + " hand to face up");
			p.setHandState(CardStates.FACE_UP);
		} else {
			doTurn(p);
		}
		view.update(null, players, sDeck, sDiscard, aDiscard);
		if(sDeck.size() > 0) {
			view.getStoryCards().getChildren().get(view.getCurrentTopStoryCardIndex()).setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					view.notifyStoryCardClicked(arg0, sDeck.get(view.getCurrentTopStoryCardIndex()));
					for(Player p : players.getPlayers()) {
						//p.setHandState(CardStates.FACE_DOWN);
					}
					view.update(null, players, sDeck, sDiscard, aDiscard);
					view.rotate(PlayGame.getInstance());
					
					doTurn(players.getPlayers().get(0));
				}
			}); 
		}
	// ---------------------------------------------------------------------------------------------------------------------	
		
	}
	
	
	
	//Used to rotate between players to emulate a "focus on current player" feel so that the drawing of a story card and "turns" can be simulated.
	/*public void focusPlayer(Player p) {
		p.setFocused(true);
		p.setHandState(CardStates.FACE_UP);
		view.update(null, players, sDeck, sDiscard);
		//PlayGame game = this;
		if(sDeck.size() > 0) {
			view.getStoryCards().getChildren().get(view.getCurrentTopStoryCardIndex()).setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					view.notifyStoryCardClicked(arg0, sDeck.get(view.getCurrentTopStoryCardIndex()));
					
					//Code should not execute until everything else is handled. Turns off the focus after activity is finished.
					p.setFocused(false);
					//p.setHandState(CardStates.FACE_DOWN);
					view.update(null, players, sDeck, sDiscard);
					//Set next focused player.
					currentPlayer = (currentPlayer + 1) % 2;
					focusPlayer(players.getPlayers().get(currentPlayer));
				}
			}); 
		}
		
		
	}*/
	
	public static class PlayGameControlHandler extends ControlHandler {

		@Override
		public void onCardOverflow(Player p) {
			System.out.println(p.getName() + " has too many cards, must either discard or play an ally.");
			for(int i = 0; i < p.getHand().size(); i++) {
				if(p.getHand().get(i).getClass().getSimpleName().equals("Ally") && p.getHand().size() > 12) {
					p.remove(p.getHand(), p.getAllies(), p.getHand().get(i));
				}
			}
		}
		
		@Override
		public void onAdventureCardPlayed(Player p, Adventure card, MouseEvent event) {
			System.out.println(p.getName() + " has drawn a card.");			
			if(card instanceof Ally) {
				p.remove(p.getHand(), p.getAllies(), card);
			} else if (card instanceof Amour) {
				if(p.getAmour().size() >= 1) {
					//show an error alert
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
					System.out.println("Duplicate!!" + card.getName());
				} else {
					p.remove(p.getHand(), p.getWeapons(), card);
				}
			}
			view.update(event, players, sDeck, sDiscard, aDiscard);	
			//p.discard(card, aDiscard, true);
		}
		
		@Override
		public void onPlayerVictory(Player p) {
			System.out.println(p.getName() + " has become knight of the round table!");
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
			players.getPlayers().get(0).drawCard(sDeck, sDiscard);
			view.update(event, players, sDeck, sDiscard,aDiscard);
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
			view.update(null, players, sDeck, sDiscard, aDiscard);
		}
		
		@Override
		public void onQuestCardDraw(Player p) {
			Story topCard = sDiscard.get(sDiscard.size() - 1);
			QuestHandler questHandler = new QuestHandler((Quest)topCard, players, p, aDeck, aDiscard);
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
			System.out.println("Event: " + topCard.getName());
		}
		
		@Override
		public void onTournamentCardDraw(Player p) {
			System.out.println("test tourn draw");
			view.update(null, players, sDeck, sDiscard, aDiscard);
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
	}
}