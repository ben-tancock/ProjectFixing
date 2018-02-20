import static org.junit.Assert.*;

import org.junit.Test;

import model.AdventureDeck;
import model.AdventureDiscard;
import model.Player;
import model.Player.Person;
import model.StoryDeck;
import model.StoryDiscard;
import control.PlayGame.PlayGameControlHandler;

public class Test4Players {

	@Test
	public void testScenario1() {
		//Create 4 players and assign the 4th player as the dealer.
		Player players = new Player();
		players.addListener(new PlayGameControlHandler());
				
		for(int i = 0; i < 4; i++) {
			players.add();
		}
		
		players.getPlayers().get(3).setDealer(true);
		
		Person player1 = players.getPlayers().get(0);
		player1.setName("Player 1");
		Person player2 = players.getPlayers().get(1);
		player2.setName("Player 2");
		Person player3 = players.getPlayers().get(2);
		player3.setName("Player 3");
		Person player4 = players.getPlayers().get(3);
		player4.setName("Player 4");
		
		//Check for all players, check names.
		assertEquals(players.getPlayers().size(), 4);
		assertEquals(player1.getName(), "Player 1");
		assertEquals(player2.getName(), "Player 2");
		assertEquals(player3.getName(), "Player 3");
		assertEquals(player4.getName(), "Player 4");
				
		//Check dealer.
		assertFalse(player1.getDealer());
		assertFalse(player2.getDealer());
		assertFalse(player3.getDealer());
		assertTrue(player4.getDealer());
		
		//Players are dealt cards and Adventure and Story Decks are setup.
		AdventureDeck ADeck = new AdventureDeck();
		ADeck.shuffle();
		AdventureDiscard aDiscard = new AdventureDiscard();
		StoryDeck SDeck = new StoryDeck();
		SDeck.shuffle();
		StoryDiscard SDiscard = new StoryDiscard();
		//test overflow error
		player1.drawCard(12, ADeck);
		player2.drawCard(12, ADeck);
		player3.drawCard(12, ADeck);
		player4.drawCard(12, ADeck);
		
		
		for(Person p : players.getPlayers()) {
			if(p.getHand().size() > 12) {
				try {
					p.discard(p.getCard(0), aDiscard, false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			System.out.println(p.getName() + "'s hand: " + p.getHand());
			System.out.println(p.getName() + "'s allies: " + p.getAllies());
		}
		
		//make sure card overflows dealt with properly
		assertEquals(player1.getHand().size(), 12);
		assertEquals(player2.getHand().size(), 12);
		assertEquals(player3.getHand().size(), 12);
		assertEquals(player4.getHand().size(), 12);
		
		
		
	}
	
	@Test
	public void testScenario2() {
		//create 4 players and assign a player as the dealer
				Player players = new Player();
						
				for(int i = 0; i < 4; i++) {
					players.add();
				}
						
				players.getPlayers().get(3).setDealer(true);
				
				Person player1 = players.getPlayers().get(0);
				player1.setName("Player 1");
				Person player2 = players.getPlayers().get(1);
				player2.setName("Player 2");
				Person player3 = players.getPlayers().get(2);
				player3.setName("Player 3");
				Person player4 = players.getPlayers().get(3);
				player4.setName("Player 4");
				
				//Check for all players, check names
				assertEquals(players.getPlayers().size(), 4);
				assertEquals(player1.getName(), "Player 1");
				assertEquals(player2.getName(), "Player 2");
				assertEquals(player3.getName(), "Player 3");
				assertEquals(player4.getName(), "Player 4");
						
				//check dealer
				assertFalse(player1.getDealer());
				assertFalse(player2.getDealer());
				assertFalse(player3.getDealer());
				assertTrue(player4.getDealer());
	}

}
