import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import model.AdventureDeck;
import model.AdventureDiscard;
import model.Foe;
import model.StoryDeck;
import model.StoryDiscard;
import model.Weapon;
import model.Player;
import model.Player.Person;
import model.Quest;
import model.Stage;
public class Test3Players {

	@Test
	public void test() throws Exception {
		//create 3 players and assign a player as the dealer
		Player players = new Player();
		
		for(int i = 0; i < 3; i++) {
			players.add();
		}
		
		players.getPlayers().get(0).setDealer(true);
		
		Person playerA = players.getPlayers().get(0);
		playerA.setName("Player A");
		Person playerB = players.getPlayers().get(1);
		playerB.setName("Player B");
		Person playerC = players.getPlayers().get(2);
		playerC.setName("Player C");
		
		assertEquals(players.getPlayers().size(), 3);
		
		//check
		assertTrue(playerA.getDealer());
		assertFalse(playerB.getDealer());
		assertFalse(playerC.getDealer());
		
		//Create adventureDeck and a copy for testing, also need to test if deck was setup correctly on initialization
		AdventureDeck adventureDeck = new AdventureDeck();
		AdventureDiscard adventureDiscard = new AdventureDiscard();
		assertEquals(adventureDeck.size(), 125);
		adventureDeck.shuffle();
		
		//Create storyDeck and a copy for testing, also need to test if deck was setup correctly
		StoryDeck storyDeck = new StoryDeck();
		StoryDiscard storyDiscard = new StoryDiscard();
		assertEquals(storyDeck.size(), 28);
		storyDeck.shuffle();
		
		//We want to hand players specific cards to make the game play out in a specific way for testing.
		
		//Player B gets Mordred, Sir Tristan, and Queen Iseult... and all the rest of the allies
		playerB.drawCard(adventureDeck, "sir_tristan");
		playerB.drawCard(adventureDeck, "queen_iseult");
		playerB.drawCard(adventureDeck, "king_arthur");
		playerB.drawCard(adventureDeck, "king_pellinore");
		playerB.drawCard(adventureDeck, "merlin");
		playerB.drawCard(adventureDeck, "sir_galahad");
		playerB.drawCard(adventureDeck, "sir_gawain");
		playerB.drawCard(adventureDeck, "sir_lancelot");
		playerB.drawCard(adventureDeck, "sir_percival");
		playerB.drawCard(adventureDeck, "amour");
		playerB.drawCard(adventureDeck, "mordred");
		
		//Player A gets Queen Guinevere
		playerA.drawCard(adventureDeck, "queen_guinevere");
		playerA.drawCard(adventureDeck, "sword");
		
		//Player C gets Boar, Dagger, Test of Morgan Le Fey, Black Knight
		playerC.drawCard(adventureDeck, "dagger");
		playerC.drawCard(adventureDeck, "boar");
		playerC.drawCard(adventureDeck, "test_of_morgan_le_fey");
		playerC.drawCard(adventureDeck, "black_knight");
		
		//Players draw rest of cards and we check counts.
		playerA.drawCard(10, adventureDeck);
		playerB.drawCard(1, adventureDeck);
		playerC.drawCard(8, adventureDeck);
		
		assertEquals(playerA.getHand().size(), 12);
		assertEquals(playerB.getHand().size(), 12);
		assertEquals(playerC.getHand().size(), 12);
		
		for(Person p : players.getPlayers()) {
			System.out.println(p.getName());
			p.displayHand();
		}
		
		//it becomes Player B's turn(left of dealer), Player B draws King's Recognition.
		playerB.drawCard(storyDeck, storyDiscard, "king's_recognition");
		assertEquals(storyDiscard.get(0).getName(), "king's_recognition");
		
		//make sure event is triggered so that the event is properly handled.
		
		//it becomes Player C's turn(left of Player B), Player C draws Rescue The Fair Maiden.
		playerC.drawCard(storyDeck, storyDiscard, "rescue_the_fair_maiden");
		assertEquals(storyDiscard.get(1).getName(), "rescue_the_fair_maiden");
		
		//Player C decides to sponsor the quest.
		//storyDiscard.get(1).setSponsor(playerC);
		
		//Player C then sets up stages 1, 2, and 3.
		//Stage 1
		ArrayList<Weapon> stage1Weapons = new ArrayList<>();
		stage1Weapons.add((Weapon)playerC.playCard(playerC.getCard(0), false));
		((Quest)storyDiscard.get(1)).addStage(new Stage((Foe)playerC.playCard(playerC.getCard(0), false), stage1Weapons));
		//Stage 2
		((Quest)storyDiscard.get(1)).addStage(new Stage((model.Test)playerC.playCard(playerC.getCard(0), false)));
		//Stage 3
		((Quest)storyDiscard.get(1)).addStage(new Stage((Foe)playerC.playCard(playerC.getCard(0), false), new ArrayList<Weapon>()));
		System.out.println();
		System.out.println("Player C's hand: ");
		playerC.displayHand();
		assertEquals(playerC.getHand().size(), 8);
		//display the stages.
		System.out.println();
		System.out.println(storyDiscard.get(1).toString());
		
		//Player C announces that the first stage is a Foe
		
		//Player A and Player B both receive adventure cards
		playerA.drawCard(1, adventureDeck);
		playerB.drawCard(1, adventureDeck);
		playerA.displayHand();
		playerB.displayHand();
		//to check discarding functionality
		playerA.discard(playerA.getCard(12), adventureDiscard, false);
		playerB.discard(playerB.getCard(12), adventureDiscard, false);
		assertEquals(playerA.getHand().size(), 12);
		assertEquals(playerB.getHand().size(), 12);
		
		//Player B decides to play all his allies to defeat the boar
		playerB.playCard(playerB.getCard(0), true);
		playerB.playCard(playerB.getCard(0), true);
		playerB.playCard(playerB.getCard(0), true);
		playerB.playCard(playerB.getCard(0), true);
		playerB.playCard(playerB.getCard(0), true);
		playerB.playCard(playerB.getCard(0), true);
		playerB.playCard(playerB.getCard(0), true);
		playerB.playCard(playerB.getCard(0), true);
		playerB.playCard(playerB.getCard(0), true);
		playerB.playCard(playerB.getCard(0), true);
		
		assertEquals(playerB.getHand().size(), 2);
		assertEquals(playerB.getPlayingSurface().size(), 10);
		
		//Player B decides to play Queen Guinevere and a Sword
		playerA.playCard(playerA.getCard(0), true);
		playerA.playCard(playerA.getCard(0), true);
		
		assertEquals(playerA.getHand().size(), 10);
		assertEquals(playerA.getPlayingSurface().size(), 2);
		System.out.println();
		playerB.displayHand();
		playerB.displaySurface();
		playerA.displayHand();
		playerA.displaySurface();
		
		//both players turn over their cards, they defeat the boar
		//need to get battlepoints and compare
		
		//Player C announces the second stage is a Test
		
		//Players A and B announce their bids
		//Player A announces 3 bids
		//Player B plays mordred to eliminate player A's Queen Guinevere
		//Player B announces 4 bids
		//Player B advances, Player A is no longer in quest.
		//Player B beats the Black Knight and finishes the Fair Maiden Quest being awarded the shields, plus the bonus shields from the event.
	}

}
