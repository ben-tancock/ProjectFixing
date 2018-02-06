package view;

import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Adventure;
import model.AdventureDeck;
import model.Player;

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
		Player players = new Player();
		players.add();
		players.persons.get(0).drawCard(12, adventureDeck);
		
		for(Adventure a : players.persons.get(0).getHand()) {
			//System.out.println(a.getName());
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
	
	
	/*
	 * BorderPane border = new BorderPane();
		HBox hbox1 = deckView.playerRank();
		HBox hbox2 = deckView.addHbox();
		HBox player1Cards = new HBox(5);
		player1Cards.getChildren().addAll(hbox1, hbox2);
		border.setBottom(player1Cards);
		//border.setAlignment(player1Cards, Pos.BOTTOM_CENTER);
		
		//LOADING VBOX FOR VERTICAL PLAYER
		VBox player2rank = verticalView.playerRank();
		VBox vbox1 = verticalView.addVBox();
		VBox player2Cards = new VBox(5);
		player2Cards.getChildren().addAll(player2rank, vbox1);
		border.setLeft(player2Cards);
		
		//Loading top component
		HBox player3 = deckView.playerRank();
		HBox hbox3 = deckView.addHbox();
		HBox player3Cards = new HBox(5);
		player3Cards.getChildren().addAll(player3, hbox3);
		border.setTop(player3Cards);
		
		//LOADING VBOX FOR VERTICAL PLAYER
		VBox player4rank = verticalView.playerRank();
		VBox vbox4 = verticalView.addVBox();
		VBox player4Cards = new VBox(5);
		player4Cards.getChildren().addAll(player4rank, vbox4);
		border.setRight(player4Cards);
		
	 */
}
