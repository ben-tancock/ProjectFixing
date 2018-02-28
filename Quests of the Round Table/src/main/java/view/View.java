package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import control.ControlHandler;
import control.PlayGame;
import control.QuestHandler;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.effect.BoxBlur;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

// new stuff Ben added
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
// ------------------

import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Ally;
import model.Amour;
import model.Card;
import model.CardStates;
import model.Foe;
import model.Player;
import model.Players;
import model.Quest;
import model.Story;
import model.StoryDeck;
import model.StoryDiscard;
import model.Test;
import model.Weapon;
import control.PlayGame.PlayGameControlHandler;
import control.QuestHandler.QuestControlHandler;

public class View extends Application {
	
	private BorderPane border;
	
	// NEW THING I ADDED V WEEKY DEEKY
	public GridPane grid;// = new GridPane();
	// -------------------------------
	
	AdventureDeck adventureDeck = new AdventureDeck();
	private HBox playerSpace;
	private HBox secondPlayerSpace;
	private HBox thirdPlayerSpace;
	private HBox fourthPlayerSpace;
	private VBox verticalPlayerSpace;
	
	
	private Scene gameTable;
	DeckView deckView = new DeckView();
	verticalView verticalView = new verticalView();
	ArrayList<Adventure> adventureCards = new ArrayList<>();
	int numberOfPlayers = 0;
	public List<Player> persons = new ArrayList<Player>();
	Players players = new Players();
	
	//declare it outside the start method to be called in Update()
	private Stage twoPlayerStage, threePlayerStage, fourPlayerStage, primStage;
	private boolean firstTime;
	private int topCard;
	
	//Declare buttons on starting page
	public Button rulesButton;
	public Button twoPlayerButton;
	public Button threePlayerButton;
	public Button fourPlayerButton;
	
	//Declare cards
	private HBox player1Cards;
	private HBox player2Cards;
	private HBox player3Cards;
	private VBox player4Cards;
	
	private static List<ControlHandler> listeners = new ArrayList<ControlHandler>();
	
	public View() {
		border = new BorderPane();
		rulesButton = new Button("RULES OF THE GAME");
		twoPlayerButton = new Button("TWO PLAYER QUEST");
		threePlayerButton = new Button("THREE PLAYER QUEST");
		fourPlayerButton  = new Button("FOUR PLAYER QUEST");
		playerSpace = new HBox();
		secondPlayerSpace = new HBox();
		verticalPlayerSpace = new VBox();
		thirdPlayerSpace = new HBox();
		fourthPlayerSpace = new HBox();
		
		
		
		firstTime = true;
		topCard = 0;
		
		gameTable = new Scene(border, 1120, 700,Color.AQUA);
		listeners.add(new PlayGameControlHandler());
		listeners.add(new QuestControlHandler());
	}
	//player1 
	public HBox getPlayerSpace() {
		return playerSpace;
	}
	//player 2
	public HBox getsecondPlayerSpace() {
		return secondPlayerSpace;
	}
	//player 3
	public HBox getThirdPlayerSpace() {
		return thirdPlayerSpace;
	}
	//player 4
	public HBox getFourthPlayer() {
		return fourthPlayerSpace;
	}
	
	public VBox getVerticalOplayer() {
		return verticalPlayerSpace;
	}
	
	public Scene getGameTable() {
		return gameTable;
	}
	
	public BorderPane getGameBorders() {
		return border;
	}
	
	public int getCurrentTopStoryCardIndex() {
		return topCard;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		VBox startPane = new VBox(50);
		primStage = primaryStage;
		
		//rules button
		rulesButton.setPrefSize(300, 100);
		//2 player button
		twoPlayerButton.setPrefSize(300, 100);
		//3 player button
		threePlayerButton.setPrefSize(300, 100);
		//4 player button
		fourPlayerButton.setPrefSize(300, 100);
		
		startPane.getChildren().addAll(rulesButton, twoPlayerButton, threePlayerButton, fourPlayerButton);
		startPane.setAlignment(Pos.CENTER);
		Scene scene = new Scene(startPane, 1220, 700,Color.BISQUE);
		primStage.setScene(scene);
		primStage.setTitle("Quest of the Round Table");
		primStage.show();
	}
	public HBox storyCards() {
		HBox storyCards = new HBox(-70.5);
		
		return storyCards;
	}
	
	
	//story deck with both story cards and the discard pile
	public VBox storyDeckCards() {
		VBox storyDeck = new VBox(-99);
		
		return storyDeck;
	}
	
	public StackPane rulesBox() {
		StackPane rulesPane = new StackPane();
		Rectangle rulesRectangle = new Rectangle(50, 50);
		Text rulesText = new Text ("THIS APPLICATION IS DEVELOPED BY BEN, JONATHAN AND PAUL \n"
				+ "RULES TO BE LOADED SOON");
		rulesText.setFont(new Font("Arial",30));;
		rulesRectangle.widthProperty().bind(rulesText.wrappingWidthProperty().add(10));
		rulesRectangle.setFill(Color.TRANSPARENT);
		
		rulesPane.getChildren().addAll(rulesRectangle, rulesText);
		
		
		// ADDED BY BEN TO TEST TOURNAMENT ASKING TO JOIN, COMMENT OUT IF NEEDED
		/*Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText("Look, an Information Dialog");
		alert.setContentText("I have a great message for you!");

		alert.showAndWait();*/
		// ----------------------------------------------------------------
		
		return rulesPane;
	}
	
	public StackPane storyDeck() {
		StackPane storyDeckPane = new StackPane();
		return storyDeckPane;
	}
	
	//need multiple of these for supporting different situations
	public void update(MouseEvent event, Players players, StoryDeck sDeck, StoryDiscard sDiscard, Quest quest) {
		if(players.getPlayers().size() == 2) {
			setupFor2Players(event, players, sDeck, sDiscard, quest);
		} else if (players.getPlayers().size() == 3) {
			setUpFor3Players(event, players,sDeck, sDiscard);
		} else {
			setUpFor4Players(event, players, sDeck, sDiscard);
		}
	}
	
	// SET UP FOR TWO PLAYERS ----------------------------------------------------------------------------------------------------------
		private void setupFor2Players(MouseEvent event, Players players, StoryDeck sDeck, StoryDiscard sDiscard, Quest quest) {
			player1Cards = new HBox();
			player2Cards = new HBox();
			HBox player1PlayingSurface = new HBox();
			HBox player2PlayingSurface = new HBox();
			HBox player1ShieldSurface = new HBox();
			HBox player2ShieldSurface = new HBox();
			HBox storyDeckSpace = new HBox();
			HBox questStageSpace = new HBox(-50);
			if(quest != null) {
				for(model.Stage stage : quest.getStages()) {
					HBox stageSpace = new HBox();
					List<Adventure> cards = new ArrayList<Adventure>();
					if(stage.getTest() != null) {
						cards.add(stage.getTest());
						stageSpace.getChildren().add(stageCards(cards));
					}
					if(stage.getFoe() != null) {
						cards.add(stage.getFoe());
						cards.addAll(stage.getFoe().getWeapons());
						stageSpace.getChildren().add(stageCards(cards));
					}
					questStageSpace.getChildren().add(stageSpace);
				}
				questStageSpace.setMinWidth(187.5);
			}
			
			player1Cards = playerCards(players.getPlayers().get(0), 0);	
			player1Cards.setMinWidth(350);
			player1PlayingSurface.getChildren().add(playedCards(players.getPlayers().get(0).getPlayingSurface(), 0));
			player1ShieldSurface.getChildren().add(shields(players.getPlayers().get(0), 0));
			player1ShieldSurface.setAlignment(Pos.BASELINE_CENTER);
			player1ShieldSurface.setMinWidth(337.5);
			//player2Cards
			player2Cards.getChildren().add(playerCards(players.getPlayers().get(1), 1));
			player1Cards.setMinWidth(350);
			player2PlayingSurface.getChildren().add(playedCards(players.getPlayers().get(1).getPlayingSurface(), 1));
			player2ShieldSurface.getChildren().add(shields(players.getPlayers().get(1), 1));
			player2ShieldSurface.setAlignment(Pos.BASELINE_CENTER);
			player2ShieldSurface.setMinHeight(140);
			player2ShieldSurface.setMinWidth(337.5);
			//Story deck space
			storyDeckSpace = storyDeckSpace(sDeck, sDiscard);
			storyDeckSpace.getChildren().add(questStageSpace);
			// WEEKY DEEKY THING BEN DID ----------
			//GridPane border = new GridPane();
			grid = new GridPane();
			// ------------------------------------
			
			grid.setVgap(23);
			grid.setHgap(0);
			grid.add(player1PlayingSurface, 2, 3);
			grid.add(player2PlayingSurface, 2, 1);
			grid.add(player2Cards, 2, 0);
			grid.add(player1Cards, 2, 4);
			grid.add(player1ShieldSurface, 1, 4);
			grid.add(player2ShieldSurface, 1, 0);
			grid.add(deckView.playerRank(players.getPlayers().get(0), 0), 0, 4);
			grid.add(deckView.playerRank(players.getPlayers().get(1), 1),0, 0);
			grid.add(storyDeckSpace, 2, 2);
			
			//grid.setGridLinesVisible(true);

			//BorderPane.setAlignment(storyDeckCards(), Pos.CENTER_RIGHT);
			
			//Scene twoPlayerScene = new Scene(border, 1120, 700,Color.AQUA);
			if(firstTime) {
				twoPlayerStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
				firstTime = false;
			}
			twoPlayerStage.setScene(new Scene(grid, 1120, 700,Color.AQUA));
			twoPlayerStage.getScene().setRoot(grid);
			twoPlayerStage.show();
		}
	// -----------------------------------------------------------------------------------------------------------------------
	
	public void setUpFor3Players(MouseEvent event, Players players, StoryDeck sDeck, StoryDiscard sDiscard) {
		player1Cards = new HBox();
		player2Cards = new HBox();
		player3Cards = new HBox();
		HBox player1PlayingSurface = new HBox();
		HBox player1ShieldSurface = new HBox();
		VBox player2PlayingSurface = new VBox();
		VBox player2ShieldSurface = new VBox();
		HBox player3PlayingSurface = new HBox();
		HBox player3ShieldSurface = new HBox();
		HBox storyDeckSpace = new HBox();
		HBox questStageSpace = new HBox(-50);
		
		player1Cards.getChildren().add(playerCards(players.getPlayers().get(0), 0));			
			
		//player2Cards
		player2Cards.getChildren().add(verticalPlayerCards(players.getPlayers().get(1), 1));
		
		//player3 cards
		player3Cards.getChildren().add(playerCards(players.getPlayers().get(2), 2));
		
		grid = new GridPane();
		grid.setVgap(0);
		grid.setHgap(0);
		
		grid.add(deckView.playerRank(players.getPlayers().get(0), 0), 0, 5);
		player1PlayingSurface.getChildren().add(playedCards(players.getPlayers().get(0).getPlayingSurface(), 1));
		player1ShieldSurface.getChildren().add(shields(players.getPlayers().get(0), 0));
		player1ShieldSurface.setAlignment(Pos.BASELINE_CENTER);
		player1ShieldSurface.setMinWidth(337.5);
		
		//player2 variables
		player2PlayingSurface.getChildren().add(playedCards(players.getPlayers().get(1).getPlayingSurface(), 1));
		player2PlayingSurface.setMinWidth(275);
		player2ShieldSurface.getChildren().add(verticalPlayerShields(players.getPlayers().get(1), 1));
		player2ShieldSurface.setAlignment(Pos.BASELINE_CENTER);
		player2ShieldSurface.setMinHeight(75);
		
		//player3 variables
		player3PlayingSurface.getChildren().add(playedCards(players.getPlayers().get(2).getPlayingSurface(), 2));
		player3ShieldSurface.getChildren().add(shields(players.getPlayers().get(2), 2));
		player3ShieldSurface.setAlignment(Pos.BASELINE_CENTER);
		player3ShieldSurface.setMinWidth(337.5);
		
		grid.add(player1ShieldSurface, 1, 5);
		grid.add(player1Cards, 2, 5);
		grid.add(player1PlayingSurface, 2, 4);
		
		grid.add(deckView.verticalPlayerRank(players.getPlayers().get(1), 1), 5, 4);
		grid.add(player2ShieldSurface, 5, 3);
		grid.add(player2Cards, 5, 1);
		grid.add(player2PlayingSurface, 4, 2);
		
		grid.add(deckView.playerRank(players.getPlayers().get(2), 2), 0, 0);
		grid.add(player3ShieldSurface, 1, 0);
		grid.add(player3Cards, 2, 0);
		grid.add(player3PlayingSurface, 2, 1);
		
		
		//Story deck space
		storyDeckSpace = storyDeckSpace(sDeck, sDiscard);
		storyDeckSpace.getChildren().add(questStageSpace);
		grid.add(storyDeckSpace, 2, 2);
		
		//grid.setGridLinesVisible(true);
		
		if(firstTime) {
			threePlayerStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			firstTime = false;
		}
		threePlayerStage.setScene(new Scene(grid, 1120, 700,Color.AQUA));
		threePlayerStage.getScene().setRoot(grid);
		threePlayerStage.show();
	}
	
	//4 player game setup
	public void setUpFor4Players(MouseEvent event, Players players, StoryDeck sDeck, StoryDiscard sDiscard) {
		player1Cards = new HBox();
		player2Cards = new HBox();
		player3Cards = new HBox();
		VBox player4Cards = new VBox();
		
		HBox player1PlayingSurface = new HBox();
		HBox player1ShieldSurface = new HBox();
		VBox player2PlayingSurface = new VBox();
		VBox player2ShieldSurface = new VBox();
		HBox player3PlayingSurface = new HBox();
		HBox player3ShieldSurface = new HBox();
		VBox player4ShieldSurface = new VBox();
		VBox player4PlayingSurface = new VBox();
		HBox storyDeckSpace = new HBox();
		HBox questStageSpace = new HBox(-50);
		
		grid = new GridPane();
		grid.setVgap(0);
		grid.setHgap(0);
		
		player1PlayingSurface.getChildren().add(playedCards(players.getPlayers().get(0).getPlayingSurface(), 0));
		player1ShieldSurface.getChildren().add(shields(players.getPlayers().get(0), 0));
		player1ShieldSurface.setAlignment(Pos.BASELINE_CENTER);
		player1ShieldSurface.setMinWidth(337.5);
		
		//storyDeck
		storyDeckSpace = storyDeckSpace(sDeck, sDiscard);
		storyDeckSpace.getChildren().add(questStageSpace);
		
		player1Cards.getChildren().add(playerCards(players.getPlayers().get(0), 0));			
		
		//player2Cards
		player2Cards.getChildren().add(verticalPlayerCards(players.getPlayers().get(1), 1));
		
		//player3 cards
		player3Cards.getChildren().add(playerCards(players.getPlayers().get(2), 2));
		
		//player4Cards
		player4Cards.getChildren().add(verticalPlayerCards(players.getPlayers().get(3), 3));
		
		//player2 variables
		player2PlayingSurface.getChildren().add(playedCards(players.getPlayers().get(1).getPlayingSurface(), 1));
		player2PlayingSurface.setMinWidth(125);
		player2PlayingSurface.setMinHeight(175);
		player2ShieldSurface.getChildren().add(verticalPlayerShields(players.getPlayers().get(1), 1));
		player2ShieldSurface.setAlignment(Pos.BASELINE_CENTER);
		player2ShieldSurface.setMinHeight(75);
		
		//player3 variables
		player3PlayingSurface.getChildren().add(playedCards(players.getPlayers().get(2).getPlayingSurface(), 2));
		player3PlayingSurface.setMinWidth(125);
		player3ShieldSurface.getChildren().add(shields(players.getPlayers().get(2), 2));
		player3ShieldSurface.setAlignment(Pos.BASELINE_CENTER);
		player3ShieldSurface.setMinWidth(337.5);
		
		//player4 variables
		player4PlayingSurface.getChildren().add(playedCards(players.getPlayers().get(3).getPlayingSurface(), 3));
		player4PlayingSurface.setMinWidth(125);
		player4ShieldSurface.getChildren().add(verticalPlayerShields(players.getPlayers().get(3), 3));
		player4ShieldSurface.setAlignment(Pos.BASELINE_CENTER);
		player4ShieldSurface.setMinWidth(100);
		
		grid.add(deckView.playerRank(players.getPlayers().get(0), 0), 1, 4);
		grid.add(player1ShieldSurface, 2, 4);
		grid.add(player1Cards, 3, 4);
		
		grid.add(deckView.verticalPlayerRank(players.getPlayers().get(1), 1), 5, 3);
		grid.add(player2ShieldSurface, 5, 2);
		grid.add(player2Cards, 5, 1);
		grid.add(player2PlayingSurface, 4, 2);
		
		
		grid.add(deckView.playerRank(players.getPlayers().get(2), 2), 1, 0);
		grid.add(player3ShieldSurface, 2, 0);
		grid.add(player3Cards, 3, 0);
		grid.add(player3PlayingSurface, 3, 1);
		
		grid.add(deckView.verticalPlayerRank(players.getPlayers().get(3), 3), 0, 3);
		grid.add(player4ShieldSurface, 0, 2);
		//player4PlayingSurface.setMinWidth(175);
		grid.add(player4Cards, 0, 1);
		grid.add(player4PlayingSurface, 1, 1);
		
		grid.add(storyDeckSpace, 3, 2);
		
		//grid.setGridLinesVisible(true);
		
		if(firstTime) {
			fourPlayerStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			firstTime = false;
		}
		fourPlayerStage.setScene(new Scene(grid, 1120, 700,Color.AQUA));
		fourPlayerStage.getScene().setRoot(grid);
		fourPlayerStage.show();
		
	}
	
	//setting up display for top and bottom of screen
	public HBox playerCards(Player player, int index) {
		HBox playerCards = new HBox(-50);
		for(Adventure a : player.getHand()) {
			Image card;
			if(a.getState() == CardStates.FACE_UP) {
				card = new Image("/playingCards/" + a.getName() + ".jpg", 75, 100, true, true);
			} else {
				card = new Image("/playingCards/adventure_back.jpg", 75, 100, true, true);
			}
			
			ImageView theCard = new ImageView(card);
			if(index == 1 || index == 2) {
				theCard.setRotate(180);
			}
			/* This should only be called in the controllers when appropriate, otherwise we run into issues.
			theCard.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					System.out.println( "This is a " + a.getName());
					//player.playCard(a, true);
					//notifyPlayerCardPlayed(event,player,a); this should only be called w
					//notify other players
				}
				
			});*/
			playerCards.getChildren().add(theCard);
		
		}
		return playerCards;
	}
	
	public VBox verticalPlayerCards(Player player, int index) {
		VBox playerCards = new VBox(-90);
		for(Adventure a : player.getHand()) {
			Image card;
			if(a.getState() == CardStates.FACE_UP) {
				card = new Image("/playingCards/" + a.getName() + ".jpg", 75, 100, true, true);
			} else {
				card = new Image("/playingCards/adventure_back.jpg", 75, 100, true, true);
			}
			
			ImageView theCard = new ImageView(card);
			theCard.setRotate(270);
			if(index == 0) {
				theCard.setRotate(180);
			}/* This should only be called in the controller when appropriate, otherwise we have issues.
			theCard.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					System.out.println( "This is a " + a.getName());
				}
				
			});*/
			playerCards.getChildren().add(theCard);
		
		}
		return playerCards;
	}
	
	public HBox stageCards(List<Adventure> cards) {
		HBox stageCards = new HBox(-75);
		for(Adventure a : cards) {
			Image card;
			if(a.getState() == CardStates.FACE_UP) {
				card = new Image("/playingCards/" + a.getName() + ".jpg", 75, 100, true, true);
			} else {
				card = new Image("/playingCards/adventure_back.jpg", 75, 100, true, true);
			}
			
			ImageView theCard = new ImageView(card);
			stageCards.getChildren().add(theCard);
		}
		return stageCards;
	}
	
	public HBox storyDeckSpace(StoryDeck sDeck, StoryDiscard sDiscard) {
		HBox storyDeckSpace = new HBox(10);
		storyDeckSpace.getChildren().addAll(storyDeckPile(sDeck), discardPileForStoryDeck(sDiscard));
		return storyDeckSpace;
	}
	
	
	public HBox playedCards(List<Adventure> playingSurface, int index) {
		HBox playedCards = new HBox(-50);
		
		for(Adventure a: playingSurface) {
			Image card;
			if(a.getState() == CardStates.FACE_UP) {
				card = new Image("/playingCards/" + a.getName() + ".jpg", 75, 100, true, true);
			} else {
				card = new Image("/playingCards/adventure_back.jpg", 75, 100, true, true);
			}
			ImageView theCard = new ImageView(card);
			if(index == 1 || index == 2) {
				theCard.setRotate(180);
			}
			playedCards.getChildren().add(theCard);
		}
		if(playedCards.getChildren().isEmpty()) {
			playedCards.setMinHeight(100);
		}
		return playedCards;
	}
	
	public HBox shields(Player p, int pIndex) {
		HBox shields = new HBox(-50);
		for(int i = 0; i < p.getShields(); i++) {
			if(pIndex == 0) {
				shields.getChildren().add(new ImageView(new Image("/playingCards/shield_1.jpg", 75, 100, true, true)));
			} else if(pIndex == 1) {
				ImageView theCard = new ImageView(new Image("/playingCards/shield_2.jpg", 75, 100, true, true));
				theCard.setRotate(180);
				shields.getChildren().add(theCard);
			}else{
				ImageView theCard = new ImageView(new Image("/playingCards/shield_3.jpg", 75, 100, true, true));
				theCard.setRotate(180);
				shields.getChildren().add(theCard);
			}
		}
		return shields;
	}
	
	public VBox verticalPlayerShields(Player p, int pIndex) {
		VBox shields = new VBox(-50);
		for(int i = 0; i < p.getShields(); i++) {
			if(pIndex == 0) {
				shields.getChildren().add(new ImageView(new Image("/playingCards/shield_1.jpg", 75, 100, true, true)));
			} else if(pIndex == 1) {
				ImageView theCard = new ImageView(new Image("/playingCards/shield_2.jpg", 75, 100, true, true));
				theCard.setRotate(270);
				shields.getChildren().add(theCard);
			}else if (pIndex == 3){
				ImageView theCard = new ImageView(new Image("/playingCards/shield_4.jpg", 75, 100, true, true));
				theCard.setRotate(270);
				shields.getChildren().add(theCard);
			}
		}
		return shields;
	}
	
	VBox storyCards;
	public VBox storyDeckPile(StoryDeck storyDeck) {
		storyCards = storyDeckCards();
		topCard = 0;
		if(storyDeck.size() > 0) {
			topCard = storyDeck.size() - 1;
		}
			
		for(Story s: storyDeck) {
			Image card;
			if(s.getState() == CardStates.FACE_UP) {
				card = new Image("/playingCards/" + s.getName() + ".jpg", 75, 100, true, true);
			} else {
				card = new Image("/playingCards/story_back.jpg", 75, 100, true, true);
			}
			
			ImageView theCard = new ImageView(card);
			storyCards.getChildren().add(theCard);
		}
		
		return storyCards;	
	}
	
	public VBox getStoryCards() {
		return storyCards;
	}
	
	public HBox getPlayerCards() {
		return player1Cards;
	}
	
	public VBox discardPileForStoryDeck(StoryDiscard sDiscard) {
		VBox discardPile = new VBox(-99);
		for(Story s: sDiscard) {
			Image card;
			if(s.getState() == CardStates.FACE_UP) {
				card = new Image("/playingCards/" + s.getName() + ".jpg", 75, 100, true, true);
			} else {
				card = new Image("/playingCards/story_back.jpg", 75, 100, true, true);
			}
			ImageView theCard = new ImageView(card);
			discardPile.getChildren().add(theCard);
		}
		return discardPile;
	}
	
	// ADDED FOR QUEST/TOURNEY PROMPT
	public boolean prompt(String type) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		
		alert.setTitle("Participant Dialog");
		alert.setHeaderText(type + " Participant Request");
		alert.setContentText("Would you like to participate?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    
			return true;
		} else {
		    // ... user chose CANCEL or closed the dialog
			return false;
		}
	}
	
	public boolean sponsorPrompt() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		
		alert.setTitle("Sponsor Dialog");
		alert.setHeaderText("Sponsor Request");
		alert.setContentText("Would you like to sponsor?");

		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean seeCardPrompt(Player p) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		alert.setTitle(p.getName() + "'s Turn");
		alert.setHeaderText(p.getName() + "'s Turn");
		alert.setContentText("Please pass the computer to " + p.getName());
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean switchPrompt(String type, String name, Player p) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		
		alert.setTitle(type + " Dialog");
		alert.setHeaderText("Switch " + type);
		alert.setContentText("Please switch to " + name + ". When you have switched, click 'OK'.");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			return true;
		} else {
		    // ... user chose CANCEL or closed the dialog
			return true;
		}
	}
	
	public boolean promptGameEnd(Player p) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		
		alert.setTitle("Player Win Dialog");
		alert.setHeaderText("Congratulations " + p.getName() + "You win!!");
		alert.setContentText("Would you like to play again?");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			primStage.close();
			return true;
		} else {
		    primStage.close();
			return false;
		}
	}
	
	public boolean promptForStageSetup(String name) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
	
		alert.setTitle("Stage Dialog");
		alert.setHeaderText("Stage setup");
		alert.setContentText("Please press OK to let "+ name + "view their cards for stage setup.");
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	
	boolean cardClicked; 
	boolean buttonClicked;
	int cardIndex;
	public boolean promptAddCardToStage(Player p) {
		cardClicked = false;
		cardIndex = 0;
		final Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.setTitle("Please choose either a foe or a test");
		VBox window = new VBox();
		List<Button> cards = new ArrayList<>();
		for(cardIndex = 0; cardIndex < p.getHand().size(); cardIndex++) {
			if(p.getHand().get(cardIndex) instanceof Foe || p.getHand().get(cardIndex) instanceof Test) {
				Button button = new Button();
				final int index = cardIndex;
				BackgroundImage buttonBackground = new BackgroundImage(((ImageView)player1Cards.getChildren().get(cardIndex)).getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
				button.setBackground(new Background(buttonBackground));
				button.setMinWidth(75);
				button.setMinHeight(100);
				button.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						twoPlayerStage.getScene().getRoot().setEffect(null);
						notifyStageCardChosen(p, p.getHand().get(index));
						cardClicked = true;
						dialog.close();
					}
				});
				cards.add(button);
			}
		}
		HBox foesAndTests = new HBox();
		foesAndTests.getChildren().addAll(cards);
		foesAndTests.setMaxHeight(100);
		window.getChildren().addAll(foesAndTests);
		window.setAlignment(Pos.CENTER);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(twoPlayerStage);
		Scene scene = new Scene(window, (75 * cards.size()) + 100, 150, Color.AQUA);
		dialog.setScene(scene);
		dialog.centerOnScreen();
		
		final Node root = dialog.getScene().getRoot();
		final Delta dragDelta = new Delta();
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				dragDelta.x = arg0.getSceneX();
				dragDelta.y = arg0.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				dialog.setX(event.getScreenX() - dragDelta.x);
				dialog.setY(event.getScreenY() - dragDelta.y);
			}
		});
		twoPlayerStage.getScene().getRoot().setEffect(new BoxBlur());
		dialog.showAndWait();
		if(cardClicked) {
			return true;
		} else {
			return false;
		}
	}
	class Delta { double x, y;}
	public boolean promptAddWeaponsToFoe(Player p, ArrayList<Weapon> weapons) {
		//ArrayList<Weapon> weapons = new ArrayList<>();
		cardClicked = false;
		buttonClicked = false;
		cardIndex = 0;
		final Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.setTitle("Please choose the weapons you wish to play");
		VBox window = new VBox();
		List<Button> cards = new ArrayList<>();
		for(cardIndex = 0; cardIndex < p.getHand().size(); cardIndex++) {
			if(p.getHand().get(cardIndex) instanceof Weapon) {
				Button button = new Button();
				final int index = cardIndex;
				BackgroundImage buttonBackground = new BackgroundImage(((ImageView)player1Cards.getChildren().get(cardIndex)).getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
				button.setBackground(new Background(buttonBackground));
				button.setMinWidth(75);
				button.setMinHeight(100);
				button.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						boolean dup = false;
						for(Weapon w : weapons) {
							if(w.getName().equals(p.getHand().get(index).getName())) {
								dup = true;
							}
						}
						if(!dup) {
							weapons.add((Weapon)p.getHand().get(index));
							notifyStageWeaponChosen(p, (Weapon)p.getHand().get(index));
							dialog.close();
							cardClicked = true; 
						} else {
							promptWeaponDuplicate("Foe");
						}
					}
				});
				cards.add(button);
			}
		}
		Button finishedButton = new Button();
		finishedButton.setText("Done");
		finishedButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				twoPlayerStage.getScene().getRoot().setEffect(null);
				dialog.close();
				buttonClicked = true;
			}
		});
		finishedButton.setAlignment(Pos.BASELINE_RIGHT);
		HBox weaponsBox = new HBox();
		cards.add(finishedButton);
		weaponsBox.getChildren().addAll(cards);
		weaponsBox.setMaxHeight(100);
		window.getChildren().add(weaponsBox);
		window.setAlignment(Pos.CENTER);
		window.setMaxHeight(weaponsBox.getHeight());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(twoPlayerStage);
		Scene scene = new Scene(window, (75 * cards.size()) + 100, 150, Color.AQUA);
		dialog.setScene(scene);
		dialog.centerOnScreen();
		
		final Node root = dialog.getScene().getRoot();
		final Delta dragDelta = new Delta();
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				dragDelta.x = arg0.getSceneX();
				dragDelta.y = arg0.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				dialog.setX(event.getScreenX() - dragDelta.x);
				dialog.setY(event.getScreenY() - dragDelta.y);
			}
		});
		twoPlayerStage.getScene().getRoot().setEffect(new BoxBlur());
		dialog.showAndWait();
		if(cardClicked) {
			if(!buttonClicked) {
				promptAddWeaponsToFoe(p, weapons);
			}
			return true;
		} else {
			if(buttonClicked) {
				return true;
			}
			return false;
		}
	}
	
	public void promptNotEnoughBP() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Stage Error Dialog");
		alert.setHeaderText("Error: Not enough BP");
		alert.setContentText("The Foe and it's weapons do not have enough battle points to be added.");

		alert.showAndWait();
	}
	
	public void promptWeaponDuplicate(String type) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Stage Error Dialog");
		alert.setHeaderText("Error: Weapon Duplicate");
		alert.setContentText(type + " already has this weapon! Please choose a different one!");

		alert.showAndWait();
	}
	
	public boolean playPrompt(String name, Player p, ArrayList<Adventure> playedCards) {
		//ArrayList<Weapon> weapons = new ArrayList<>();
		cardClicked = false;
		buttonClicked = false;
		cardIndex = 0;
		final Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.setTitle("Please choose the cards you wish to play");
		VBox window = new VBox();
		List<Button> cards = new ArrayList<>();
		for(cardIndex = 0; cardIndex < p.getHand().size(); cardIndex++) {
			if(p.getHand().get(cardIndex) instanceof Ally || p.getHand().get(cardIndex) instanceof Weapon || p.getHand().get(cardIndex) instanceof Amour) {
				Button button = new Button();
				final int index = cardIndex;
				BackgroundImage buttonBackground = new BackgroundImage(((ImageView)player1Cards.getChildren().get(cardIndex)).getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
				button.setBackground(new Background(buttonBackground));
				button.setMinWidth(75);
				button.setMinHeight(100);
				button.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						p.getHand().get(index).setState(CardStates.FACE_DOWN);
						notifyPlayerCardPlayed(arg0, p, p.getHand().get(index));
						dialog.close();
						cardClicked = true;
					}
				});
				cards.add(button);
			}
		}
		Button finishedButton = new Button();
		finishedButton.setText("Done");
		finishedButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {	
				twoPlayerStage.getScene().getRoot().setEffect(null);
				dialog.close();
				buttonClicked = true;
			}
		});
		finishedButton.setAlignment(Pos.BASELINE_RIGHT);
		HBox alliesWeaponsAndAmour = new HBox();
		cards.add(finishedButton);
		alliesWeaponsAndAmour.getChildren().addAll(cards);
		alliesWeaponsAndAmour.setMaxHeight(100);
		window.getChildren().add(alliesWeaponsAndAmour);
		window.setAlignment(Pos.CENTER);
		window.setMaxHeight(alliesWeaponsAndAmour.getHeight());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(twoPlayerStage);
		Scene scene = new Scene(window, (75 * cards.size()) + 100, 150, Color.AQUA);
		dialog.setScene(scene);
		dialog.centerOnScreen();
				
		final Node root = dialog.getScene().getRoot();
		final Delta dragDelta = new Delta();
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				dragDelta.x = arg0.getSceneX();
				dragDelta.y = arg0.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				dialog.setX(event.getScreenX() - dragDelta.x);
				dialog.setY(event.getScreenY() - dragDelta.y);
			}
		});
		twoPlayerStage.getScene().getRoot().setEffect(new BoxBlur());
		dialog.showAndWait();
		if(cardClicked) {
			if(!buttonClicked) {
				playPrompt(name, p, playedCards);
			}
			return true;
		} else {
			if(buttonClicked) {
				return true;
			}
			return false;
		}
	}
	
	public Player promptBid(int currBid, Player p) {
		TextInputDialog dialog = new TextInputDialog("" + currBid);
		dialog.setTitle("Bid Dialog");
		dialog.setHeaderText(p.getName() + " Bid Dialog");
		dialog.setContentText("current bid: " + currBid + "\n" +
							  "Your maximum allowed bid: " + p.getMaxBid() + "\n" +
							  "Please enter your bid: ");
		
		dialog.getEditor().textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				// TODO Auto-generated method stub
				if(!newValue.matches("\\d*")) {
					dialog.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			int bid = Integer.parseInt(result.get());
			p.bid(bid);
			if(bid < p.getMaxBid() || bid > currBid) {
				return p;
			} else {
				promptBid(currBid, p);
				return null;
			}
		} else {
			return null;
		}
		
	}
	
	public void rotate(PlayGame game) {
		// for now, will switch focus between just two players
		System.out.println("test rotate");
		this.grid.getChildren().clear();
		Players reversed = new Players();
		reversed = game.getPlayers();
				
		// we should probably make Players have methods that do this???
		List<Player> persons = new ArrayList<Player>();
		persons = game.getPlayers().getPlayers(); // this is really weird...
		Player temp = persons.get(persons.size()-1);
		persons.add(0, temp);
		persons.remove(persons.size()-1);
		
		//Collections.reverse(persons);
		
		reversed.setPlayers(persons);

	//	reversed.setPlayers(Collections.reverse(reversed.getPlayers()));
		setupFor2Players(null, reversed, game.getSDeck(), game.getSDiscard(), null);
		
		// We should make either Players or PlayGame do this, I prefer PlayGame because it calls update from the view.
		
	}
	
	public boolean switchPrompt(String name, Player p) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		alert.setTitle("Participant Dialog");
		alert.setHeaderText("Switch Participant");
		alert.setContentText("Please switch to " + name + ". When you have switched, click 'OK'.");
		
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			return true;
		} else {
		    // ... user chose CANCEL or closed the dialog
			return true;
		}
	}
	
	
	public ArrayList<Adventure> bidDiscardPrompt(Player p, int numCards, boolean firstTime) {
		ArrayList<Adventure> discardedCards = new ArrayList<>();
		cardClicked = false;
		buttonClicked = false;
		cardIndex = 0;
		if(firstTime) {
			if(p.getAllies().size() > 0) { //this is to lessen the required discard count if allies are in play that have bids
				for(Ally a : p.getAllies()) {
					numCards -= a.getBids();
				}
			}
		}
		System.out.println(numCards);
		final Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.setTitle("Please choose the cards you wish to discard");
		VBox window = new VBox();
		Label discardCountLabel = new Label();
		discardCountLabel.setText("Number of cards to discard: " + numCards);
		List<Button> cards = new ArrayList<>();
		for(cardIndex = 0; cardIndex < p.getHand().size(); cardIndex++) {
			Button button = new Button();
			final int index = cardIndex;
			BackgroundImage buttonBackground = new BackgroundImage(((ImageView)player1Cards.getChildren().get(cardIndex)).getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
			button.setBackground(new Background(buttonBackground));
			button.setMinWidth(75);
			button.setMinHeight(100);
			button.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					discardedCards.add(p.getHand().get(index));
					notifyBidCardChosen(p, p.getHand().get(index));
					dialog.close();
					cardClicked = true;
				}
			});
			cards.add(button);
		}
		HBox cardButtons = new HBox();
		//cards.add(finishedButton);
		cardButtons.getChildren().addAll(cards);
		cardButtons.setMaxHeight(100);
		window.getChildren().add(discardCountLabel);
		window.getChildren().add(cardButtons);
		window.setAlignment(Pos.CENTER);
		window.setMaxHeight(cardButtons.getHeight());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(twoPlayerStage);
		Scene scene = new Scene(window, (75 * cards.size()) + 100, 150, Color.AQUA);
		dialog.setScene(scene);
		dialog.centerOnScreen();
				
		final Node root = dialog.getScene().getRoot();
		final Delta dragDelta = new Delta();
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				dragDelta.x = arg0.getSceneX();
				dragDelta.y = arg0.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				dialog.setX(event.getScreenX() - dragDelta.x);
				dialog.setY(event.getScreenY() - dragDelta.y);
			}
		});
		twoPlayerStage.getScene().getRoot().setEffect(new BoxBlur());
		dialog.showAndWait();
		if(cardClicked) {
			if(numCards > 1) {
				bidDiscardPrompt(p, numCards-= 1, false);
			}
			return discardedCards;
		} else {
			return null;
		}
	}
	
	public boolean cardOverflowPrompt(Player p, int numCards) {
		cardClicked = false;
		buttonClicked = false;
		cardIndex = 0;
		System.out.println(numCards);
		PlayGame pg = PlayGame.getInstance();
		QuestHandler qh = QuestHandler.getInstance();
		p.setHandState(CardStates.FACE_UP);
		if(qh != null && qh.getCard() != null) { 
			update(null, pg.getPlayers(), pg.getSDeck(), pg.getSDiscard(), qh.getCard());
		} else {
			update(null, pg.getPlayers(), pg.getSDeck(), pg.getSDiscard(), null);
		}
		final Stage dialog = new Stage(StageStyle.DECORATED);
		dialog.setTitle("Card OverFlow");
		VBox window = new VBox();
		Label discardCountLabel = new Label();
		discardCountLabel.setText("Number of cards to play or discard: " + numCards);
		List<Button> allyAmourCards = new ArrayList<>();
		List<Button> otherCards = new ArrayList<>();
		for(cardIndex = 0; cardIndex < p.getHand().size(); cardIndex++) {
			if(qh != null && qh.getCard() != null) {
				if(p.getHand().get(cardIndex) instanceof Ally || (p.getHand().get(cardIndex) instanceof Amour && p.getAmour().size() == 0)) {
					Button button = new Button();
					final int index = cardIndex;
					BackgroundImage buttonBackground = new BackgroundImage(((ImageView)player1Cards.getChildren().get(cardIndex)).getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
					button.setBackground(new Background(buttonBackground));
					button.setMinWidth(75);
					button.setMinHeight(100);
					button.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent arg0) {
							p.getHand().get(index).setState(CardStates.FACE_UP);
							notifyPlayerCardPlayed(arg0, p, p.getHand().get(index));
							dialog.close();
							cardClicked = true;
						}
					});
					allyAmourCards.add(button);
				} else {
					Button button = new Button();
					final int index = cardIndex;
					BackgroundImage buttonBackground = new BackgroundImage(((ImageView)player1Cards.getChildren().get(cardIndex)).getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
					button.setBackground(new Background(buttonBackground));
					button.setMinWidth(75);
					button.setMinHeight(100);
					button.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent arg0) {
							notifyPlayerCardDiscarded(p, p.getHand().get(index));
							dialog.close();
							cardClicked = true;
						}
					});
					otherCards.add(button);
				}
			}
			else {
				if(p.getHand().get(cardIndex) instanceof Ally) {
					Button button = new Button();
					final int index = cardIndex;
					BackgroundImage buttonBackground = new BackgroundImage(((ImageView)player1Cards.getChildren().get(cardIndex)).getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
					button.setBackground(new Background(buttonBackground));
					button.setMinWidth(75);
					button.setMinHeight(100);
					button.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent arg0) {
							p.getHand().get(index).setState(CardStates.FACE_UP);
							notifyPlayerCardPlayed(arg0, p, p.getHand().get(index));
							dialog.close();
							cardClicked = true;
						}
					});
					allyAmourCards.add(button);
				} else {
					Button button = new Button();
					final int index = cardIndex;
					BackgroundImage buttonBackground = new BackgroundImage(((ImageView)player1Cards.getChildren().get(cardIndex)).getImage(), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true));
					button.setBackground(new Background(buttonBackground));
					button.setMinWidth(75);
					button.setMinHeight(100);
					button.setOnMouseClicked(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent arg0) {
							notifyPlayerCardDiscarded(p, p.getHand().get(index));
							dialog.close();
							cardClicked = true;
						}
					});
					otherCards.add(button);
				}
			}
		}
		HBox firstCardButtons = new HBox();
		//cards.add(finishedButton);
		firstCardButtons.getChildren().addAll(allyAmourCards);
		firstCardButtons.setMaxHeight(100);
		HBox secondCardButtons = new HBox();
		secondCardButtons.getChildren().addAll(otherCards);
		secondCardButtons.setMaxHeight(100);
		window.getChildren().add(discardCountLabel);
		window.getChildren().add(firstCardButtons);
		window.getChildren().add(secondCardButtons);
		window.setAlignment(Pos.CENTER);
		window.setMaxHeight(discardCountLabel.getHeight() + firstCardButtons.getHeight() + secondCardButtons.getHeight());
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(twoPlayerStage);
		Scene scene = new Scene(window, (75 * (allyAmourCards.size() + otherCards.size())) + 100, 250, Color.AQUA);
		dialog.setScene(scene);
		dialog.centerOnScreen();
				
		final Node root = dialog.getScene().getRoot();
		final Delta dragDelta = new Delta();
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				dragDelta.x = arg0.getSceneX();
				dragDelta.y = arg0.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				dialog.setX(event.getScreenX() - dragDelta.x);
				dialog.setY(event.getScreenY() - dragDelta.y);
			}
		});
		twoPlayerStage.getScene().getRoot().setEffect(new BoxBlur());
		dialog.showAndWait();
		if(cardClicked) {
			if(numCards > 1) {
				cardOverflowPrompt(p, numCards-= 1);
			}
			p.setHandState(CardStates.FACE_DOWN);
			if(qh != null && qh.getCard() != null) { 
				update(null, pg.getPlayers(), pg.getSDeck(), pg.getSDiscard(), qh.getCard());
			} else {
				update(null, pg.getPlayers(), pg.getSDeck(), pg.getSDiscard(), null);
			}
			return true;
		} else {
			return false;
		}
	}
	
	public void promptTooManyAmour() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Too Many Amour Error Dialog");
		alert.setHeaderText("Error: Too Many Amour");
		alert.setContentText("Amour is already played, choose something else!");

		alert.showAndWait();
	}
	
	
	
	/*
	
	public void cardSelect(Player p) { 
		System.out.println("test submit btn");
		Button submitButton = new Button("Submit");
		submitButton.setPrefSize(300, 100);
		grid.add(submitButton, 0, 1);
		
	}*/
	
	
	
	
	public void notifyStoryCardClicked(MouseEvent event, Story card) {
		if(listeners.get(0) != null) {
			listeners.get(0).onStoryCardDraw(event);
		}
	}
	
	//notify when adventure card is drawn
	public void notifyPlayerCardPlayed(MouseEvent event,Player p, Adventure card) {
		if(listeners.get(0) != null) {
			listeners.get(0).onAdventureCardPlayed(p,card, event);
		}
	}
	
	public void notifyPlayerCardDiscarded(Player p, Adventure card) {
		if(listeners.get(0) != null) {
			listeners.get(0).onDiscardCard(p, card);
		}
	}
	
	//notify when stage card is chosen
	public void notifyStageCardChosen(Player p, Adventure card) {
		if(listeners.get(1) != null) {
			listeners.get(1).onStageCardPicked(p, card);
		}
	}
	
	//notify when stage weapons are chosen
	public void notifyStageWeaponChosen(Player p, Weapon card) {
		if(listeners.get(1) != null) {
			listeners.get(1).onStageWeaponPicked(p, card);
		}
	}
		
	//notify card chosen for discard in quest
	public void notifyBidCardChosen(Player p, Adventure card) {
		if(listeners.get(1) != null) {
			listeners.get(1).onBidCardPicked(p, card);
		}
	}
}