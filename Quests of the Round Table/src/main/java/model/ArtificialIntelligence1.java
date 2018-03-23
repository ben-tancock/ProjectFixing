package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class ArtificialIntelligence1 extends Player implements AIStrategies {

	@Override
	public boolean doIParticipateInTournament(Players p, Tournament t) {
		//check if current participants including AI can win/evolve 
		if(checkWinOrEvolve(p, p.getPlayers().size() + t.getBonus())) {
			Players participants = new Players();
			participants.getPlayers().addAll(t.getParticipants());
			participants.getPlayers().add(this);
			if(checkWinOrEvolve(participants, p.getPlayers().size() + t.getBonus())) {
					List<Adventure> strongestHand = getStrongestHand();
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
			//quest will run setup1 that is tied to this strategy class
			// yes I do sponsor.
			return true;
		}
		return false;
	}

	@Override
	public boolean doIParticipantInQuest(Players p, Quest q) {
		//if(I have 2 weapons/Allies for each stage AND I have atleast 2 foes with less than 20 BP) {
		//  play1() {
		//		if(currentStage is a Test) {
		//			nextBid();
	    //			if(Test is won) {
		//				discardAfterWinningTest();
		//			}
		//      } else {
		//			sort available allies/amour/weapons in decreasing order of BPs
		//          if(Stage is last stage) {
		//				play strongest valid combination.
		//		    } else if (I have 1 or 2 allies/amour) {
		//				play them.
		//			}
		//			if(less than 2 have been played AND I have enough weapons) {
		//				play weakest weapon(s) until 2 cards have been played.
		//			}
		//		}
		//	}
		//	return true;	
		//}
 		return false;
	}

	@Override
	public int nextBid() {
		//if(has bid already) {
		//	return 0;
		//}
		//if(foes of less than 20 points are in hand) {
		//	return numFoesWithLessThan20Points;
		//}
		return 0;
	}

	@Override
	public List<Adventure> discardAfterWinningTest() {
		//discard foes of less than 20 points in hand
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
			
	}

	public Stage setupStageToBeAtleast50(Quest card) {
		int stageBP = 0;
		//Choose foe with highest BP and increase stage BP with that
		Foe f = getFoeWithHighestBP(card.getSpecialFoes());
		
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
		return null;
	}
	
	public Foe getFoeWithHighestBP(String spfs) {
		HashMap<Integer, Integer> foeBPMap = new HashMap<>();
		for(Adventure card : getHand()) {
			if(card instanceof Foe) {
				foeBPMap.put(Integer.valueOf(getHand().indexOf(card)), getFoeBP((Foe) card, spfs));
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
	
	public void play1() {
		
	}
	
	public void decideWhatToPlay(Player p) {
		p.decideWhatToPlay();
	}
}
