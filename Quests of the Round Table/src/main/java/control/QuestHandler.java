package control;

import java.util.ArrayList;

import model.AdventureDeck;
import model.AdventureDiscard;
import model.Player;
import model.Player.Person;
import model.Quest;

public class QuestHandler {
	
	private Quest card;
	private AdventureDeck deck;
	private AdventureDiscard discard;
	private Player players;
	
	public QuestHandler(Quest c, Player p, AdventureDeck d, AdventureDiscard di) {
		card = c;
		deck = d;
		discard = di;
		players = p;
	}

	public boolean playQuest() {
		//Ask for the sponsor and keep track of them.
		Person sponsor = askForSponsor();
		if(sponsor == null) {
			return true; //nobody sponsored so go back
		}
		card.setSponsor(sponsor);
		int sponsorIndex = players.getPlayers().indexOf(sponsor);
		
		//Sponsor sets up stages of the quest, only one Test card can be played.
		
		
		ArrayList<Person> participants = askForParticipants();
		
		return true;
	}
	
	public Person askForSponsor() {
		return null;
	}
	
	public ArrayList<Person> askForParticipants() {
		return null;
	}
}
