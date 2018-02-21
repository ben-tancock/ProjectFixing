package control;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import model.Player;
import model.Story;
//Possible abstract class for notifying the controllers?
public abstract class ControlHandler {

	public void onCardOverflow(Player p) {
		
	}
	
	public void onAdventureCardDraw(Player p) {
		
	}
	
	public void onAdventureCardDiscard(Player p) {
		
	}
	
	public void onStoryCardDraw(MouseEvent event) {
		
	}
	
	public void onStoryCardDiscard() {
		
	}
	
	public void onStoryDeckEmpty() {
		
	}
	
	public void onPlayerVictory(Player p) {
		
	}
	
	public void onMordredPlayed(Player perp, Player targ) {
		
	}
	
	public void onKingsRecognition() {
		
	}
}
