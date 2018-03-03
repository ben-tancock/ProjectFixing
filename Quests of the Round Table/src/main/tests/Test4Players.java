import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;

import model.AdventureDeck;
import model.AdventureDiscard;
import model.CardStates;
import model.Foe;
import model.Player;
import model.Players;
import model.Stage;
import model.StoryDeck;
import model.StoryDiscard;
import model.Weapon;
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
		
		players.getPlayers().get(3).setDealer(true);
		
		Player player1 = players.getPlayers().get(0);
		player1.setName("Player 1");
		Player player2 = players.getPlayers().get(1);
		player2.setName("Player 2");
		Player player3 = players.getPlayers().get(2);
		player3.setName("Player 3");
		Player player4 = players.getPlayers().get(3);
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
		assertEquals(aDeck.size(), 77);
		assertEquals(aDiscard.size(), 0);
		
		PlayGame playGame = new PlayGame(players, aDeck, aDiscard, sDeck, sDiscard);
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(),playGame.getSDiscard(), null);
		playGame.doTurn(players.getPlayers().get(0));
		player1.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "boar_hunt");
		for(Player p : players.getPlayers()) { //this is handled in PlayGame after a story card is played all the way through, so we put it here to catch it.
			p.setHandState(CardStates.FACE_DOWN);
		}
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(), playGame.getSDiscard(), null);
		assertEquals(playGame.getSDeck().size(), 27);
		assertEquals(playGame.getSDiscard().size(), 1);
		playGame.getView().rotate(playGame);
		playGame.doTurn(players.getPlayers().get(0));
		player2.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "prosperity_throughout_the_realm");
		for(Player p : players.getPlayers()) {
			p.setHandState(CardStates.FACE_DOWN);
		}
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(), playGame.getSDiscard(), null);
		assertEquals(playGame.getSDeck().size(), 26);
		assertEquals(playGame.getSDiscard().size(), 2);
		playGame.getView().rotate(playGame);
		playGame.doTurn(players.getPlayers().get(0));
		player3.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "chivalrous_deed");
		for(Player p : players.getPlayers()) {
			p.setHandState(CardStates.FACE_DOWN);
		}
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(), playGame.getSDiscard(), null);
		assertEquals(playGame.getSDeck().size(), 25);
		assertEquals(playGame.getSDiscard().size(), 3);
		playGame.getView().rotate(playGame);
		playGame.doTurn(players.getPlayers().get(0));
		player4.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "journey_through_the_enchanted_forest");
		for(Player p : players.getPlayers()) {
			p.setHandState(CardStates.FACE_DOWN);
		}
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(), playGame.getSDiscard(), null);
		assertEquals(playGame.getSDeck().size(), 24);
		assertEquals(playGame.getSDiscard().size(), 4);
	}
	
	@Test
	public void testScenario3() throws Exception {
		//Create 4 players and assign the 4th player as the dealer.
		Players players = new Players();
		players.addListener(new PlayGameControlHandler());
						
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
		player1.drawCard(aDeck, "sir_gawain");
		player1.drawCard(aDeck, "boar");
		player1.drawCard(aDeck, "sword");
		player1.drawCard(aDeck, "sword");
		player1.drawCard(aDeck, "sword");
		player1.drawCard(aDeck, "thieves");
		player1.drawCard(aDeck, "robber_knight");
		player1.drawCard(aDeck, "saxon_knight");
		player1.drawCard(aDeck, "dagger");
		player1.drawCard(aDeck, "test_of_the_questing_beast");
		player2.drawCard(aDeck, "thieves");
		player2.drawCard(aDeck, "saxons");
		player2.drawCard(aDeck, "saxon_knight");
		player2.drawCard(aDeck, "green_knight");
		player2.drawCard(aDeck, "sir_percival");
		player2.drawCard(aDeck, "amour");
		player3.drawCard(aDeck, "lance");
		player3.drawCard(aDeck, "battle-ax");
		player3.drawCard(aDeck, "amour");
		player3.drawCard(aDeck, "sword");
		
		player1.drawCard(12 - player1.getHand().size(), aDeck);
		player2.drawCard(12 - player2.getHand().size(), aDeck);
		player3.drawCard(12 - player3.getHand().size(), aDeck);
		player4.drawCard(12 - player4.getHand().size(), aDeck);
		
		//make sure card overflows dealt with properly
		assertEquals(player1.getHand().size(), 12);
		assertEquals(player2.getHand().size(), 12);
		assertEquals(player3.getHand().size(), 12);
		assertEquals(player4.getHand().size(), 12);
		assertEquals(aDeck.size(), 77);
		assertEquals(aDiscard.size(), 0);
		
		PlayGame playGame = new PlayGame(players, aDeck, aDiscard, sDeck, sDiscard);
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(),playGame.getSDiscard(), null);
		playGame.doTurn(players.getPlayers().get(0));
		player1.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "tintagel");
		for(Player p : players.getPlayers()) { //this is handled in PlayGame after a story card is played all the way through, so we put it here to catch it.
			p.setHandState(CardStates.FACE_DOWN);
		}
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(), playGame.getSDiscard(), null);
		assertEquals(playGame.getSDeck().size(), 27);
		assertEquals(playGame.getSDiscard().size(), 1);
		playGame.getView().rotate(playGame);
		playGame.doTurn(players.getPlayers().get(0));
		player2.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "plague");
		for(Player p : players.getPlayers()) {
			p.setHandState(CardStates.FACE_DOWN);
		}
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(), playGame.getSDiscard(), null);
		assertEquals(playGame.getSDeck().size(), 26);
		assertEquals(playGame.getSDiscard().size(), 2);
		playGame.getView().rotate(playGame);
		playGame.doTurn(players.getPlayers().get(0));
		player3.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "pox");
		for(Player p : players.getPlayers()) {
			p.setHandState(CardStates.FACE_DOWN);
		}
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(), playGame.getSDiscard(), null);
		assertEquals(playGame.getSDeck().size(), 25);
		assertEquals(playGame.getSDiscard().size(), 3);
		playGame.getView().rotate(playGame);
		playGame.doTurn(players.getPlayers().get(0));
		player4.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "queen's_favor");
		for(Player p : players.getPlayers()) {
			p.setHandState(CardStates.FACE_DOWN);
		}
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(), playGame.getSDiscard(), null);
		assertEquals(playGame.getSDeck().size(), 24);
		assertEquals(playGame.getSDiscard().size(), 4);
		playGame.getView().rotate(playGame);
		playGame.doTurn(players.getPlayers().get(0));
		player1.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "search_for_the_holy_grail");
		for(Player p : players.getPlayers()) {
			p.setHandState(CardStates.FACE_DOWN);
		}
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(), playGame.getSDiscard(), null);
		assertEquals(playGame.getSDeck().size(), 23);
		assertEquals(playGame.getSDiscard().size(), 5);
		playGame.getView().rotate(playGame);
		playGame.doTurn(players.getPlayers().get(0));
		player2.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "test_of_the_green_knight");
		for(Player p : players.getPlayers()) {
			p.setHandState(CardStates.FACE_DOWN);
		}
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(), playGame.getSDiscard(), null);
		assertEquals(playGame.getSDeck().size(), 22);
		assertEquals(playGame.getSDiscard().size(), 6);
		playGame.getView().rotate(playGame);
		playGame.doTurn(players.getPlayers().get(0));
		player3.drawCard(playGame.getSDeck(), playGame.getSDiscard(), "court_called_to_camelot");
		for(Player p : players.getPlayers()) {
			p.setHandState(CardStates.FACE_DOWN);
		}
		playGame.getView().update(null, playGame.getPlayers(), playGame.getSDeck(), playGame.getSDiscard(), null);
		assertEquals(playGame.getSDeck().size(), 21);
		assertEquals(playGame.getSDiscard().size(), 7);
		playGame.getView().rotate(playGame);
		playGame.doTurn(players.getPlayers().get(0));
	}

}