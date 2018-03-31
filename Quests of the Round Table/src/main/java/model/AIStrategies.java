package model;

import java.util.List;

public interface AIStrategies {
	
	//Deciding on tournament participation.
	public boolean doIParticipateInTournament(Players p, Tournament t);
	
	//Deciding on sponsoring a quest.
	public boolean doISponsorAQuest(Players p, Quest q);
	
	//Deciding on quest participation.
	public boolean doIParticipantInQuest(Players p, Quest q);
	
	//Deciding on how much to bid.
	public int nextBid(Quest q);
	
	//Deciding on which cards to discard if test is won.
	public List<Adventure> discardAfterWinningTest();
}
