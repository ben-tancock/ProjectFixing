package view;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import model.Player;
import model.Player.Person;

public class View extends Application {
	
	AdventureDeck adventureDeck = new AdventureDeck();
	DeckView deckView = new DeckView();
	verticalView verticalView = new verticalView();
	ArrayList<Adventure> adventureCards = new ArrayList<>();
	int numberOfPlayers = 0;
	public List<Person> persons = new ArrayList<Person>();
	Player players = new Player();
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		adventureDeck.shuffle();
		HBox startPane = new HBox(50);
		
		//rules button
		Button rules = new Button("RULES OF THE GAME");
		rules.setMaxSize(200, 100);
		rules.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				System.out.println("these are the rules");
			}
			
		});
		
		//2 player button
		Button twoPlayerButton = new Button("TWO PLAYER QUEST");
		twoPlayerButton.setMaxSize(200, 100);
		twoPlayerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				twoPlayerSetUp();
				HBox hBox1 = new HBox();
				HBox hBox2 = new HBox();
				HBox hbox3 = new HBox();
				HBox secondPlayerCards = new HBox();
				
				players.persons.get(0).drawCard(12, adventureDeck);
				
				for(Adventure a : players.persons.get(0).getHand()) {
					Image card = new Image("/playingCards/" + a.getName() + ".jpg", 75, 100, true, true);
					ImageView theCard = new ImageView(card);
					theCard.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent event) {
							// TODO Auto-generated method stub
							System.out.println( "This is a " + a.getName());
						}
						
					});
					hBox1.getChildren().add(theCard);
				
				}
				hBox2.getChildren().addAll(deckView.playerRank(), hBox1);
				
				players.persons.get(1).drawCard(12, adventureDeck);
				for(Adventure a : players.persons.get(1).getHand()) {
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
					secondPlayerCards.getChildren().add(theCard);
					
				}
				hbox3.getChildren().addAll(deckView.playerRank(),secondPlayerCards);
				
				BorderPane border = new BorderPane();
				border.setBottom(hBox2);
				border.setTop(hbox3);
				
				Scene twoPlayerScene = new Scene(border, 1120, 700,Color.AQUA);
				Stage twoPlayerStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
				twoPlayerStage.setScene(twoPlayerScene);
				twoPlayerStage.getScene().setRoot(border);
				twoPlayerStage.show();
			}
			
		});
		
		Button threePlayerButton = new Button("THREE PLAYER QUEST");
		threePlayerButton.setMaxSize(200, 100);
		threePlayerButton.setOnAction(new EventHandler<ActionEvent>() {

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
				allPlayer1Cards.getChildren().addAll(playerRank, playerCards(0));			
				border.setTop(allPlayer1Cards);
				//player2Cards
				player2Cards.getChildren().addAll(playerRank, playerCards(1));
				border.setBottom(player2Cards);
				//player3Cards
				player3Cards.getChildren().addAll(playerRank, verticalPlayerCards(2));
				border.setRight(player3Cards);
				
				//setting the scene and window
				Scene threePlayerScene = new Scene(border, 1120, 700,Color.AQUA);
				Stage threePlayerStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
				threePlayerStage.setScene(threePlayerScene);
				threePlayerStage.getScene().setRoot(border);
				threePlayerStage.show();
				
				
			}
			
		});
		
		Button fourPlayerButton = new Button("FOUR PLAYER QUEST");
		fourPlayerButton.setMaxSize(200, 100);
		fourPlayerButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				BorderPane border = new BorderPane();
				HBox allPlayer1Cards = new HBox();
				HBox player2Cards = new HBox();
				HBox player3Cards = new HBox();
				HBox player4Cards = new HBox();
				HBox playerRank = deckView.playerRank();
				
				//adding 3 players
				PlayerSetup(4);
				
				//player1 cards
				allPlayer1Cards.getChildren().addAll(playerRank, playerCards(0));			
				border.setTop(allPlayer1Cards);
				//player2 Cards
				player2Cards.getChildren().addAll(playerRank, playerCards(1));
				border.setBottom(player2Cards);
				//player3 Cards
				player3Cards.getChildren().addAll(playerRank, verticalPlayerCards(2));
				border.setRight(player3Cards);
				//player4 Cards
				player4Cards.getChildren().addAll(playerRank, verticalPlayerCards(3));
				border.setLeft(player4Cards);
				
				//setting the scene and window
				Scene fourPlayerScene = new Scene(border, 1120, 800,Color.AQUA);
				Stage fourPlayerStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
				fourPlayerStage.setScene(fourPlayerScene);
				fourPlayerStage.getScene().setRoot(border);
				fourPlayerStage.show();
			}
			
		});
		
		startPane.getChildren().addAll(rules, twoPlayerButton, threePlayerButton, fourPlayerButton);
		
		Scene scene = new Scene(startPane, 1120, 700,Color.GRAY);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Quest of the Round Table");
		primaryStage.show();
	}
	
	public void twoPlayerSetUp() {
		numberOfPlayers = 2;
		HBox hbox = new HBox();
		hbox.setSpacing(-50);
		adventureDeck.shuffle();
		for (int i = 0; i < numberOfPlayers ; i++) {
			players.add();
		}
			
	}
	
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
}
