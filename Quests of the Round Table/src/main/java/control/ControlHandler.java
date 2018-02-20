package control;

import model.Player;
//Possible abstract class for notifying the controllers?
public abstract class ControlHandler {

	public void onCardOverflow(Player p) {
		
	}
	
	public void onAdventureCardDraw(Player p) {
		
	}
	
	public void onAdventureCardDiscard(Player p) {
		
	}
	
	public void onStoryCardDraw() {
		
	}
	
	public void onStoryCardDiscard() {
		
	}
	
	public void onPlayerVictory(Player p) {
		
	}
	
	public void onMordredPlayed(Player perp, Player targ) {
		
	}
	
	public void onKingsRecognition() {
		
	}
}
