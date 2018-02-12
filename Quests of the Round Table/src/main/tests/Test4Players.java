import static org.junit.Assert.*;

import org.junit.Test;

import model.Player;
import model.Player.Person;

public class Test4Players {

	@Test
	public void testScenario1() {
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
		
		assertEquals(players.getPlayers().size(), 4);
				
		//check
		assertFalse(player1.getDealer());
		assertFalse(player2.getDealer());
		assertFalse(player3.getDealer());
		assertTrue(player4.getDealer());
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
				
		assertEquals(players.getPlayers().size(), 4);
						
		//check
		assertFalse(player1.getDealer());
		assertFalse(player2.getDealer());
		assertFalse(player3.getDealer());
		assertTrue(player4.getDealer());
	}

}
