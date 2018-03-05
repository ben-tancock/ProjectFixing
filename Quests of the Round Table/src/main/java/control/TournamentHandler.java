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
        originalPartNum = tournament.getParticipants().size();
        System.out.println("original participant num: " + originalPartNum);
        getWinner(tournament.getParticipants(), false);
        while(pg.getPlayers().getPlayers().get(0).getName() != player.getName()) {
            pg.getView().rotate(pg);
        }
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
        p.setHandState(CardStates.FACE_UP);
        pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), null);
        pg.getView().playPrompt(p.getName(), p, new ArrayList<Adventure>());
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
            while(pg.getPlayers().getPlayers().get(0).getName() != p.getName()) {
                pg.getView().rotate(pg);
            }
            pg.doTurn(players.getPlayers().get(0));
            playCards(p);
        }
        
        getWinner(tied, true);
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
            for(int i = 0; i < part.size(); i++) {
                if(max == -1) {
                    part.get(0).setAllyBp(part.get(0).getAllyBp());
                    max = part.get(0).getBattlePoints() - part.get(0).getABP();
                    maxP = part.get(0);
                    tied.add(maxP);
                    
                }   
                else if(part.get(i).getBattlePoints() - part.get(i).getABP() > max) {
                    cleanupWeaponsAndAmour(maxP);
                    max = part.get(i).getBattlePoints() - part.get(i).getABP();
                    maxP = part.get(i);
                    tied.clear();
                    tied.add(maxP);
                    isTie = false;
                }
                else if(part.get(i).getBattlePoints() - part.get(i).getABP() == max) {
                    System.out.println("TEST TIE DETECT");
                    tied.add(part.get(i));
                    isTie = true;
                    
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
    }
    
    public void announceTieWinner(ArrayList<Player> tie) {
        System.out.println("it's a tie! all players tied with " + (tie.get(0).getBattlePoints() - tie.get(0).getABP()) + " battlepoints!");
        System.out.println("all players awarded " + tournament.getParticipants().size() + " shields!");
        for(Player p : tie) {
            p.setShields(p.getShields() + originalPartNum + tournament.getBonus());
            cleanupWeaponsAndAmour(p);
        }
        pg.getView().update(null, players, pg.getSDeck(), pg.getSDiscard(), null);
    }
}