package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.Adventure;
import model.AdventureDeck;

public class DeckView extends HBox {
	AdventureDeck adventureDeck;
	public DeckView() {
		adventureDeck  = new AdventureDeck();
	}
	
	public HBox addHbox() {
		HBox hbox = new HBox();
		//hbox.setPadding(new Insets(20, 15, 20, 15));
		hbox.setSpacing(5);
		//hbox.setStyle("-fx-background-color : #336699;");
		adventureDeck.shuffle();
		for(Adventure a : adventureDeck) {
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
	
	public void makePlayer1() {
		
	}

}
