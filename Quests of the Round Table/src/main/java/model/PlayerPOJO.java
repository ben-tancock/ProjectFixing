package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayerPOJO implements Serializable{
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
	private int bid;
	private int aBP;
	
	public PlayerPOJO() {
		this.name = "";
		this.rank = "";
		this.dealer = false;
		this.focused = false;
		this.shields = 0;
		this.shieldName = "";
		this.hand = new ArrayList<>();
		this.allies = new ArrayList<>();
		this.weapons = new ArrayList<>();
		this.amour = new ArrayList<>();
		this.bid = 0;
		this.aBP = 0;
	}

	public PlayerPOJO(String name, String rank, boolean dealer, boolean focused, int shields, String shieldName,
			List<Adventure> hand, List<Ally> allies, List<Weapon> weapons, List<Amour> amour, 
			int bid, int aBP) {
		this.name = name;
		this.rank = rank;
		this.dealer = dealer;
		this.focused = focused;
		this.shields = shields;
		this.shieldName = shieldName;
		this.hand = hand;
		this.allies = allies;
		this.weapons = weapons;
		this.amour = amour;
		this.bid = bid;
		this.aBP = aBP;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public boolean getDealer() {
		return dealer;
	}
	public void setDealer(boolean dealer) {
		this.dealer = dealer;
	}
	public boolean getFocused() {
		return focused;
	}
	public void setFocused(boolean focused) {
		this.focused = focused;
	}
	public int getShields() {
		return shields;
	}
	public void setShields(int shields) {
		this.shields = shields;
	}
	public String getShieldName() {
		return shieldName;
	}
	public void setShieldName(String shieldName) {
		this.shieldName = shieldName;
	}
	public List<Adventure> getHand() {
		return hand;
	}
	public void setHand(List<Adventure> hand) {
		this.hand = hand;
	}
	public List<Ally> getAllies() {
		return allies;
	}
	public void setAllies(List<Ally> allies) {
		this.allies = allies;
	}
	public List<Weapon> getWeapons() {
		return weapons;
	}
	public void setWeapons(List<Weapon> weapons) {
		this.weapons = weapons;
	}
	public List<Amour> getAmour() {
		return amour;
	}
	public void setAmour(List<Amour> amour) {
		this.amour = amour;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public int getaBP() {
		return aBP;
	}
	public void setaBP(int aBP) {
		this.aBP = aBP;
	}
	
	
	
}
