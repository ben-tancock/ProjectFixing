package model;

import java.util.List;

public class ArtificialIntelligence1 extends Player implements AIStrategies {

	@Override
	public boolean doIParticipateInTournament(Players p, Tournament t) {
		//if(Any player can win/evolve by winning tournament) {
		//  if(Another player that can win/evolve is in tournament
		//     OR I can win/evolve myself) {
		//		play strongest hand possible;
		//	} else {
		//		play weapons that I have 2 or more of;
		//  }
		//	return true;
	    //}
		return false;
	}

	@Override
	public boolean doISponsorAQuest(Players p, Quest q) {
		//if(Someone else could win/evolve by winning quest) {
		//	return false;
		//} else if(There are enough foes in hand(-1 if they have test) and they have increasing battle points) {
		//  q.setup1(); {
		//		setup last stage to be atleast 50
		//		setup second last stage to be a test (if possible)
		//		starting with the next stage toward first:
		//			pick strongest foe
		//			set with one weapon that is a duplicate and respect order of BP
		//  }
		//	return true;
		//}
		return false;
	}

	@Override
	public boolean doIParticipantInQuest(Players p, Quest q) {
		//q.play1(this)?
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

}
