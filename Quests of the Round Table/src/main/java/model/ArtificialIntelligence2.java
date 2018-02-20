package model;

import java.util.List;

public class ArtificialIntelligence2 extends Player implements AIStrategies {

	@Override
	public boolean doIParticipateInTournament(Players p, Tournament t) {
		//getTo50BPWithAsFewCardsAsPossible()
		return true;
	}

	@Override
	public boolean doISponsorAQuest(Players p, Quest q) {
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
		return false;
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

}
