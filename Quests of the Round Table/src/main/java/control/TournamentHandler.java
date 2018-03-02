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

	public void getWinner() {
		Player max = null;
		int maxBP = 0;
		System.out.println("TEST GET WINNERS");
		if(tournament.getParticipants().size() == 0) {
			System.out.println("nobody joined the tournament");
		}
		else {
			for(int i = 0; i < tournament.getParticipants().size(); i++) {
				if(max == null) {
					max = tournament.getParticipants().get(0);
					maxBP = max.getBattlePoints() - max.getABP();
				} 
				else if(tournament.getParticipants().get(i).getBattlePoints() - tournament.getParticipants().get(i).getABP() > max.getBattlePoints() - max.getABP()) {
					max = tournament.getParticipants().get(i);
					maxBP = tournament.getParticipants().get(i).getBattlePoints() - max.getABP();
				}
		
				for(Iterator<Weapon> weaponsIterator = tournament.getParticipants().get(i).getWeapons().iterator(); weaponsIterator.hasNext();) {
					Weapon w = weaponsIterator.next();
					weaponsIterator.remove();
					discard.add(w);
				}
				
				for(Iterator<Ally> allyIterator = tournament.getParticipants().get(i).getAllies().iterator(); allyIterator.hasNext();) {
					Ally a = allyIterator.next();
					a.setState(CardStates.FACE_UP);
				}
		
				for(Iterator<Amour> amourIterator = tournament.getParticipants().get(i).getAmour().iterator(); amourIterator.hasNext();) {
					Amour a = amourIterator.next();
					amourIterator.remove();
					discard.add(a);
				}
				
				
			}
			if(max != null) {
				System.out.println(max.getName() + " wins the tournament with " + maxBP + " battlepoints!" );
				System.out.println(tournament.getBonus());
				System.out.println(tournament.getParticipants().size());
				System.out.println(max.getName() + " shields increased by " + (tournament.getParticipants().size() + tournament.getBonus()));
				max.setShields(max.getShields() + tournament.getParticipants().size() + tournament.getBonus());
			}
			
			pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), null);
		}

	}

}
