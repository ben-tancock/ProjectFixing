import static org.junit.Assert.*;

import org.junit.Test;

import model.AdventureDeck;
import model.StoryDeck;
import model.Player;
public class Test3Players {

	@Test
	public void test() {
		//create 2 players and assign a player as the dealer
		Player player1 = new Player();
		Player player2 = new Player();
		Player player3 = new Player();
		
		player1.dealer = true; //player1 is assigned the dealer position
		player2.dealer = false; //ensure that player2 is not the dealer
		player3.dealer = false; //ensure that player3 is not the dealer
		//check
		assertTrue(player1.dealer);
		assertFalse(player2.dealer);
		assertFalse(player3.dealer);
		
		//Create adventureDeck and a copy for testing, also need to test if deck was setup correctly on initialization
		AdventureDeck adventureDeck = new AdventureDeck();
		AdventureDeck oldAdventureDeck = adventureDeck;
		assertEquals(adventureDeck.size(), 125);
		adventureDeck.shuffle();
		assertNotEquals(adventureDeck, oldAdventureDeck);
		
		//Create storyDeck and a copy for testing, also need to test if deck was setup correctly
		StoryDeck storyDeck = new StoryDeck();
		StoryDeck oldStoryDeck = storyDeck;
		assertEquals(storyDeck.size(), 28);
		storyDeck.shuffle();
		assertNotEquals(storyDeck, oldStoryDeck);
		
		
		fail("Not yet implemented");
	}

}
