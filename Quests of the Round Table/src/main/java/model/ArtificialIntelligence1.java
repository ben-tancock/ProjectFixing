package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import control.QuestHandler;

public class ArtificialIntelligence1 extends Player implements AIStrategies {

	@Override
	public boolean doIParticipateInTournament(Players p, Tournament t) {
		//check if current participants including AI can win/evolve 
		if(checkWinOrEvolve(p, p.getPlayers().size() + t.getBonus())) {
			Players participants = new Players();
			participants.getPlayers().addAll(t.getParticipants());
			participants.getPlayers().add(this);
			if(checkWinOrEvolve(participants, p.getPlayers().size() + t.getBonus())) {
					List<Adventure> strongestHand = getStrongestHand(IncreasingOrDecreasing.INCREASING);
					for(Adventure card : strongestHand) {
						Players.notifyListeners("card played", this, card);
					}
		     } else {
					Set<Weapon> duplicates = findDuplicateWeapons();
					for(Weapon w : duplicates) {
						Players.notifyListeners("card played", this, w);
					}
			}
			return true;
	    }
		return false;
	}

	@Override
	public boolean doISponsorAQuest(Players p, Quest q) {
		Players prClone = p;
		prClone.getPlayers().remove(this);
		if(checkWinOrEvolve(prClone, q.getNumStages())) {
			return false;
		} else if(checkIfEnoughFoes(q)) {
			setup1(q);
			return true;
		}
		return false;
	}

	@Override
	public boolean doIParticipantInQuest(Players p, Quest q) {
		if(twoWeaponsAlliesPerStage(q) && twoFoesUnderXBP(20, q)) {
		    play1(q);
			return true;	
		}
 		return false;
	}

	@Override
	public int nextBid(Quest q) {
		if(getBid() > 0) {
			return 0;
		}
		if(containsFoeWithLessThan20BP(q)) {
		    setBid(numFoesWithLessThan20BP(q));
		    return getBid();
		}
		return 0;
	}

	@Override
	public List<Adventure> discardAfterWinningTest() {
		discardFoesOfLessThansomethingPoints(20);
		return null;
	}
	
	
	/**
	 *	setup last stage to be atleast 50
	 *	setup second last stage to be a test (if possible)
	 *	starting with the next stage toward first:
	 *		pick strongest foe
	 *		set with one weapon that is a duplicate and respect order of BP
	 */  
	public void setup1(Quest card) {
		//setup last stage
		Stage lastStage = setupStageToBeAtleast50(card); 
		int restOfStages = card.getNumStages();
		int currentBP = 0;
		if(lastStage == null) {// if they couldn't make a stage with 50 BP
			lastStage = nextHighestFoeBPWithDupWeapon(currentBP, card.getSpecialFoes());
		}
		currentBP = lastStage.getBattlePoints();
		restOfStages -= 1;
		
		//setup second last stage
		Stage secondLastStage = null;
		for(Adventure a : getHand()) {
			if(a instanceof Test) {
				secondLastStage = new Stage((Test) a);
				getHand().remove(a);
				break; // only taking a test out if found
			}
		}
		if(secondLastStage != null) { // there was a test
			restOfStages -= 1;
		} 
		List<Stage> restOfStagesList = new ArrayList<>(); // get the rest of the stages setup
		for(int i = 0; i < restOfStages; i++) {
			Stage nextLastStage = nextHighestFoeBPWithDupWeapon(currentBP, card.getSpecialFoes());
			currentBP = nextLastStage.getBattlePoints();
			restOfStagesList.add(nextLastStage);
		}
		Collections.reverse(restOfStagesList); //reverse the order so that they're in the right order
		if(secondLastStage != null) { //add second last stage if it exists
			restOfStagesList.add(secondLastStage);
		}
		restOfStagesList.add(lastStage); // add the last stage
		for(Stage s : restOfStagesList) { //add all stages to the quest
			card.addStage(s);
		}
	}

	public Stage setupStageToBeAtleast50(Quest card) {
		int stageBP = 0;
		//Choose foe with highest BP and increase stage BP with that
		Foe f = getFoeWithHighestBP(card.getSpecialFoes());
		stageBP += f.getFoeBP(card.getSpecialFoes());
		
		ArrayList<Weapon> weapons = new ArrayList<>();
		//We need to keep grabbing the weapon with the highest battlepoints until the stage battlepoints are atleast 50.
		
		String currWeaponName = "";
		int maxIter = getHand().size();
		int iter = 0;
		while(stageBP < 50 && iter < maxIter) {
			Weapon w = getStrongestWeapon(currWeaponName); //get strongest weapon that hasn't already been chosen
			currWeaponName = w.getName();
			weapons.add(w);
			stageBP += w.getBattlePoints();
			iter++;
		}
		if(stageBP >= 50) { //if stage was successfully made, remove the foe and weapons from the hand and return the stage created with them
			getHand().remove(f);
			getHand().removeAll(weapons);
			return new Stage(f, weapons);
		} else { // otherwise return null
			return null;
		}
	}
	
	public Stage nextHighestFoeBPWithDupWeapon(int BPtoCompare, String spfs) {
		HashMap<Integer, Integer> foeBPMap = new HashMap<>();
		for(Adventure card : getHand()) {
			if(card instanceof Foe) {
				foeBPMap.put(Integer.valueOf(getHand().indexOf(card)), ((Foe)card).getFoeBP(spfs));
			}
		}
		ValueComparator valueComparator = new ValueComparator(foeBPMap);
		TreeMap<Integer, Integer> sortedFoeBPMap = new TreeMap<>(valueComparator);
		int indexOfHighestBPFoe = sortedFoeBPMap.lastKey().intValue();
		Foe f = (Foe)getHand().get(indexOfHighestBPFoe);
		List<Object> duplicateWeapons = Arrays.asList(findDuplicateWeapons().toArray());
		
		int totalBP = 0;
		totalBP += f.getFoeBP(spfs);
		totalBP += ((Weapon)duplicateWeapons.get(0)).getBattlePoints();
		while(totalBP >= BPtoCompare) {
			totalBP -= ((Weapon)duplicateWeapons.get(0)).getBattlePoints();
			duplicateWeapons.remove(0);
			if(duplicateWeapons.isEmpty()) {
				break;
			}
			totalBP += ((Weapon)duplicateWeapons.get(0)).getBattlePoints();
		}
		if(totalBP < BPtoCompare) {
			ArrayList<Weapon> weapon = new ArrayList<>();
			if(duplicateWeapons != null) {
				weapon.add((Weapon)duplicateWeapons.get(0));
			}
			return new Stage(f, weapon);
		}
		return null;
	}
	
	public boolean twoWeaponsAlliesPerStage(Quest q) {
		int count = 0;
		for(Adventure a : getHand()) {
			if(a instanceof Weapon || a instanceof Ally) {
				count++;
			}
		}
		if (count >= (q.getNumStages() * 2)) {
			return true;
		}
		return false;
	}
	
	public void play1(Quest q) {
		QuestHandler qh = QuestHandler.getInstance();
		if(q.getStages().get(qh.getCurrentStage()).getTest() != null) {
			nextBid(q);
	    	if(q.getParticipants().size() == 1 && q.getParticipants().contains(this)) { //this guy one
				discardAfterWinningTest();
			}
	    } else {
	        //sort available allies/amour/weapons in decreasing order of BPs
	       	int amtPlayed = 0;
	        ArrayList<Adventure> playableCards = grabAndSortPlayableCardsInDecreasingOrderOfBPs();
	        if(qh.getCurrentStage() == q.getNumStages() - 1) { //if it's the last stage
	           	for(Adventure card : playableCards) {
	           		Players.notifyListeners("card played", this, card);
	           	}
			} else if (hasAllyOrAmour(playableCards)) {
				amtPlayed += playUpToTwoAlliesOrAmours(playableCards);
			} else if(amtPlayed < 2) {
				playWeakestWeapons(playableCards, 2-amtPlayed);
			}
	    }	
	}
	
	//helper methods for play1
	public ArrayList<Adventure> grabAndSortPlayableCardsInDecreasingOrderOfBPs() {
		return getStrongestHand(IncreasingOrDecreasing.DECREASING);
	}
	
	public boolean hasAllyOrAmour(ArrayList<Adventure> playableCards) {
		for(Adventure a : playableCards) {
			if(a instanceof Ally || a instanceof Amour) {
				return true;
			}
		}
		return false;
	}
	
	public int playUpToTwoAlliesOrAmours(ArrayList<Adventure> playableCards) {
		int count = 0;
		for(Adventure a : playableCards) {
			if(count >= 2) {
				break;
			}
			if(a instanceof Amour && getAmour().size() > 0) {
				Players.notifyListeners("card played", this, a);
				playableCards.remove(a);
				count ++;
			}
			if(a instanceof Ally) {
				Players.notifyListeners("card played", this, a);
				playableCards.remove(a);
				count ++;
			}
		}
		return count;
	}
	
	public int playWeakestWeapons(ArrayList<Adventure> playableCards, int req) {
		//Want the weakest first, so reverse the list
		Collections.reverse(playableCards);
		int count = 0;
		//now play the weakest weapons
		for(Adventure a : playableCards) {
			if(count >= req) {
				break;
			}
			if(a instanceof Weapon) {
				Players.notifyListeners("card played", this, a);
				playableCards.remove(a);
				count++;
			}
		}
		return count;
	}
	
	public boolean containsFoeWithLessThan20BP(Quest q) {
		for(Adventure a : getHand()) {
			if(a instanceof Foe && ((Foe)a).getFoeBP(q.getSpecialFoes()) < 20) {
				return true;
			}
		}
		return false;
	}
	
	public int numFoesWithLessThan20BP(Quest q) {
		return 0;
	}
}
