package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;
import model.Card;
import model.Adventure;
import model.AdventureDeck;

public class Player {
	public static String NAME = "name";
	public static int SHIELDS = 0;
	public static String RANK = "squire";
	public static boolean dealer = false;
	private List<Adventure> HAND = new ArrayList<Adventure>(); 
	private List<Ally> ALLIES = new ArrayList<Ally>();
	private List<Adventure> PLAYING_SURFACE = new ArrayList<Adventure>();
	//AdventureDeck advDeck = new AdventureDeck(); decks kept separate for now
	
	public List<Person> persons = new ArrayList<Person>();
	private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

	public Player() {
		
	}
	
	
	public class Person { // not entirely sure if this should be static...
		
		public Person() {
			
		}
		private String name;
		private String rank;
		private boolean dealer = false;
		private int shields;
		private List<Adventure> hand = new ArrayList<Adventure>();
		private List<Ally> allies = new ArrayList<Ally>();
		private List<Adventure> playingSurface = new ArrayList<Adventure>();
		
		// Getters and Setters --------------------------------
		public String getName() {
			return name;
		}
		
		public String getRank() {
			return rank;
		}
		
		public void setRank(String s) {
			rank = s;
		}
		
		public int getShields() {
			return shields;
		}
		
		public void setHandState(int state) throws Exception {
			for(Adventure card : hand) {
				card.setState(state);
			}
		}
		
		public void setShields(int s) {
			notifyListeners("shieldset", this.getShields(), s);
			shields = s;
		}
		
		public List<Adventure> getHand(){	
			return this.hand;
		}
		
		public Adventure getCard(int i) {
			return hand.get(i);
		}
		
		public boolean getDealer() {
			return dealer;
		}
		
		public void setDealer(boolean b) {
			dealer = b;
		}
		
		public void displayHand() {
			System.out.println(this.getHand().toString());	
		}
		// Getters and Setters --------------------------------
		
		
		public void playCard() {
			
		}
		
		public void bid(int b) {
			
		}
		
		public void drawCard(int j, AdventureDeck deck) { // to do: when player receives card from AD, remove card from AD
			for(int i = 0; i < j; i++) {
				hand.add(deck.top());
				notifyListeners("draw", this.getCard(this.hand.size()-1)); // element in the hand at the end of the list is what was added
			}
		}
		
		//draws a specific card from the AD
		public void drawCard(AdventureDeck deck, String name) {
			hand.add(deck.findAndDraw(name));
		}
		
		//draws a specific card from the SD
		public void drawCard(StoryDeck storyDeck, StoryDiscard storyDiscard, String name) {
			storyDiscard.add(storyDeck.findAndDraw(name));
		}
		
		public void drawCard(StoryDeck storyDeck, StoryDiscard storyDiscard) {
			storyDiscard.add(storyDeck.top());
			int current = storyDiscard.size() - 1;
			notifyListeners("draw", storyDiscard.get(current));
			
			if (storyDiscard.get(current) instanceof Quest) {
				System.out.println("Quest card drawn: " + storyDiscard.get(current).getName());
			}
			else if (storyDiscard.get(current) instanceof Event) {
				System.out.println("Event card drawn: " + storyDiscard.get(current).getName());
			}
			else if (storyDiscard.get(current) instanceof Tournament) {
				System.out.println("Tournament card drawn: " + storyDiscard.get(current).getName());
			}
		}
		
		public void discard(int i) { // discard either from hand or allies in play, implement allies later
			notifyListeners("discard", hand.get(i));
			hand.remove(i);
		}
		
		public void drawRank(String r) {
			notifyListeners("rankset", this.rank, r);
			setRank(r);
		}
		
	}
	//adding adventure cards to each player
/*	public void addPlayerCard() {
		int twelveCards = 12;
		for (int i = 0; i < twelveCards ; i++) {
			hand.add()
		}
	}*/
	
	public void add() {
		persons.add(new Person());
	}
	
	public List<Person> getPlayers() {
        return persons;
    }
	
	// notify listeners: send event message (e.g. drawCard, discard), old value(s), new value(s)
	// possible changes: card drawn/discarded, rank up/down, shields up/down, 
	
	// card drawn notify: what card was drawn, and from where (to determine animation to execute)
	private void notifyListeners(String event, Card card) { // not 100% sure Card type will work for this, 
        for (PropertyChangeListener name : listener) {      // might need two functions, one for adventure and one for story (use instanceof ?)
            name.propertyChange(new PropertyChangeEvent(this, event, null, card));
        } 
    }
	
	// rank change notify
	private void notifyListeners(String event, String oldval, String newval) { 
        for (PropertyChangeListener name : listener) {      
            name.propertyChange(new PropertyChangeEvent(this, event, oldval, newval));
        } 
    }
	
	// shield change notify
		private void notifyListeners(String event, int oldval, int newval) { 
	        for (PropertyChangeListener name : listener) {      
	            name.propertyChange(new PropertyChangeEvent(this, event, oldval, newval));
	        } 
	    }
	
	private void notifyListeners(Object object, String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        } 
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }
}