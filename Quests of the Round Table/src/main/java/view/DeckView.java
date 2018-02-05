package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.Adventure;
import model.AdventureDeck;
import model.Player;
import model.Player.Person;

public class DeckView extends HBox {
	Player player = new Player();
	AdventureDeck adventureDeck;
	public DeckView() {
		adventureDeck  = new AdventureDeck();
	}
	
	public HBox addHbox() {
		HBox hbox = new HBox();
		//hbox.setPadding(new Insets(20, 15, 20, 15));
		hbox.setSpacing(-50);
		//hbox.setStyle("-fx-background-color : #336699;");
		adventureDeck.shuffle();
		player.add();
		player.persons.get(0).drawCard(12, adventureDeck);
		
		for(Adventure a : player.persons.get(0).getHand()) {
			System.out.println(a.getName());
			Image card = new Image("/playingCards/" + a.getName() + ".jpg", 75, 100, true, true);
			ImageView theCard = new ImageView(card);
			theCard.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					System.out.println( "This is a " + a.getName());
				}
				
			});
			hbox.getChildren().add(theCard);
			
		}
		return hbox;
	}
	
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
