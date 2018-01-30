import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

import model.AdventureDeck;
import model.Player;
import model.StoryDeck;
import model.Player;
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
		//Player player1 = new Player();
		//Player player2 = new Player();
		
		players.getPlayers().get(0).setDealer(true); // player 1 (index 0) is set to dealer
		
		
		//player1.dealer = true; //player1 is assigned the dealer position
		//player2.dealer = false; //ensure that player2 is not the dealer
		
		System.out.print(players.getPlayers().get(0).getDealer() + "\n");
		System.out.print(players.getPlayers().get(1).getDealer() + "\n");
		
		
		//Create adventureDeck and a copy for testing, also need to test if deck was setup correctly on initialization
		AdventureDeck adventureDeck = new AdventureDeck();
		assertEquals(adventureDeck.size(), 125);
		adventureDeck.shuffle();
		for(int i = 0; i < 10; i++) {
			System.out.println("shuffled: " + adventureDeck.get(i).getClass());
		}
		System.out.println();
		
		//Create storyDeck and a copy for testing, also need to test if deck was setup correctly
		StoryDeck storyDeck = new StoryDeck();
		assertEquals(storyDeck.size(), 28);
		storyDeck.shuffle();
		for(int i = 0; i < 10; i++) {
			System.out.println("shuffled: " + storyDeck.get(i).getClass());
		}
		System.out.println();
		
		
		//players 1 and 2 get squire rank cards
		//player1.drawRank("squire");
		//player2.drawRank("squire");
		for (Person person : players.getPlayers()){
			person.drawRank("squire");
		} 
		
		
		
		//players 1 and 2 draw 12 adventure cards
		/*for(int i = 0; i < 12; i++) {
			player1.drawAdventure(); // player1 draws 12 cards
			player2.drawAdventure(); // player2 draws 12 cards
		}*/
		
		for (Person person : players.getPlayers()){
			person.drawCard(12, "Adventure");
			System.out.println(person.getHand());
		} 
		
		//fail("Not yet implemented");
	}

}