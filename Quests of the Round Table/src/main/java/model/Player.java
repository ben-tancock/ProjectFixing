package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {
	
	private String name;
	private String rank;
	private boolean dealer;
	private boolean focused;
	private int shields;
	private ArrayList<Adventure> hand;
	private ArrayList<Ally> allies;
	private List<Weapon> weapons;
	private List<Amour> amour;
	//private List<Adventure> bidCards;
	private int bid;
	
	public Player() {
		hand = new ArrayList<Adventure>();
		allies = new ArrayList<Ally>();
		weapons = new ArrayList<Weapon>();
		amour = new ArrayList<Amour>();
		dealer = false;
		focused = false;
		rank = "";
		bid = 0;
	}
	
	// Getters and Setters --------------------------------
	public String getName() {
		return name;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public boolean isFocused() {
		return focused;
	}
	
	public void setFocused(boolean f) {
		focused = f;
	}
	
	public int getRank() {
		if(rank.equals("squire")) {
			return 0;
		}
		else if(rank.equals("knight")) {
			return 5;
		}
		else if(rank.equals("champion_knight")){
			return 12;
		} else {
			return 22;
		}
	}
	
	public String getRankString() {
		return rank;
	}
	
	public void setRank(String s) {
		rank = s;
		if(rank.equals("knight_of_the_round_table")) {
			//notify win
		}
	}
	
	public int getShields() {
		return shields;
	}
	
	public int getHandState() {
		return hand.get(0).getState();
	}
	
	public void setHandState(int state) {
		for(Adventure card : hand) {
			card.setState(state);
		}
	}
	
	public void setShields(int s) {
		//notifyListeners("shieldset", this.getShields(), s);
		shields = s;
		if(shields >= 5 && rank.equals("squire")) {
			setRank("knight");
			shields = shields - 5;
			if(shields >= 7) {
				setRank("champion_knight");
				shields = shields - 7;
				if(shields >= 10) {
					Players.notifyListeners("player won", this);
				}
			}
			Players.notifyListeners("rank set", this);
		}
		else if(shields >= 7 && rank.equals("knight")) {
			setRank("champion_knight");
			shields = shields - 7;
			if(shields >= 10) {
				Players.notifyListeners("player won", this);
			}
			Players.notifyListeners("rank set", this);
		}
		else if(shields >= 10 && rank.equals("champion_knight")) {
			Players.notifyListeners("player won", this);
		}
	}
	
	public List<Adventure> getHand(){	
		return this.hand;
	}
	
	public List<Ally> getAllies(){	
		return this.allies;
	}
	
	public List<Weapon> getWeapons() {
		return this.weapons;
	}
	
	public List<Amour> getAmour() {
		return this.amour;
	}
	/*
	public List<Adventure> getBidCards() {
		return this.bidCards;
	}*/
	
	public Adventure getCard(int i) {
		return hand.get(i);
	}
	
	public boolean getDealer() {
		return dealer;
	}
	
	public void setDealer(boolean b) {
		dealer = b;
	}
	
	public int getBattlePoints() {
		int totalBP = 0;
		if(allies.size() > 0) {
			for(Ally a : allies) {
				totalBP += a.getBattlePoints();
			}
		} if(weapons.size() > 0) {
			for(Weapon w : weapons) {
				totalBP += w.getBattlePoints();
			}
		} if(amour.size() > 0) {
			totalBP += amour.get(0).getBattlePoints();
		}
		return totalBP;
	}
	
	public int getMaxBid() {
		int totalBid = 0;
		if(allies.size() > 0) {
			for(Ally a : allies) {
				totalBid += a.getBids();
			}
		}
		totalBid += hand.size();
		return totalBid;
	}
	
	public void displayHand() {
		System.out.println(this.getHand().toString());	
	}
	// Getters and Setters --------------------------------
	
	
	public Adventure playCard(Adventure card, boolean toPlayingSurface) {
		boolean success;
		if(toPlayingSurface == true) {
			if(card instanceof Ally) {
				success = hand.remove(card);
				allies.add((Ally) card);
			} else if(card instanceof Weapon) {
				//check for duplicates and notify the controller if so
				for(Weapon w : weapons) {
					if(w.getName().equals(card.getName())) {
						success = false;
						Players.notifyListeners("invalid card played", this);
					}
				}
				success = hand.remove(card);
				weapons.add((Weapon) card);
			} else if(card instanceof Amour) {
				if(!amour.isEmpty()) {
					success = false;
					Players.notifyListeners("invalid card played", this);
				}
				success = hand.remove(card);
				amour.add((Amour) card);
			} else {
				Players.notifyListeners("invalid card played", this);
				success = false;
			}
		} else {
			success = hand.remove(card);	
		}
		if(success) {
			return card;
		} else {
			return null;
		}
	}
	
	public List<Adventure> getPlayingSurface() {
		List<Adventure> playingSurface = new ArrayList<Adventure>();
		if(allies.size() > 0) {
			playingSurface.addAll(allies);
		}
		if(weapons.size() > 0) {
			playingSurface.addAll(weapons);
		}
		if(amour.size() > 0) {
			playingSurface.addAll(amour);
		}
		return playingSurface;
	}
	
	public void displaySurface() {
		System.out.println(this.getPlayingSurface().toString());
	}
	
	public void bid(int b) {
		bid = b;
	}
	
	public int getBid() {
		return bid;
	}
	
	public void drawCard(int j, AdventureDeck deck) { // to do: when player receives card from AD, remove card from AD
		for(int i = 0; i < j; i++) {
			hand.add(deck.top());
			//notifyListeners("draw", this.getCard(this.hand.size()-1)); // element in the hand at the end of the list is what was added
		}
		
		if(hand.size() > 12) {
			Players.notifyListeners("card overflow", this);
		}
	}
	
	//draws a specific card from the AD
	public void drawCard(AdventureDeck deck, String name) throws Exception {
		hand.add(deck.findAndDraw(name));
		
		if(hand.size() > 12) {
			Players.notifyListeners("card overflow", this);
		}
	}
	
	//draws a specific card from the SD
	public void drawCard(StoryDeck storyDeck, StoryDiscard storyDiscard, String name) {
		storyDiscard.add(storyDeck.findAndDraw(name));
		int current = storyDiscard.size() - 1;
		//notifyListeners("draw", storyDiscard.get(current));
		try {
			storyDiscard.get(current).setState(CardStates.FACE_UP);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (storyDiscard.get(current) instanceof Quest) {
			Players.notifyListeners("quest drawn", this);
		}
		else if (storyDiscard.get(current) instanceof Event) {
			Players.notifyListeners("event drawn", this);
		}
		else if (storyDiscard.get(current) instanceof Tournament) {
			Players.notifyListeners("tournament drawn", this);
		}
		
		if(hand.size() > 12) {
			Players.notifyListeners("card overflow", this);
		}
	}
	
	public void drawCard(StoryDeck storyDeck, StoryDiscard storyDiscard) {
		storyDiscard.add(storyDeck.top());
		int current = storyDiscard.size() - 1;
		//notifyListeners("draw", storyDiscard.get(current));
		try {
			storyDiscard.get(current).setState(CardStates.FACE_UP);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (storyDiscard.get(current) instanceof Quest) {
			Players.notifyListeners("quest drawn", this);
		}
		else if (storyDiscard.get(current) instanceof Event) {
			Players.notifyListeners("event drawn", this);
		}
		else if (storyDiscard.get(current) instanceof Tournament) {
			Players.notifyListeners("tournament drawn", this);
		}
		
		if(hand.size() > 12) {
			Players.notifyListeners("card overflow", this);
		}
	}
	
	public void discard(Adventure card, AdventureDiscard discardPile, boolean onPlaySurface) throws Exception { // discard either from hand or allies in play, implement allies later
		
		boolean success;
		if(onPlaySurface) {
			if(card instanceof Ally) {
				success = allies.remove(card);
			} else if(card instanceof Weapon) {
				success = weapons.remove(card);
			} else if(card instanceof Amour) {
				success = amour.remove(card);
			}else {
				success = false;
			}
		} else {
			success = hand.remove(card);
		}
		if(success) {
			discardPile.add(card);
		}
	}
	
	public void remove(List<? extends Card> from, List<? extends Card> to, Card c) {
		((List<Card>)to).add(c);
		from.remove(c);
		
	}
	
	public void drawRank(String r) {
		//notifyListeners("rankset", this.rank, r);
		setRank(r);
	}

}