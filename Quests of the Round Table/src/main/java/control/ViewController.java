package control;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Adventure;
import model.AdventureDeck;
import model.Player;
import view.DeckView;
import view.View;

public class ViewController extends Application implements PropertyChangeListener{
	View view = new View();
	Player players = new Player();
	AdventureDeck adventureDeck = new AdventureDeck();
	DeckView deckView = new DeckView();
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		view.start(primaryStage);
		view.rulesButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("THESE ARE THE RULES OF THE GAME");
			}
			
		});
		
		view.twoPlayerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				HBox player1Cards = new HBox();
				HBox player2Cards = new HBox();
				
				PlayerSetup(2);
				player1Cards.getChildren().addAll(deckView.playerRank(), playerCards(0));			
				
				//player2Cards
				player2Cards.getChildren().addAll(deckView.playerRank(), playerCards(1));
				
				BorderPane border = new BorderPane();
				border.setBottom(player1Cards);
				border.setTop(player2Cards);
				border.setCenter(view.storyDeck());
				
				Scene twoPlayerScene = new Scene(border, 1120, 700,Color.AQUA);
				Stage twoPlayerStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
				twoPlayerStage.setScene(twoPlayerScene);
				twoPlayerStage.getScene().setRoot(border);
				twoPlayerStage.show();
			}
			
		});
		view.threePlayerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				BorderPane border = new BorderPane();
				HBox allPlayer1Cards = new HBox();
				HBox player2Cards = new HBox();
				HBox player3Cards = new HBox();
				HBox playerRank = deckView.playerRank();
				
				//adding 3 players
				PlayerSetup(3);
				
				//player1cards
				allPlayer1Cards.getChildren().addAll(deckView.playerRank(), playerCards(0));			
				
				//player2Cards
				player2Cards.getChildren().addAll(deckView.playerRank(), playerCards(1));
				
				//player3Cards
				player3Cards.getChildren().addAll(playerRank, verticalPlayerCards(2));
			
				
				//setting the scene and window
				border.setTop(allPlayer1Cards);
				border.setBottom(player2Cards);
				border.setRight(player3Cards);
				border.setCenter(view.storyDeck());
				Scene threePlayerScene = new Scene(border, 1120, 700,Color.AQUA);
				Stage threePlayerStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
				threePlayerStage.setScene(threePlayerScene);
				threePlayerStage.getScene().setRoot(border);
				threePlayerStage.show();
			}
			
		});
		
		view.fourPlayerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				BorderPane border = new BorderPane();
				HBox allPlayer1Cards = new HBox();
				HBox player2Cards = new HBox();
				HBox player3Cards = new HBox();
				HBox player4Cards = new HBox();

				//adding 4 players
				PlayerSetup(4);
				
				//player1 cards
				allPlayer1Cards.getChildren().addAll(deckView.playerRank(), playerCards(0));			
				border.setTop(allPlayer1Cards);
				//player2 Cards
				player2Cards.getChildren().addAll(deckView.playerRank(), playerCards(1));
				border.setBottom(player2Cards);
				//player3 Cards
				player3Cards.getChildren().addAll(deckView.playerRank(), verticalPlayerCards(2));
				border.setRight(player3Cards);
				//player4 Cards
				player4Cards.getChildren().addAll(deckView.playerRank(), verticalPlayerCards(3));
				border.setLeft(player4Cards);
				
				//setting the scene and window
				border.setCenter(view.storyDeck());
				Scene fourPlayerScene = new Scene(border, 1120, 800,Color.AQUA);
				Stage fourPlayerStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
				fourPlayerStage.setScene(fourPlayerScene);
				fourPlayerStage.getScene().setRoot(border);
				fourPlayerStage.show();
			}
			
		});
		
		
	}
	
	//setting up the number of players
	public void PlayerSetup(int playerNumber) {
		for(int i = 0; i< playerNumber ; i++) {
			players.add();
		}
	}
	
	public HBox playerCards(int playerPosition) {
		HBox playerCards = new HBox(-50);
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
	
	public VBox verticalPlayerCards(int playerPosition) {
		VBox playerCards = new VBox(-80);
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

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}


}
