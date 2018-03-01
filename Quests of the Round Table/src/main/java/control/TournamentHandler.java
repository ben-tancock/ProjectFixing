package control;

import model.Tournament;
import java.util.ArrayList;

import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Amour;
import model.CardStates;
import model.Person;
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
		getWinner();
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
				if(this.pg.getView().switchPrompt(players.getPlayers().get((currentIndex + 1) % players.getPlayers().size()).getName(), players.getPlayers().get((currentIndex + 1) % players.getPlayers().size()))) {
					//this.pg.focusPlayer(players.getPlayers().get(currentIndex + i));
					players.getPlayers().get((currentIndex + 1) % players.getPlayers().size()).setHandState(CardStates.FACE_UP);
					pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), null);
				}
			}
			
			ask(0);
			currentIndex += 1 % players.getPlayers().size();
		}
		
	
		return null;
	}
	
	public void ask(int i){
		//System.out.println(players.getPlayers().get(i).getName() + " joins the tournament \n");
		
		boolean join = this.pg.getView().prompt("Tournament"); 
		if(join) {
			System.out.println(players.getPlayers().get(i).getName() + " joins the tournament");
			players.getPlayers().get(i).drawCard(1, deck);
			this.tournament.addParticipant(players.getPlayers().get(i));
			players.getPlayers().get(i).setHandState(CardStates.FACE_UP);
			pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), null);
			playCards(players.getPlayers().get(i));
			
		}
		else {
			System.out.println(players.getPlayers().get(i).getName() + " does not join the tournament");
		}
	}
	
	public void playCards(Player p) {
		p.setAllyBp(p.getAllyBp());
		pg.getView().playPrompt(p.getName(), p, new ArrayList<Adventure>());
	}
	
	public void getWinner() {
		Player max = null;
		for(int i = 0; i < tournament.getParticipants().size(); i++) {
			if(max == null) {
				max = tournament.getParticipants().get(0);
			}	
			else if(tournament.getParticipants().get(i).getBattlePoints() - tournament.getParticipants().get(i).getABP() > max.getBattlePoints() - max.getABP()) {
				max = tournament.getParticipants().get(i);
			}
			else {
				//tournament.getParticipants().get(i)
			}
		}
		
		
		System.out.println(max.getName() + " wins the tournament with " + max.getBattlePoints() + " battlepoints!" );
		System.out.println(tournament.getBonus());
		System.out.println(tournament.getParticipants().size());
		System.out.println(max.getName() + " shields increased by " + (tournament.getParticipants().size() + tournament.getBonus()));
		max.setShields(max.getShields() + tournament.getParticipants().size() + tournament.getBonus());
	}

}