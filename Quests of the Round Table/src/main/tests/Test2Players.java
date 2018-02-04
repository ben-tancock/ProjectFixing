import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Card;
import model.Player;
import model.StoryDeck;
import model.StoryDiscard;
import model.Player.Person;
public class Test2Players {

	/**
	 * At the start of a game every player is given 12 Adventure Cards to their hand
	 */
	
	@Test
	public void testGameStart() {
		//create 2 players and assign a player as the dealer
		
		Player players = new Player(); // model contains all the players in it
		players.add();
		players.add();
		System.out.print("test\n");
		
		
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
		
		for (Person person : players.getPlayers()){
			person.drawRank("squire");
		} 
		
		for (Person person : players.getPlayers()){
			person.drawCard(12, adventureDeck);
			person.displayHand();
		} 
		
		assertEquals(0, storyDiscard.size());
		//Player to left of dealer starts by drawing first Story card
		players.getPlayers().get(1).drawCard(storyDeck, storyDiscard);
		assertEquals(27, storyDeck.size());
		assertEquals(1, storyDiscard.size());
		//Drawn card event is handled and something happens.
		
		//fail("Not yet implemented");
	}

}