package view;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.Adventure;
import model.AdventureDeck;
import model.Player;

public class DeckView extends HBox {
	Player player = new Player();
	Player players = new Player();
	AdventureDeck adventureDeck;
	public DeckView() {
		adventureDeck  = new AdventureDeck();
	}
	
	//playersCards
	public HBox playerCards(int playerPosition) {
		HBox playerCards = new HBox(-50);
		
		adventureDeck.shuffle();
		players.persons.get(playerPosition).drawCard(12, adventureDeck);
		
		for(Adventure a : players.persons.get(playerPosition).getHand()) {
			Image card = new Image("/playingCards/" + a.getName() + ".jpg", 75, 100, true, true);
			ImageView theCard = new ImageView(card);
			theCard.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					System.out.println( "This is a " + a.getName());
				}
				
			});
			playerCards.getChildren().add(theCard);
		
		}
		return playerCards;
	}
	
	//playerRank
	public HBox playerRank() {
		HBox hbox = new HBox();
		Image card = new Image("/playingCards/squire.jpg", 105, 140, true, true);
		ImageView theCard = new ImageView(card);
		hbox.getChildren().add(theCard);
		return hbox;
	}
	
	public void makePlayer1() {
		
	}

}
