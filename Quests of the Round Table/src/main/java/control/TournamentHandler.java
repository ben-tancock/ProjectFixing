package control;

import model.Tournament;
import java.util.ArrayList;
import java.util.Iterator;

import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Ally;
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
		askToJoin();
		getWinner();
		pg.getView().rotate(pg);
		return true;
	}
	
	public Player askToJoin() {
		for(int i = 0; i < players.getPlayers().size(); i++) {	
			if(i > 0) {
				pg.getView().rotate(pg);
				pg.doTurn(players.getPlayers().get(0));
			}	
			ask(0);
		}
		return null;
	}
	
	public void ask(int i){		
		boolean join = this.pg.getView().prompt("Tournament"); 
		if(join) {
			System.out.println(players.getPlayers().get(i).getName() + " joins the tournament");
			players.getPlayers().get(i).drawCard(1, deck);
			this.tournament.addParticipant(players.getPlayers().get(i));
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
	
	public void cleanup(Player p) {
		System.out.println("TEST CLEANUP");
		for(Iterator<Weapon> weaponsIterator = p.getWeapons().iterator(); weaponsIterator.hasNext();) {
			Weapon w = weaponsIterator.next();
			weaponsIterator.remove();
			discard.add(w);
		}
		
		for(Iterator<Ally> allyIterator = p.getAllies().iterator(); allyIterator.hasNext();) {
			Ally a = allyIterator.next();
			allyIterator.remove();
			discard.add(a);
		}
		
		for(Iterator<Amour> amourIterator = p.getAmour().iterator(); amourIterator.hasNext();) {
			Amour a = amourIterator.next();
			amourIterator.remove();
			discard.add(a);
		}
	}
	
	public void tie() {
		
	}
	
	public void getWinner() {
		int max = -1;
		Player maxP = null;
		System.out.println("TEST GET WINNERS");
		if(tournament.getParticipants().size() == 0) {
			System.out.println("nobody joined the tournament");
		}
		else {
			for(int i = 0; i < tournament.getParticipants().size(); i++) {
				if(max == -1) {
					tournament.getParticipants().get(0).setAllyBp(tournament.getParticipants().get(0).getAllyBp());
					max = tournament.getParticipants().get(0).getBattlePoints() - tournament.getParticipants().get(0).getABP();
					maxP = tournament.getParticipants().get(0);
					
				}	
				else if(tournament.getParticipants().get(i).getBattlePoints() - tournament.getParticipants().get(i).getABP() > max) {
					cleanup(maxP);
					max = tournament.getParticipants().get(i).getBattlePoints() - tournament.getParticipants().get(i).getABP();
					maxP = tournament.getParticipants().get(i);
				}
				else {
					cleanup(tournament.getParticipants().get(i));
				}
				
			}
			
			System.out.println(maxP.getName() + " wins the tournament with " + maxP.getBattlePoints() + " battlepoints!" );
			System.out.println(tournament.getBonus());
			System.out.println(tournament.getParticipants().size());
			System.out.println(maxP.getName() + " shields increased by " + (tournament.getParticipants().size() + tournament.getBonus()));
			maxP.setShields(maxP.getShields() + tournament.getParticipants().size() + tournament.getBonus());
			cleanup(maxP);
			pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), null);
		}	
	}
}