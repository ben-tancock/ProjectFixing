package control;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import model.Adventure;
import model.Event;
import model.Player;
import model.Story;
//Possible abstract class for notifying the controllers?
public abstract class ControlHandler {

	public void onCardOverflow(Player p) {
		
	}
	
	public void onInvalidCardPlayed(Player p) {
		
	}
	
	public void onQuestCardDraw(Player p) {
		
	}
	
	public void onTournamentCardDraw(Player p) {
		
	}
	
	public void onEventCardDraw(Player p) {
		
	}
	
	public void onRankSet(Player p) {
		
	}
	
	public void onAdventureCardPlayed(Player p, Adventure card, MouseEvent event) {
		
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
	
	public void onStageCardPicked(Player p, Adventure card) {
		
	}
	
	public void onStageWeaponsPicked(Player p, ArrayList<Adventure> cards) {
		
	}
}
