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
    private int originalPartNum;
    private int numAsked;  //number of times we've asked participants to join
    private int tiePlayed; //number of times tie participant has played
    private static TournamentHandler instance;
    private ArrayList<Player> tieParticipants;
    
    public TournamentHandler(Tournament t, PlayGame game, Player p) {
        tournament = t;
        players = game.getPlayers();
        player = p;
        deck = game.getADeck();
        discard = game.getADiscard();
        pg = game;
        instance = this;
        numAsked = 0;
        tiePlayed = 0;
        tieParticipants = new ArrayList<Player>();
    }
    
    public static TournamentHandler getInstance() {
		return instance;
	}
    
    public boolean playTournament() throws Exception {
    	if(pg.getTie() == true) {
    		playCards(tieParticipants.get(0));
    		tieRotate();
    		tiePlayed += 1;
    	}
		else {
			askToJoin();
		}
        originalPartNum = tournament.getParticipants().size();
        return true;
    }
    
    public void onEnd() {
    	if(pg.getTie()) { // if a tournament is tied, you must rotate to the next tied participant
			if(getTiePlayed() >= getTieParticipants().size()) { // if all the tied players have played, calculate the winner, end the tournament
    			getWinner(getTieParticipants(), true);
    			pg.setTournament(false);
    		}
			else {
				while(!(players.getPlayers().get(0).getName().equals(getTieParticipants().get(0).getName()))){
					pg.getView().rotate(PlayGame.getInstance());
				}
				//doTurn(players.getPlayers().get(0));
			}
		}
		else if (getNumAsked() >= players.getPlayers().size()){ //if all players have been asked to join, determine winner/is tied before rotating
    		getWinner(getParticipants(), false);
    		if(pg.getTie()) {
    			while(!(pg.getPlayers().getPlayers().get(0).getName().equals(getTieParticipants().get(0).getName()))){
    				pg.getView().rotate(PlayGame.getInstance());
				}
    		}
    	}
		else {
			System.out.println("NORMAL TOURNAMENT ROTATE");
			pg.getView().rotate(PlayGame.getInstance());
		}
    	
    }
    
    public int getNumAsked() {
    	return numAsked;
    }
    
    public int getTiePlayed() {
    	return tiePlayed;
    }
    
    public ArrayList<Player> getTieParticipants(){
    	return tieParticipants;
    }
    
    public void setTieParticipants(ArrayList<Player> p){
    	//if(!tieParticipants.isEmpty())
    	//	tieParticipants.clear();
    	tieParticipants = p;
    }
    
    public void tieRotate() {
    	ArrayList<Player> rotate = tieParticipants;
    	rotate.add(rotate.get(0));
    	rotate.remove(0);
    	setTieParticipants(rotate);
    	System.out.println("TEST TIE ROTATE, CURRENT PLAYER SHOULD BE: " + tieParticipants.get(0).getName());
    	System.out.println("TEST TIE ROTATE NEXT IS: " + tieParticipants.get(tieParticipants.size()-1).getName());
    }
    
    public ArrayList<Player> getParticipants() {
    	return tournament.getParticipants(); 
    }
    
    public Player askToJoin() {
    	ask(0);
        return null;
    }
    
    public void ask(int i){ 
    	numAsked += 1;
    	System.out.println("test ask " + numAsked);
        boolean join = this.pg.getView().prompt("Tournament"); 
        if(join) {
            System.out.println(players.getPlayers().get(i).getName() + " joins the tournament");
            players.getPlayers().get(i).drawCard(1, deck);
            tournament.addParticipant(players.getPlayers().get(i));
            playCards(players.getPlayers().get(i));
        }
        else {
            System.out.println(players.getPlayers().get(i).getName() + " does not join the tournament");
        }
    }
    
    public void playCards(Player p) {
        p.setAllyBp(p.getAllyBp());
        p.setHandState(CardStates.FACE_UP);
        pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), null);
       // pg.getView().playPrompt(p.getName(), p, new ArrayList<Adventure>());
        if(pg.getTie() == true) {
    		pg.getView().tiePlay();
    	}
        else {
        	pg.getView().playCards();
        }
        
    }
    
    public void cleanupWeaponsAndAmour(Player p) {
        System.out.println("TEST CLEANUP");
        for(Iterator<Weapon> weaponsIterator = p.getWeapons().iterator(); weaponsIterator.hasNext();) {
            Weapon w = weaponsIterator.next();
            weaponsIterator.remove();
            discard.add(w);
        }
        
        for(Iterator<Ally> allyIterator = p.getAllies().iterator(); allyIterator.hasNext();) {
        	//Allies are not discarded during a tournament, explained in game rules.
            Ally a = allyIterator.next();
            a.setState(CardStates.FACE_UP);
        }
        
        for(Iterator<Amour> amourIterator = p.getAmour().iterator(); amourIterator.hasNext();) {
            Amour a = amourIterator.next();
            amourIterator.remove();
            discard.add(a);
        }
    }
    
    public void cleanupWeapons(Player p) {
    	for(Iterator<Weapon> weaponsIterator = p.getWeapons().iterator(); weaponsIterator.hasNext();) {
            Weapon w = weaponsIterator.next();
            weaponsIterator.remove();
            discard.add(w);
        }
        
        for(Iterator<Ally> allyIterator = p.getAllies().iterator(); allyIterator.hasNext();) {
        	//Allies are not discarded during a tournament, explained in game rules.
            Ally a = allyIterator.next();
            a.setState(CardStates.FACE_UP);
        }
        
        for(Iterator<Amour> amourIterator = p.getAmour().iterator(); amourIterator.hasNext();) {
        	//When tie happens, amours are not discarded.
            Amour a = amourIterator.next();
            a.setState(CardStates.FACE_UP);
        }
    }
    
    public void tie(ArrayList<Player> tied) {
        System.out.println("TEST TIE");
        for (Player p : tied) {
        	// On tie, only the weapons are discarded.
            cleanupWeapons(p);
        }
        setTieParticipants(tied);
        pg.setTie(true);
    }
    
    public void getWinner(ArrayList<Player> part, boolean tiebreaker) {
        int max = -1;
        Player maxP = null;
        boolean isTie = false;
        ArrayList<Player> tied = new ArrayList<Player>();
        System.out.println("TEST GET WINNERS");
        if(part.size() == 0) {
            System.out.println("nobody joined the tournament");
        }
        else {
        	int s = part.size();
            for(int i = 0; i < s; i++) {
                if(max == -1) {
                    part.get(0).setAllyBp(part.get(0).getAllyBp());
                    max = part.get(0).getBattlePoints() - part.get(0).getABP();
                    maxP = part.get(0);
                    tied.add(maxP);
                   // tieParticipants.add(maxP);
                    System.out.println("tie participant added: " + tied.get(tied.size()-1).getName());
                    
                }   
                else if(part.get(i).getBattlePoints() - part.get(i).getABP() > max) {
                    cleanupWeaponsAndAmour(maxP);
                    max = part.get(i).getBattlePoints() - part.get(i).getABP();
                    maxP = part.get(i);
                    tied.clear();
                    tied.add(maxP);
                   // tieParticipants.clear();
                   // tieParticipants.add(maxP);
                    System.out.println("FIRST tie participant added: " + tied.get(tied.size()-1).getName());
                    isTie = false;
                    pg.setTie(false);
                }
                else if(part.get(i).getBattlePoints() - part.get(i).getABP() == max) {
                    System.out.println("TEST TIE DETECT");
                    tied.add(part.get(i));
                   // tieParticipants.add(part.get(i));
                    System.out.println("tie participant added: " + tied.get(tied.size()-1).getName());
                    isTie = true;
                    pg.setTie(true);
                    
                }
                else {
                    cleanupWeaponsAndAmour(part.get(i));
                }
                
            }
            
            if(!isTie) {
                announceWinner(maxP);
            }
            else {
                if(tiebreaker) {
                    System.out.println("TEST DOUBLE TIE");
                    announceTieWinner(tied);
                }
                else {
                    tie(tied);
                }
            }
        }
    }
    
    public void announceWinner(Player p) {
        System.out.println(p.getName() + " wins the tournament with " + (p.getBattlePoints() - p.getABP()) + " battlepoints!" );
        System.out.println(tournament.getBonus());
        System.out.println(tournament.getParticipants().size());
        System.out.println(p.getName() + " shields increased by " + (tournament.getParticipants().size() + tournament.getBonus()));
        p.setShields(p.getShields() + tournament.getParticipants().size() + tournament.getBonus());
        cleanupWeaponsAndAmour(p);
        pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), null);
        endTournament(player);
    }
    
    public void announceTieWinner(ArrayList<Player> tie) {
        System.out.println("it's a tie! all players tied with " + (tie.get(0).getBattlePoints() - tie.get(0).getABP()) + " battlepoints!");
        System.out.println("all players awarded " + tournament.getParticipants().size() + " shields!");
        for(Player p : tie) {
            p.setShields(p.getShields() + originalPartNum + tournament.getBonus());
            cleanupWeaponsAndAmour(p);
        }
        pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), null);
        endTournament(player);
    }
    
    public void endTournament(Player p) { //end the tournament by rotating back to the initial player
    	//so that the view rotates back to the player who drew the tournament card for the game to resume to the player on the left.
		while(!pg.getPlayers().getPlayers().get(0).equals(p)) {
			pg.getView().rotate(pg);
		}
		pg.setTournament(false);
    }
    
    
    
}