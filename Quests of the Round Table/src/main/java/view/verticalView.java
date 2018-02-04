package view;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Adventure;
import model.AdventureDeck;
import model.Player.Person;

public class verticalView extends VBox {
	AdventureDeck adventureDeck = new AdventureDeck();
	public verticalView() {
		
	}
	
	public VBox addVBox() {
		VBox vBox = new VBox();
		//vBox.setPadding(new Insets(20, 15, 20, 15));
		vBox.setSpacing(-80);
		//vBox.setStyle("-fx-background-color : #336699;");
		adventureDeck.shuffle();
		Person player = new Person();
		player.drawCard(12, adventureDeck);
		
		for(Adventure a : player.getHand()) {
			System.out.println(a.getName());
			Image card = new Image("/playingCards/" + a.getName() + ".jpg", 75, 100, true, true);
			vBox.getChildren().add(new ImageView(card));
		}
		return vBox;
	}
	
	public VBox playerRank() {
		VBox Vbox = new VBox();
		Image card = new Image("/playingCards/squire.jpg", 105, 140, true, true);
		ImageView theCard = new ImageView(card);
		Vbox.getChildren().add(theCard);
		return Vbox;
	}
}
