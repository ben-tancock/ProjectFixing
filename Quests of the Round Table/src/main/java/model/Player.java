package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.List;
import model.Card;
import model.Adventure;

public class Player {
	public static String NAME = "name";
	public static int SHIELDS = 0;
	public static String RANK = "squire";
	public static boolean dealer = false;
	private List<Card> HAND = new ArrayList<Card>(); 
	private List<Card> ALLIES = new ArrayList<Card>();
	
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
		private List<Card> hand = new ArrayList<Card>();
		private List<Card> allies = new ArrayList<Card>();
		
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
		
		public List<Card> getHand(){
			
			return this.hand;
		}
		
		public Card getCard(int i) {
			
			return hand.get(i);
		}
		
		public void setDealer(boolean b) {
			dealer = false;
		}
		// Getters and Setters --------------------------------
		
		
		public void playCard() {
			
		}
		
		public void bid(int b) {
			
		}
		
		public void drawCard(int j, String type) {
			if(type == "Adventure") {
				for(int i = 0; i < j; i++) {
					//hand.add(new Adventure());
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
			}
			
		}
		
		public void drawRank(String r) {
			setRank(r);
		}
		
	}
	
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