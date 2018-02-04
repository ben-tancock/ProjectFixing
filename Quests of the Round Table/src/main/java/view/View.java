package view;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Adventure;
import model.AdventureDeck;

public class View extends Application {
	
	AdventureDeck adventureDeck = new AdventureDeck();
	DeckView deckView = new DeckView();
	verticalView verticalView = new verticalView();
	ArrayList<Adventure> adventureCards = new ArrayList<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		//adventureDeck.shuffle();
		BorderPane border = new BorderPane();
		HBox hbox1 = deckView.playerRank();
		HBox hbox2 = deckView.addHbox();
		HBox player1Cards = new HBox(5);
		player1Cards.getChildren().addAll(hbox1, hbox2);
		border.setBottom(player1Cards);
		border.setAlignment(player1Cards, Pos.BOTTOM_CENTER);
		
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
		
		
		Scene scene = new Scene(border, 1120, 700,Color.GRAY);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Quest of the Round Table");
		primaryStage.show();
	}

}
