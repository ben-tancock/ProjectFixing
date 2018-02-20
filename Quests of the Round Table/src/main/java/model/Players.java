package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import control.ControlHandler;
import control.PlayGame.PlayGameControlHandler;

public class Players {
	
	public List<Player> persons = new ArrayList<Player>();
	//private List<PropertyChangeListener> listener = new ArrayList<PropertyChangeListener>();
	private static List<ControlHandler> listeners = new ArrayList<ControlHandler>();
	
	public Players() {
		
	}
	
	
	//adding adventure cards to each player
/*	public void addPlayerCard() {
		int twelveCards = 12;
		for (int i = 0; i < twelveCards ; i++) {
			hand.add()
		}
	}*/
	
	public void addHuman() {
		persons.add(new Person());
	}
	
	public void addAI1() {
		persons.add(new ArtificialIntelligence1());
	}
	
	public void addAI2() {
		persons.add(new ArtificialIntelligence2());
	}
	
	public List<Player> getPlayers() {
        return persons;
    }
	
	// notify listeners: send event message (e.g. drawCard, discard), old value(s), new value(s)
	// possible changes: card drawn/discarded, rank up/down, shields up/down, 
	
	//Possible card overflow notify?
	public static void notifyListeners(String event, Player p) {
		if(event.equals("card overflow")) {
			listeners.get(0).onCardOverflow(p);
		}
	}
	
	public void addListener(ControlHandler c) {
		listeners.add(c);
	}
	
	// card drawn notify: what card was drawn, and from where (to determine animation to execute)
	//private void notifyListeners(String event, Card card) { // not 100% sure Card type will work for this, 
    //    for (PropertyChangeListener name : listener) {      // might need two functions, one for adventure and one for story (use instanceof ?)
    //        name.propertyChange(new PropertyChangeEvent(this, event, null, card));
    //    } 
    //}
	
	// rank change notify
	//private void notifyListeners(String event, String oldval, String newval) { 
    //    for (PropertyChangeListener name : listener) {      
    //        name.propertyChange(new PropertyChangeEvent(this, event, oldval, newval));
    //    } 
   // }
	
	// shield change notify
	//	private void notifyListeners(String event, int oldval, int newval) { 
	//        for (PropertyChangeListener name : listener) {      
	//            name.propertyChange(new PropertyChangeEvent(this, event, oldval, newval));
	//        } 
	//    }
	
	//private void notifyListeners(Object object, String property, String oldValue, String newValue) {
    //    for (PropertyChangeListener name : listener) {
    //        name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
    //    } 
    //}

    //public void addChangeListener(PropertyChangeListener newListener) {
    //    listener.add(newListener);
   // }
}