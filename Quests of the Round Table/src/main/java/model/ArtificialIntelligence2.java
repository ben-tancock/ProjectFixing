package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import control.QuestHandler;

@JsonDeserialize(as = ArtificialIntelligence2.class)
public class ArtificialIntelligence2 extends Player implements AIStrategies {

	/**
	 * Serial UID
	 */
	private static final long serialVersionUID = -8305381973346570592L;

	@Override
	public boolean doIParticipateInTournament(Players p, Tournament t) {
		Players participants = new Players();
		participants.getPlayers().addAll(t.getParticipants());
		//adding the partcipating AI
		participants.getPlayers().add(this);
		decideWhatToPlay();
		return true;
	}

	@Override
	public boolean doISponsorAQuest(Players p, Quest q) {
		if(checkWinOrEvolve(p, p.getPlayers().size())) {
			return false;
		}else if(checkIfEnoughFoes(q)) {
			setUp2(q);
			return true;
		}
		return false;
	
	}

	@Override
	public boolean doIParticipantInQuest(Players p, Quest q) {
		if(incrementByTenPerStage(q) && twoFoesUnderXBP(25,q)) {
			play2(q);
			return true;
		}
		//q.play2(this)?
		//if(I can increase my BP by 10 each stage AND I have atleast 2 foes of less than 25 BP) {
		//  play2() {
		//		if(currentStage is a Test) {
		//			nextBid();
		//			if(Test is won) {
		//				discardAfterWinningTest();
		//			}
		//      } else {
		//			
		//          if(Stage is last stage) {
		//				play strongest valid combination.
		//		    } else {
		//				play each stage with an increment of +10, using amour first, then ally, then weapon(s)
		//			}
		//		}
		//	}
		//	return true;	
		//}
		return false;
	}

	@Override
	public int nextBid(Quest q) {
		//if(foes of less than 25 points are in hand AND first round) {
		//	return numFoesWithLessThan25Points;
		//}
		//if(second round AND foes of less than 25 points are in hand) {
		//	return numFoesWithLessThan25Points + numDuplicateCards
		//}
		return 0;
	}

	@Override
	public List<Adventure> discardAfterWinningTest() {
		//if(first round) {
		//	discard foes less than 25 points in hand
		//}
		//if(second round) {
		//	discard round 1 foes and duplicate cards
		//}
		return null;
	}

	public void setUp2(Quest q) {
		Stage lastStage = setUpStageToBeAtLeast40(q);
		int restOfStages = q.getNumStages();
		int currentBp = 0;
		
		//if last stage could not be made with 40 bp
		if(lastStage == null) {
			nextHighestFoeBPWithDupWeapon(currentBp, q.getSpecialFoes());
		}
		currentBp = lastStage.getBattlePoints();
		restOfStages -= 1;
		
		Stage secondLastStage = null;
		for(Adventure a : getHand()) {
			if(a instanceof Test) {
				secondLastStage = new Stage((Test) a);
				getHand().remove(a);
				//break; // only taking a test out if found
			}
		}
		if (secondLastStage != null) {
			restOfStages -= 1;
		}
		//setting up stages
		List<Stage> restOfStagesList = new ArrayList<>();
		for(int i = 0; i < restOfStages; i++) {
			Stage nextLastStage = weakestFoeWithNoWeapons(q);
			currentBp = nextLastStage.getBattlePoints();
			restOfStagesList.add(nextLastStage);
		}
		if(secondLastStage != null) {
			restOfStagesList.add(secondLastStage);
		}
		restOfStagesList.add(lastStage);
		for(Stage s : restOfStagesList) {
			q.addStage(s);
		}
	}
	
	public Stage weakestFoeWithNoWeapons(Quest questCard) {
		int indexOfCardWithLowestBp;
		ArrayList<Foe> foes = new ArrayList<Foe>();
		//int smallestBp = getHand().get(0).getBattlePoints();
		for(Adventure a : getHand()) {
			if(a instanceof Foe) {
				foes.add((Foe)a);
			}
		}
		Foe weakestFoe = Collections.min(foes,(c1, c2)-> c2.getFoeBP(questCard.getSpecialFoes()));
		Stage foeStage = new Stage(weakestFoe, new ArrayList<Weapon>());
		//Collections.sort(, (c1, c2)-> c2.getBattlePoint());
		//indexOfCardWithLowestBp = getHand().indexOf(Collections.min(getHand()));
		
		return foeStage;
	}
	
	public Stage nextHighestFoeBPWithDupWeapon(int BPtoCompare, String spfs) {
		return null;
	}
	
	public Stage setUpStageToBeAtLeast40(Quest questCard) {
		int stageBP = 0;
		
		Foe f = getFoeWithHighestBP(questCard.getSpecialFoes());
		
		//weapons with highest battlepoint
		ArrayList <Weapon> weapons = new ArrayList<>();
		String currentWeaponName = "";
		int maxIter = getHand().size();
		int iter = 0;
		while(stageBP < 40 && iter < maxIter) {
			Weapon w = getStrongestWeapon(currentWeaponName);
			currentWeaponName = w.getName();
			weapons.add(w);
			stageBP =+ w.getBattlePoints();
			iter++;
		}
		if(stageBP >= 40) {
			getHand().remove(f);
			getHand().removeAll(weapons);
			return new Stage(f, weapons);
		}else {
			return null;
		}
	}
	
	
	public boolean incrementByTenPerStage(Quest q) {
		int bpCounter = 1;
		ArrayList<Adventure>cardsWithTenBp = new ArrayList<Adventure>();
		for(Adventure a : getHand()) {
			if(a.getBattlePoints() % 10 == 0) {
				cardsWithTenBp.add(a);
				//bpCounter++;
			}
		}
		Collections.sort(cardsWithTenBp, (c1, c2)-> c2.getBattlePoints());
		for(Adventure a : cardsWithTenBp) {
			if(a.getBattlePoints() % 10 != bpCounter) {
				cardsWithTenBp.remove(a);
			}
			bpCounter++;
		}
		if(q.getNumStages() == cardsWithTenBp.size()) {
			return true;
		}
		return false;
	}
	
	public void play2(Quest q) {
		QuestHandler questHandler = QuestHandler.getInstance();
		
		if(q.getStages().get(questHandler.getCurrentStage()).getTest() != null) {
			//nextBid
			//last stage
		}else if(questHandler.getCurrentStage() == q.getNumStages() - 1) {
			ArrayList<Adventure>strongestValidCombination = getStrongestHand(IncreasingOrDecreasing.DECREASING);
			for(Adventure a : strongestValidCombination) {
				Players.notifyListeners("card played", this, a);
			}
		}else {
			incrementOf10WithAmourFirst();
			for(Adventure a : incrementOf10WithAmourFirst()) {
				Players.notifyListeners("card played", this, a);
			}
		}
	}
	
	
	public ArrayList<Adventure> incrementOf10WithAmourFirst(){
		ArrayList<Adventure> increasingCards = new ArrayList<Adventure>();
		int bpCounter = 1;
		for(Adventure a : getHand()) {
			if(a instanceof Amour && (a.getBattlePoints() / 10 == bpCounter) && 
					(!(increasingCards.contains(a)))) {
				increasingCards.add(a);
				bpCounter++;
			}
		}
		for (Adventure a : getHand()) {
			if(a instanceof Ally && (a.getBattlePoints() / 10 == bpCounter) && 
					(!(increasingCards.contains(a)))) {
				increasingCards.add(a);
				bpCounter++;
			}
		}
		for(Adventure a : getHand()) {
			if(a instanceof Weapon && (a.getBattlePoints() / 10 == bpCounter)) {
				increasingCards.add(a);
				bpCounter++;
			}
		}
		return increasingCards;
	}
	
	//foes with less than 25 battle points
	public boolean foesWithlessThan25Points(Quest q) {
		for(Adventure a : getHand()) {
			if(a instanceof Foe && ((Foe) a).getFoeBP(q.getSpecialFoes())< 25) {
				return true;
			}
		}
		return false;
	}
	
	
	//finding duplicate foes
	public ArrayList<Foe> findDuplicateFoes() {
		ArrayList<Foe> duplicateFoes = new ArrayList<Foe>();
		Set<Foe>testFoes = new HashSet<>();
		for(Adventure a : getHand()) {
			if(a instanceof Foe && !testFoes.add((Foe) a)) {
				duplicateFoes.add((Foe) a);
			}
		}
		
		return duplicateFoes;
	}
	
	public ArrayList<Adventure> decideWhatToPlay() {
		ArrayList<Adventure> cardsToPlay = new ArrayList<>();
		int aipoints = 0;
		for(Adventure a : getStrongestHand(IncreasingOrDecreasing.INCREASING)) {
			while (aipoints < 50) {
				cardsToPlay.add(a);
				aipoints += a.getBattlePoints();
			}
		}
		return cardsToPlay;
	}
}
