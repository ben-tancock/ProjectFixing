package control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Foe;
import model.Player;
import model.Players;
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
	
	public PlayGame() {
		aDeck = new AdventureDeck();
		aDiscard = new AdventureDiscard();
		sDeck = new StoryDeck();
		sDiscard = new StoryDiscard();
		players = new Players();
	}
	
	public static void main(String[] args) {
		//logger.info("Game Menu starting!");
		Application.launch(args);
		
	}

	@Override
	public void start(Stage arg0) throws Exception {
		aDeck.shuffle();
		sDeck.shuffle();
		//logger.info("Shuffled the decks.");
		View view = new View();
		view.start(arg0);
		//logger.info("Started the view.");
		view.twoPlayerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				//logger.info("Started the 2 player game.");
				for(int i = 0; i < 2; i++) {
					players.addHuman();
				}
				for(Player p : players.getPlayers()) {
					p.drawCard(12, aDeck);
				}
				view.update(arg0, players, sDeck, sDiscard);
				
			}
			
		});
		
		//ViewController viewController = new ViewController();
		//viewController.start(arg0);
	}
	
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
		public void onAdventureCardDraw(Player p) {
			System.out.println(p.getName() + " has drawn a card.");
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
	}
}