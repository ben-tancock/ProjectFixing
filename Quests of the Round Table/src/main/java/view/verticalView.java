package view;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Adventure;
import model.AdventureDeck;

public class verticalView extends VBox {
	AdventureDeck adventureDeck = new AdventureDeck();
	public verticalView() {
		
	}
	
	public VBox addVBox() {
		VBox vBox = new VBox();
		//vBox.setPadding(new Insets(20, 15, 20, 15));
		vBox.setSpacing(5);
		//vBox.setStyle("-fx-background-color : #336699;");
		adventureDeck.shuffle();
		for(Adventure a : adventureDeck) {
			System.out.println(a.getName());
			Image card = new Image("/playingCards/" + a.getName() + ".jpg", 75, 100, true, true);
			vBox.getChildren().add(new ImageView(card));
		}
		return vBox;
	}
}
