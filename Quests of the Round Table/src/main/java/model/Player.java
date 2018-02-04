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
	//AdventureDeck advDeck = new AdventureDeck(); decks kept separate for now
	
	public List<Person> persons = new ArrayList<Person>();
	private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();

	public Player() {
		
	}
	
	
	public static class Person { // not entirely sure if this should be static...
		
		public Person() {
			
		}
		private String name;
		private String rank;
		private boolean dealer = false;
		private int shields;
		private List<Adventure> hand = new ArrayList<Adventure>();
		private List<Ally> allies = new ArrayList<Ally>();
		
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
			}
			
			/*if(type == "Adventure") {
				for(int i = 0; i < j; i++) {
					hand.add(advDeck.top());
				}
			}
			
			else if(type == "Quest") {
				for(int i = 0; i < j; i++) {
					//hand.add(new Adventure());
				}
			}
			
			if(type == "Event") {
				for(int i = 0; i < j; i++) {
					//hand.add(new Adventure());
				}
			}*/
			
		}
		
		public void drawRank(String r) {
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
	
	private void notifyListeners(Object object, String property, String oldValue, String newValue) {
        for (PropertyChangeListener name : listener) {
            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
        }
    }

    public void addChangeListener(PropertyChangeListener newListener) {
        listener.add(newListener);
    }
}