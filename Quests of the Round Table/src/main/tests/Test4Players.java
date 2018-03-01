import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;

import model.AdventureDeck;
import model.AdventureDiscard;
import model.CardStates;
import model.Player;
import model.Players;
import model.StoryDeck;
import model.StoryDiscard;
import control.PlayGame;
import control.QuestHandler;
import control.PlayGame.PlayGameControlHandler;

public class Test4Players {

	
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	@Test
	public void testScenario1() throws Exception {
		//Create 4 players and assign the 4th player as the dealer.
		Players players = new Players();
		players.addListener(new PlayGameControlHandler());
				
		for(int i = 0; i < 4; i++) {
			players.addHuman();
		}
		
		players.getPlayers().get(1).setDealer(true);
		
		Player player1 = players.getPlayers().get(0);
		player1.setName("Player 1");
		Player player2 = players.getPlayers().get(3);
		player2.setName("Player 2");
		Player player3 = players.getPlayers().get(2);
		player3.setName("Player 3");
		Player player4 = players.getPlayers().get(1);
		player4.setName("Player 4");
		
		//Check for all players, check names.
		assertEquals(players.getPlayers().size(), 4);
		assertEquals(player1.getName(), "Player 1");
		assertEquals(player2.getName(), "Player 2");
		assertEquals(player3.getName(), "Player 3");
		assertEquals(player4.getName(), "Player 4");
		
		for(Player p : players.getPlayers()) {
			p.drawRank("squire");
			p.setShields(1);
		}
				
		//Check dealer.
		assertFalse(player1.getDealer());
		assertFalse(player2.getDealer());
		assertFalse(player3.getDealer());
		assertTrue(player4.getDealer());
		
		//Players are dealt cards and Adventure and Story Decks are setup.
		AdventureDeck aDeck = new AdventureDeck();
		aDeck.shuffle();
		AdventureDiscard aDiscard = new AdventureDiscard();
		StoryDeck sDeck = new StoryDeck();
		sDeck.shuffle();
		StoryDiscard sDiscard = new StoryDiscard();
		player1.drawCard(aDeck, "saxons");
		player1.drawCard(aDeck, "boar");
		player1.drawCard(aDeck, "dagger");
		player1.drawCard(aDeck, "sword");
		player3.drawCard(aDeck, "horse");
		player3.drawCard(aDeck, "excalibur");
		player4.drawCard(aDeck, "battle-ax");
		player4.drawCard(aDeck, "lance");
		player1.drawCard(12 - player1.getHand().size(), aDeck);
		player2.drawCard(12, aDeck);
		player3.drawCard(12 - player3.getHand().size(), aDeck);
		player4.drawCard(12 - player4.getHand().size(), aDeck);
		
		//make sure card overflows dealt with properly
		assertEquals(player1.getHand().size(), 12);
		assertEquals(player2.getHand().size(), 12);
		assertEquals(player3.getHand().size(), 12);
		assertEquals(player4.getHand().size(), 12);
		
		PlayGame playGame = new PlayGame(players, aDeck, aDiscard, sDeck, sDiscard);
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(),playGame.getSDiscard(), null);
		boolean seeCards = playGame.getView().seeCardPrompt(player1);
		if(seeCards) {
			player1.setHandState(CardStates.FACE_UP);
			playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(), playGame.getSDiscard(), null);
		}
		player1.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "boar_hunt");
		
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(), playGame.getSDiscard(), null);
		QuestHandler qh = QuestHandler.getInstance();
		if(qh != null && qh.getCard() != null) {
			playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(), playGame.getSDiscard(), qh.getCard());
		}
		
		player2.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "prosperity_throughout_the_realm");
		player3.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "chivalrous_deed");
		player4.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "journey_through_the_enchanted_forest");
	}
	
	@Test
	public void testScenario2() {
		//create 4 players and assign a player as the dealer
				Players players = new Players();
						
				for(int i = 0; i < 4; i++) {
					players.addHuman();
				}
						
				players.getPlayers().get(3).setDealer(true);
				
				Player player1 = players.getPlayers().get(0);
				player1.setName("Player 1");
				Player player2 = players.getPlayers().get(1);
				player2.setName("Player 2");
				Player player3 = players.getPlayers().get(2);
				player3.setName("Player 3");
				Player player4 = players.getPlayers().get(3);
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
				
				//Players are dealt cards and Adventure and Story Decks are setup.
				AdventureDeck aDeck = new AdventureDeck();
				aDeck.shuffle();
				AdventureDiscard aDiscard = new AdventureDiscard();
				StoryDeck sDeck = new StoryDeck();
				sDeck.shuffle();
				StoryDiscard sDiscard = new StoryDiscard();
				//test overflow error
				player1.drawCard(12, aDeck);
				player2.drawCard(12, aDeck);
				player3.drawCard(12, aDeck);
				player4.drawCard(12, aDeck);
				
				PlayGame playGame = new PlayGame(players, aDeck, aDiscard, sDeck, sDiscard);
				playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(),playGame.getSDiscard(), null);
	}

}
