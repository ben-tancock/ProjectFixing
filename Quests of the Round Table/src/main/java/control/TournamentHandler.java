package control;

import model.Tournament;
import java.util.ArrayList;

import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Amour;
import model.CardStates;
import model.Player;
import model.Players;
import model.Quest;
import model.Stage;
import model.Weapon;

public class TournamentHandler {
	
	private Tournament tournament;
	private AdventureDeck deck;
	private AdventureDiscard discard;
	private Players players;
	private Player player;
	private PlayGame pg;
	
	public TournamentHandler(Tournament t, PlayGame game, Player p) {
		tournament = t;
		players = game.getPlayers();
		player = p;
		deck = game.getADeck();
		discard = game.getADiscard();
		pg = game;
	}
	
	public boolean playTournament() throws Exception {
		askToJoin(player);
		
		return true;
	}
	
	public Player askToJoin(Player pr) {
		//pr = player that drew the quest card, so we start there.
		// to do:
		// - ask each player, starting at who drew, to join (prompt: join tourney? (Y/N))
		// 		- tournament.askPlayer(i) 
		// - if Y: Tournament.addParticipant() 
		// 
		int currentIndex = players.getPlayers().indexOf(pr);
		//ask(currentIndex);
		
		for(int i = 0; i < players.getPlayers().size(); i++) {
			
			if(i > 0) {
				pg.getView().rotate(pg);
				if(this.pg.getView().switchPrompt("Participant", players.getPlayers().get((currentIndex + i) % players.getPlayers().size()).getName(), players.getPlayers().get((currentIndex + i) % players.getPlayers().size()))) {
					//this.pg.focusPlayer(players.getPlayers().get(currentIndex + i));
					players.getPlayers().get((currentIndex + 1) % players.getPlayers().size()).setHandState(CardStates.FACE_UP);
					pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), null);
				}
			}
			
			ask(0);
			currentIndex += 1 % players.getPlayers().size();
		}
		
		/*if(!pr.isFocused()) { 
			for(Player p : players.getPlayers()) {
				p.setFocused(false);
			}
			pr.setFocused(true);
		}*/
		//pr.setHandState(CardStates.FACE_UP);
		return null;
	}
	
	public void ask(int i){
		// there'll be a whole lot more stuff here, for now just say yes
		//System.out.println(players.getPlayers().get(i).getName() + " joins the tournament \n");
		
		// TO DO:
		// prompt the player: "join tourney?" (Y/N)
		boolean join = this.pg.getView().prompt("Tournament"); 
		if(join) {
			System.out.println(players.getPlayers().get(i).getName() + " joins the tournament");
			this.tournament.addParticipant(players.getPlayers().get(i));
			players.getPlayers().get(i).setHandState(CardStates.FACE_DOWN);
		}
		else {
			System.out.println(players.getPlayers().get(i).getName() + " does not join the tournament");
		}
		// Yes --> Tournament.addParticipant(player)
		// No --> ?
	}

}
