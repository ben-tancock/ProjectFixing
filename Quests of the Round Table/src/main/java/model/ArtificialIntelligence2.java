package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ArtificialIntelligence2 extends Player implements AIStrategies {

	@Override
	public boolean doIParticipateInTournament(Players p, Tournament t) {
		//getTo50BPWithAsFewCardsAsPossible()
		
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
	/*	if(checkWinOrEvolve(p, p.getPlayers().size())){
			Players participants = new Players();
			participants.getPlayers().addAll(q.getParticipants());
			//adding AI to participants
			participants.getPlayers().add(this);
			if(checkWinOrEvolve(p, p.getPlayers().size())) {
				List <Adventure>strongestHand = getStrongestHand();
				for(Adventure a : strongestHand) {
					Players.notifyListeners("card played", this, a);
				}
			}else {
				Set<Weapon>duplicateWeapons = findDuplicateWeapons();
				for(Weapon w : duplicateWeapons) {
					Players.notifyListeners("card played", this, w);
				}
			}
			return true;
		}
		//if(Someone else could win/evolve by winning quest) {
		//	return false;
		//} else if(There are enough foes in hand(-1 if they have test) and they have increasing battle points) {
		//  q.setup2(); {
		//		last stage is atleast 40
		//		second last stage is a test
		//		setup stages in valid order to have weakest foes without weapons
		//  }
		//	return true;
		//}
		return false;*/
	}

	@Override
	public boolean doIParticipantInQuest(Players p, Quest q) {
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
	public int nextBid() {
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
			//nextHighestFoeBPWithDupWeapon(currentBp, q.getSpecialFoes());
		}
		currentBp = lastStage.getBattlePoints();
		restOfStages -= 1;
		
		Stage secondLastStage = null;
		for(Adventure a : getHand()) {
			if(a instanceof Test) {
				secondLastStage = new Stage((Test) a);
				getHand().remove(a);
				break; // only taking a test out if found
			}
		}
		if (secondLastStage != null) {
			restOfStages -= 1;
		}
		//setting up stages
		List<Stage> restOfStagesList = new ArrayList<>();
		for(int i = 0; i < restOfStages; i++) {
			
		}
	}
	
	public Stage weakestFoeWithNoWeapons(Quest questCard) {
		int indexOfCardWithLowestBp;
		ArrayList<Foe> foes = new ArrayList<Foe>();
		int smallestBp = getHand().get(0).getBattlePoints();
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
		if(stageBP >= 50) {
			getHand().remove(f);
			getHand().removeAll(weapons);
			return new Stage(f, weapons);
		}else {
			return null;
		}
	}
	
	
	public ArrayList decideWhatToPlay() {
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
