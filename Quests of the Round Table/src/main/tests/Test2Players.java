import static org.junit.Assert.*;
import control.PlayGame.PlayGameControlHandler;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;

import org.junit.Rule;
import org.junit.Test;

import control.PlayGame;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Card;
import model.Player;
import model.Players;
import model.StoryDeck;
import model.StoryDiscard;
public class Test2Players {

	/**
	 * At the start of a game every player is given 12 Adventure Cards to their hand
	 */
	
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	@Test
	public void testGameStart() {
		//create 2 players and assign a player as the dealer
		
		Players players = new Players(); // model contains all the players in it
		players.addHuman();
		players.addHuman();
		System.out.print("test\n");
		
		players.addListener(new PlayGameControlHandler());
		players.getPlayers().get(0).setDealer(true); // player 1 (index 0) is set to dealer
		
		//Check if dealer has been properly assigned
		assertTrue(players.getPlayers().get(0).getDealer());
		assertFalse(players.getPlayers().get(1).getDealer());
		
		//Create adventureDeck and a copy for testing, also need to test if deck was setup correctly on initialization
		AdventureDeck adventureDeck = new AdventureDeck();
		AdventureDiscard adventureDiscard = new AdventureDiscard();
		assertEquals(adventureDeck.size(), 125);
		adventureDeck.shuffle();
		
		for(int i = 0; i < 10; i++) {
			System.out.println("shuffled: " + adventureDeck.get(i).getClass());
		}
		System.out.println();
		
		//Create storyDeck and a copy for testing, also need to test if deck was setup correctly
		StoryDeck storyDeck = new StoryDeck();
		StoryDiscard storyDiscard = new StoryDiscard();
		assertEquals(storyDeck.size(), 28);
		storyDeck.shuffle();
		for(int i = 0; i < 10; i++) {
			System.out.println("shuffled: " + storyDeck.get(i).getClass());
		}
		System.out.println();
		
		for (Player person : players.getPlayers()){
			person.drawRank("squire");
		} 
		
		for (Player person : players.getPlayers()){
			person.drawCard(12, adventureDeck);
			person.displayHand();
		} 
		//Application.launch("");
		/*final CountDownLatch latch = new CountDownLatch(1);
		SwingUtilities.invokeLater(new Runnable() {
		    public void run() {
		        new JFXPanel(); // initializes JavaFX environment
		        latch.countDown();
		    }
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		PlayGame playGame = new PlayGame(players, adventureDeck, adventureDiscard, storyDeck, storyDiscard);
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(),playGame.getSDiscard(), null);
		playGame.doTurn(playGame.getPlayers().getPlayers().get(0));
		assertEquals(0, storyDiscard.size());
		//Player to left of dealer starts by drawing first Story card
		playGame.getPlayers().getPlayers().get(1).drawCard(playGame.getSDeck(), playGame.getSDiscard());
		assertEquals(27, playGame.getSDeck().size());
		assertEquals(1, playGame.getSDiscard().size());
		//Drawn card event is handled and something happens.
		
		//fail("Not yet implemented");
	}

}