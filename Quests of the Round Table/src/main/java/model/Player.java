package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import control.PlayGame;
import control.QuestHandler;

@JsonDeserialize(using = PlayerDeserializer.class)
public abstract class Player implements Serializable {
	/**
	 * Player class needed to be serializable
	 */
	private static final long serialVersionUID = 9067267335225726349L;
	private static final Logger logger = LogManager.getLogger(Player.class);
	private String name;
	private String rank;
	private boolean dealer;
	private boolean focused;
	private int shields;
	private String shieldName;
	private List<Adventure> hand;
	private List<Ally> allies;
	private List<Weapon> weapons;
	private List<Amour> amour;
	//private List<Adventure> bidCards;
	private int bid;
	private int aBP;
	
	
	public Player() {
		hand = new ArrayList<Adventure>();
		allies = new ArrayList<Ally>();
		weapons = new ArrayList<Weapon>();
		amour = new ArrayList<Amour>();
		dealer = false;
		focused = false;
		name = "";
		rank = "squire";
		shields = 0;
		bid = 0;
		aBP = 0;
		shieldName = "";
	}
	
	public Player fromMap(HashMap<String, String> playerMap) {
		System.out.println("GOT HERE");
		if(playerMap.get("name") != null) {
			name = playerMap.get("name");
			System.out.println("name = " + name);
		}
		if(playerMap.get("shieldName") != null) {
			shieldName = playerMap.get("shieldName");
			System.out.println("shieldName = " + shieldName);
		}
		/*
		if(!playerMap.get("rank").equals(null) || !playerMap.get("rank").equals("")) {
			System.out.println(playerMap.get("rank").getClass());
		}
		System.out.println("GOT HERE");
		if(playerMap.get("focused") != null) {
			focused = Boolean.parseBoolean(playerMap.get("focused"));
			System.out.println("focused = " + focused);
		}
		if(playerMap.get("dealer") != null) {
			dealer = Boolean.parseBoolean(playerMap.get("dealer"));
			System.out.println("dealer = " + dealer);
		}
		if(playerMap.get("shields") != null) {
			shields = Integer.parseInt(playerMap.get("shields"));
			System.out.println("shields = " + shields);
		}
		
		if(playerMap.get("bid") != null) {
			bid = Integer.parseInt(playerMap.get("bid"));
			System.out.println("bid = " + bid);
		}
		if(playerMap.get("aBP") != null) {
			aBP = Integer.parseInt(playerMap.get("aBP"));
			System.out.println("aBP = " + aBP);
		}*/
		return this;
	}
	
	public Player fromPOJO(PlayerPOJO pojo) {
		name = pojo.getName();
		shieldName = pojo.getShieldName();
		rank = pojo.getRank();
		focused = pojo.getFocused();
		dealer = pojo.getDealer();
		shields = pojo.getShields();
		bid = pojo.getBid();
		aBP = pojo.getaBP();
		hand = pojo.getHand();
		weapons = pojo.getWeapons();
		allies = pojo.getAllies();
		amour = pojo.getAmour();
		/*
		if(!playerMap.get("rank").equals(null) || !playerMap.get("rank").equals("")) {
			System.out.println(playerMap.get("rank").getClass());
		}
		System.out.println("GOT HERE");
		if(playerMap.get("focused") != null) {
			focused = Boolean.parseBoolean(playerMap.get("focused"));
			System.out.println("focused = " + focused);
		}
		if(playerMap.get("dealer") != null) {
			dealer = Boolean.parseBoolean(playerMap.get("dealer"));
			System.out.println("dealer = " + dealer);
		}
		if(playerMap.get("shields") != null) {
			shields = Integer.parseInt(playerMap.get("shields"));
			System.out.println("shields = " + shields);
		}
		
		if(playerMap.get("bid") != null) {
			bid = Integer.parseInt(playerMap.get("bid"));
			System.out.println("bid = " + bid);
		}
		if(playerMap.get("aBP") != null) {
			aBP = Integer.parseInt(playerMap.get("aBP"));
			System.out.println("aBP = " + aBP);
		}*/
		return this;
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
	
	public String getShieldName() {
		return shieldName;
	}
	
	public void setShieldName(String s) {
		shieldName = s;
	}
	
	public int getHandState() {
		if(hand.size() > 0) {
			return hand.get(0).getState();
		} else {
			return CardStates.FACE_DOWN;
		}
	}
	
	public ArrayList<Adventure> getStrongestHand(int increasingOrDecreasing) {
		ArrayList<Adventure> strongestHand = new ArrayList<>();
		for(Adventure card : hand) {
			if(card instanceof Weapon) {
				boolean distinct = true;
				for(Adventure c : strongestHand) {
					if(c.getName().equals(card.getName())) {
						distinct = false;
						break;
					}
				}
				for(Weapon w : weapons) {
					if(w.getName().equals(card.getName())) {
						distinct = false;
						break;
					}
				}
				if(distinct)
					strongestHand.add(card);
			} else if(card instanceof Ally){
				strongestHand.add(card);
			}
			if(amour.size() == 0 && card instanceof Amour) {
				strongestHand.add(card);
			}
		}
		
		Collections.sort(strongestHand, (c1, c2)-> c2.getBattlePoints());
		Foe f = (Foe)Collections.min(strongestHand, (c1, c2) -> c2.getBattlePoints());
		
		if (increasingOrDecreasing == IncreasingOrDecreasing.DECREASING) {
			Collections.reverse(strongestHand);
		}
		return strongestHand;
	}
	
	public boolean checkWinOrEvolve(Players players, int potentialShields) {
		for(Player p : players.getPlayers()) {
			//if they're a squire and could become a knight
			if(p.getRank() == 0 && (p.getShields() + potentialShields) >= 5 ) {
				return true;
			}
			//else if they're a knight and could become a champion knight
			else if(p.getRank() == 5 && (p.getShields() + potentialShields) >= 7) {
				return true;
			}
			//else if they're a champion knight and could win
			else if (p.getRank() == 12 && (p.getShields() + potentialShields) >= 10) {
				return true;
			}
		}
		return false;
	}
	
	public boolean twoFoesUnderXBP(int x, Quest q) {
		int count = 0;
		for(Adventure a : hand) {
			if(((Foe)a).getFoeBP(q.getSpecialFoes()) < x) {
				count ++;
			}
		}
		if(count >= 2) {
			return true;
		}
		return false;
	}
	
	public Foe getFoeWithHighestBP(String spfs) {
		HashMap<Integer, Integer> foeBPMap = new HashMap<>();
		for(Adventure card : getHand()) {
			if(card instanceof Foe) {
				foeBPMap.put(Integer.valueOf(getHand().indexOf(card)), ((Foe)card).getFoeBP(spfs));
			}
		}
		ValueComparator valueComparator = new ValueComparator(foeBPMap);
		TreeMap<Integer, Integer> sortedFoeBPMap = new TreeMap<>(valueComparator);
		int indexOfHighestBPFoe = sortedFoeBPMap.lastKey().intValue();
		
		return (Foe)getHand().get(indexOfHighestBPFoe);
	}
	
	public Weapon getStrongestWeapon(String n) {
		HashMap <Integer, Integer> weaponBPMap = new HashMap<>();
		for(Adventure card : getHand()) {
			if(card instanceof Weapon) {
				weaponBPMap.put(Integer.valueOf(getHand().indexOf(card)), ((Weapon)card).getBattlePoints());
			}
		}
		ValueComparator valueComparator = new ValueComparator(weaponBPMap);
		TreeMap<Integer, Integer> sortedWeaponBPMap = new TreeMap<>(valueComparator);
		Weapon strongestWeapon = null;
		while(strongestWeapon == null && sortedWeaponBPMap.size() > 0) {
			strongestWeapon = (Weapon)getHand().get(sortedWeaponBPMap.lastKey());
			if(strongestWeapon.getName().equals(n)) { //make sure it isn't a duplicate of the previous
				sortedWeaponBPMap.remove(sortedWeaponBPMap.lastKey());
				strongestWeapon = null;
			}
		}
		return strongestWeapon;
	}
	
	public Set<Weapon> findDuplicateWeapons() {
		Set<Weapon> setToReturn = new HashSet<>();
		Set<Weapon> set1 = new HashSet<>();
		for(Adventure a : hand) {
			if(a instanceof Weapon && !set1.add((Weapon) a)) {
				setToReturn.add((Weapon) a);
			}
		}
		
		return setToReturn;
	}
	
	public boolean checkIfEnoughFoes(Quest card) {
		int count = 0;
		//check for a test and if there is one, increase count by 1
		for(Adventure a : hand) {
			if(a instanceof Test) {
				count++;
				break; //there's a test, increase the count and exit
			}
		}
		
		// a set removes duplicates
		HashSet<Integer> setOfBP = new HashSet<>(); 
		for(Adventure a : hand) {
			if(a instanceof Foe) {
				int foeBP = ((Foe)a).getFoeBP(card.getSpecialFoes());
				setOfBP.add(Integer.valueOf(foeBP));
			}
		}
		
		//different bp means there's some sort of increasing order, so increase the count
		for(Integer bp : setOfBP) {
			count++;
		}
		
		if(count >= card.getNumStages()) {
			return true;
		}
		return false;
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
		return totalBP + getRankBP();
	}
	
	public int getRankBP() {
		if (rank.equals("squire")) {
			return 5;
		}
		else if (rank.equals("knight")) {
			return 10;
		}
		else if (rank.equals("champion_knight")) {
			return 20;
		}
		return 0;
	}
	
	public void setAllyBp(int x) {
		aBP = x;
	}
	
	public int getABP() {
		return aBP;
	}
	
	public int getAllyBp() {
		int setABp = 0;
		if(allies.size() > 0) {
			for(Ally a : allies) {
				setABp += a.getBattlePoints();
			}
		}
		return setABp;
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
	
	public void setBid(int bid) {
		this.bid = bid;
	}
	
	public void drawCard(int j, AdventureDeck deck) { // to do: when player receives card from AD, remove card from AD
		if(deck.size() == 0) {
			Players.notifyListeners("deck empty", this, j);
		} else {
			for(int i = 0; i < j; i++) {
				hand.add(deck.top());
				if(deck.size() == 0) {
					int cardsLeftToDraw = (j - 125);
					Players.notifyListeners("deck empty", this, cardsLeftToDraw);
					break;
				}
			}
		
			if(deck.size() == 0) {
				Players.notifyListeners("deck empty", this, 0);
			}
			
			logger.info(j + " card(s) drawn by " + getName() + " from Adventure Deck.");
			logger.info("Adventure Deck Count: " + deck.size() + " Player Hand Count: " + getHand().size());
			
			if(hand.size() > 12) {
				logger.info(name + " has too many cards! Notifying the controller!");
				Players.notifyListeners("card overflow", this);
			}
		}
	}
	
	//draws a specific card from the AD
	public void drawCard(AdventureDeck deck, String name) throws Exception {
		if(deck.size() == 0) {
			Players.notifyListeners("deck empty", this, 1);
		} else {
			hand.add(deck.findAndDraw(name));
			if(deck.size() == 0) {
				Players.notifyListeners("deck empty", this, 0);
			}
			
			
			logger.info(name + " card drawn by " + getName() + " from Adventure Deck.");
			logger.info("Adventure Deck Count: " + deck.size() + " Player Hand Count: " + getHand().size());
			
			if(hand.size() > 12) {
				logger.info(name + " has too many cards! Notifying the controller!");
				Players.notifyListeners("card overflow", this);
			}
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
		logger.info("Story card drawn by " + getName() + ": " + storyDiscard.get(current).getName());
		logger.info("Story Deck Count: " + storyDeck.size() + " Story Discard Pile Count: " + storyDiscard.size());
		if (storyDiscard.get(current) instanceof Quest) {
			Players.notifyListeners("quest drawn", this);
		}
		else if (storyDiscard.get(current) instanceof Event) {
			Players.notifyListeners("event drawn", this);
		}
		else if (storyDiscard.get(current) instanceof Tournament) {
			Players.notifyListeners("tournament drawn", this);
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
		logger.info("Story card drawn by " + getName() + ": " + storyDiscard.get(current).getName());
		logger.info("Story Deck Count: " + storyDeck.size() + " Story Discard Pile Count: " + storyDiscard.size());
		if (storyDiscard.get(current) instanceof Quest) {
			Players.notifyListeners("quest drawn", this);
		}
		else if (storyDiscard.get(current) instanceof Event) {
			Players.notifyListeners("event drawn", this);
		}
		else if (storyDiscard.get(current) instanceof Tournament) {
			Players.notifyListeners("tournament drawn", this);
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
	
	public void sponsor(Quest q) {
		q.setSponsor(this);
	}
	
	public void remove(List<? extends Card> from, List<? extends Card> to, Card c) {
		((List<Card>)to).add(c);
		from.remove(c);
		
	}
	
	public void drawRank(String r) {
		//notifyListeners("rankset", this.rank, r);
		setRank(r);
	}
	
	//discarding foes with BP less than 20 0r 25 ....needed for AI
	public void discardFoesOfLessThansomethingPoints(int point) {
		for(Adventure a : getHand()) {
			if(a instanceof Foe && a.getBattlePoints() < point) {
				remove(getHand(), PlayGame.getInstance().getADiscard(), a);
				Players.notifyListeners("card discarded", this, a);
			}
		}
	}
	
	public boolean validBid(int numBids) {
		if(numBids > QuestHandler.getInstance().getCurrentBid()) {
			return true;
		}
		return false;
	}
	
	@JsonProperty("wrapper")
	@Override
	public String toString() {
		return getName();
	}

}