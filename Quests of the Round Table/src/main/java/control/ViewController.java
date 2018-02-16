package control;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Adventure;
import model.AdventureDeck;
import model.Player;
import model.Story;
import model.StoryDeck;
import view.DeckView;
import view.View;

public class ViewController extends Application implements PropertyChangeListener{
	View view = new View();
	Player players = new Player();
	AdventureDeck adventureDeck = new AdventureDeck();
	StoryDeck storyDeck = new StoryDeck();
	DeckView deckView = new DeckView();
	Rectangle storyDeckRectangle = view.rectangle();
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		view.start(primaryStage);
		
		
		view.rulesButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				BorderPane border = new BorderPane();//view.getGameBorders();
				border.setCenter(view.rulesBox());
				Stage rulesStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
				//rulesStage.setScene(view.rulesBox());
				rulesStage.getScene().setRoot(border);
				rulesStage.show();
				
				
			}
			
		});
		
		view.twoPlayerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				HBox player1Cards = view.getPlayerSpace();
				HBox player2Cards = view.getsecondPlayerSpace();
				
				PlayerSetup(2);
				adventureDeck.shuffle();
				player1Cards.getChildren().addAll(deckView.playerRank(), playerCards(0));			
				
				//player2Cards
				adventureDeck.shuffle();
				player2Cards.getChildren().addAll(deckView.playerRank(), playerCards(1));
				
				BorderPane border = view.getGameBorders();
				border.setBottom(player1Cards);
				border.setTop(player2Cards);
				//border.setCenter(view.storyDeck());
				border.setCenter(storyDeckCards());
		
				BorderPane.setAlignment(storyDeckCards(), Pos.CENTER_RIGHT);
				
				//Scene twoPlayerScene = new Scene(border, 1120, 700,Color.AQUA);
				Stage twoPlayerStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
				twoPlayerStage.setScene(view.getGameTable());
				twoPlayerStage.getScene().setRoot(border);
				twoPlayerStage.show();
			}
			
		});
		view.threePlayerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				BorderPane border = new BorderPane();
				HBox allPlayer1Cards = view.getPlayerSpace();
				HBox player2Cards = view.getsecondPlayerSpace();
				HBox player3Cards = new HBox();
				HBox playerRank = deckView.playerRank();
				
				//adding 3 players
				PlayerSetup(3);
				
				//shuffle cards
				adventureDeck.shuffle();
				
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
				border.setCenter(storyDeckCards());
				
				//Scene threePlayerScene = new Scene(border, 1120, 700,Color.AQUA);
				Stage threePlayerStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
				threePlayerStage.setScene(view.getGameTable());
				threePlayerStage.getScene().setRoot(border);
				threePlayerStage.show();
			}
			
		});
		
		view.fourPlayerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				BorderPane border = new BorderPane();
				HBox allPlayer1Cards = view.getPlayerSpace();
				HBox player2Cards = view.getsecondPlayerSpace();
				HBox player3Cards = view.getThirdPlayerSpace();
				HBox player4Cards = view.getFourthPlayer();

				//adding 4 players
				PlayerSetup(4);
				
				//shuffling the cards
				adventureDeck.shuffle();
				
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
				border.setCenter(storyDeckCards());
				
				//Scene fourPlayerScene = new Scene(border, 1120, 800,Color.AQUA);
				Stage fourPlayerStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
				fourPlayerStage.setScene(view.getGameTable());
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
	
	//story deck
	public HBox storyDeckCards() {
		HBox storyCards = view.storyCards();
		storyDeck.shuffle();
		for(Story s: storyDeck) {
			System.out.println(s.getName() + ".jpg");
			Image card = new Image("/playingCards/" + s.getName() + ".jpg", 75, 100, true, true);
			System.out.println(s.getName() + ".jpg");
			ImageView theCard = new ImageView(card);
			theCard.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					
				}
				
			});
			storyCards.getChildren().add(theCard);
		}
		return storyCards;
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
