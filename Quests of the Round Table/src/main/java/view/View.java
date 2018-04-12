package view;

import java.awt.Dialog.ModalityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import control.ControlHandler;
import control.PlayGame;
import control.QuestHandler;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;

// new stuff Ben added
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.Insets;
import javafx.stage.Modality;
// ------------------

import model.Adventure;
import model.Ally;
import model.Amour;
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
	
	private static final Logger logger = LogManager.getLogger(View.class);
	private BorderPane border;
	
	// NEW THING I ADDED V WEEKY DEEKY
	public GridPane grid;// = new GridPane();
	// -------------------------------
	
	private HBox playerSpace;
	private HBox secondPlayerSpace;
	private HBox thirdPlayerSpace;
	private HBox fourthPlayerSpace;
	private VBox verticalPlayerSpace;
	
	
	private Scene gameTable;
	private DeckView deckView = new DeckView();
	private Players players = new Players();
	
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
	private HBox player4Cards;
	private HBox stageSpace;
	private HBox player1PlayingSurface;
	private VBox player2PlayingSurface;
	private HBox player3PlayingSurface;
	private VBox player4PlayingSurface;
	public Button endButton;
	
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
	
	/*public HBox getSurface(int i) {
		if(i == 1) {
			return getPlayerSurface();
		}
		else if (i == 2) {
			return getPlayer2Surface();
		}
		else if (i == 3) {
			return getPlayer3Surface();
		}
		else {
			return getPlayer4Surface();
		}
		
	}*/
	
	public HBox getPlayerSurface() {
		return player1PlayingSurface;
	}
	
	public VBox getPlayer2Surface() {
		return player2PlayingSurface;
	}
	
	public HBox getPlayer3Surface() {
		return player3PlayingSurface;
	}
	
	public VBox getPlayer4Surface() {
		return player4PlayingSurface;
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
			setUpFor3Players(event, players,sDeck, sDiscard, quest);
		} else {
			setUpFor4Players(event, players, sDeck, sDiscard, quest);
			notifyUpdate();
		}
	}
	
	// SET UP FOR TWO PLAYERS ----------------------------------------------------------------------------------------------------------
		private void setupFor2Players(MouseEvent event, Players players, StoryDeck sDeck, StoryDiscard sDiscard, Quest quest) {
			player1Cards = new HBox();
			player2Cards = new HBox();
			player1PlayingSurface = new HBox();
			HBox player2PlayingSurface = new HBox();
			HBox player1ShieldSurface = new HBox();
			HBox player2ShieldSurface = new HBox();
			HBox storyDeckSpace = new HBox();
			HBox questStageSpace = new HBox(-50);
			if(quest != null) {
				for(model.Stage stage : quest.getStages()) {
					stageSpace = new HBox();
					List<Adventure> cards = new ArrayList<Adventure>();
					if(stage.getTest() != null) {
						cards.add(stage.getTest());
						stageSpace = stageCards(cards);
					}
					if(stage.getFoe() != null) {
						cards.add(stage.getFoe());
						cards.addAll(stage.getFoe().getWeapons());
						stageSpace = stageCards(cards);
					}
					questStageSpace.getChildren().add(stageSpace);
				}
				questStageSpace.setMinWidth(187.5);
			}
			
			player1Cards = playerCards(players.getPlayers().get(0), 0);	
			player1Cards.setMinWidth(350);
			player1PlayingSurface = playedCards(players.getPlayers().get(0).getPlayingSurface(), 0);
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
			if(firstTime) {
				if(primStage != null) {
					primStage.close();
				}
			
				twoPlayerStage = new Stage(StageStyle.DECORATED);
				firstTime = false;
			}
			twoPlayerStage.setScene(new Scene(grid, 1120, 700,Color.AQUA));
			twoPlayerStage.getScene().setRoot(grid);
			primStage = twoPlayerStage;
			twoPlayerStage.show();
		}
	// -----------------------------------------------------------------------------------------------------------------------
	
	public void setUpFor3Players(MouseEvent event, Players players, StoryDeck sDeck, StoryDiscard sDiscard, Quest quest) {
		player1Cards = new HBox();
		player2Cards = new HBox();
		player3Cards = new HBox();
		player1PlayingSurface = new HBox();
		HBox player1ShieldSurface = new HBox();
		VBox player2PlayingSurface = new VBox();
		VBox player2ShieldSurface = new VBox();
		HBox player3PlayingSurface = new HBox();
		HBox player3ShieldSurface = new HBox();
		HBox storyDeckSpace = new HBox();
		HBox questStageSpace = new HBox(-50);
		if(quest != null) {
			for(model.Stage stage : quest.getStages()) {
				stageSpace = new HBox();
				List<Adventure> cards = new ArrayList<Adventure>();
				if(stage.getTest() != null) {
					cards.add(stage.getTest());
					stageSpace = stageCards(cards);
				}
				if(stage.getFoe() != null) {
					cards.add(stage.getFoe());
					cards.addAll(stage.getFoe().getWeapons());
					stageSpace = stageCards(cards);
				}
				questStageSpace.getChildren().add(stageSpace);
			}
			questStageSpace.setMinWidth(187.5);
		}
		
		player1Cards = playerCards(players.getPlayers().get(0), 0);			
			
		//player2Cards
		player2Cards.getChildren().add(verticalPlayerCards(players.getPlayers().get(2), 1));
		
		//player3 cards
		player3Cards.getChildren().add(playerCards(players.getPlayers().get(1), 2));
		
		grid = new GridPane();
		grid.setVgap(0);
		grid.setHgap(0);
		
		
		player1PlayingSurface = playedCards(players.getPlayers().get(0).getPlayingSurface(), 1);
		player1ShieldSurface.getChildren().add(shields(players.getPlayers().get(0), 0));
		player1ShieldSurface.setAlignment(Pos.BASELINE_CENTER);
		player1ShieldSurface.setMinWidth(337.5);
		
		//player2 variables
		player2PlayingSurface.getChildren().add(verticalPlayedCards(players.getPlayers().get(1).getPlayingSurface(), 1));
		player2PlayingSurface.setMinWidth(275);
		player2ShieldSurface.getChildren().add(verticalPlayerShields(players.getPlayers().get(1), 1));
		player2ShieldSurface.setAlignment(Pos.CENTER_RIGHT);
		player2ShieldSurface.setMinHeight(75);
		
		//player3 variables
		player3PlayingSurface.getChildren().add(playedCards(players.getPlayers().get(2).getPlayingSurface(), 2));
		player3ShieldSurface.getChildren().add(shields(players.getPlayers().get(2), 2));
		player3ShieldSurface.setAlignment(Pos.BASELINE_CENTER);
		player3ShieldSurface.setMinWidth(337.5);
		
		grid.add(deckView.playerRank(players.getPlayers().get(0), 0), 1, 4);
		grid.add(player1ShieldSurface, 2, 4);
		grid.add(player1Cards, 3, 4);
		grid.add(player1PlayingSurface, 3, 3);
		
		grid.add(deckView.verticalPlayerRank(players.getPlayers().get(1), 1), 0, 3);
		grid.add(player2ShieldSurface, 0, 2);
		grid.add(player2Cards, 0, 1);
		grid.add(player2PlayingSurface, 1, 2);
		
		grid.add(deckView.playerRank(players.getPlayers().get(2), 2), 1, 0);
		grid.add(player3ShieldSurface, 2, 0);
		grid.add(player3Cards, 3, 0);
		grid.add(player3PlayingSurface, 3, 1);
		
		
		//Story deck space
		storyDeckSpace = storyDeckSpace(sDeck, sDiscard);
		storyDeckSpace.getChildren().add(questStageSpace);
		grid.add(storyDeckSpace, 3, 2);
		
		//grid.setGridLinesVisible(true);
		
		if(firstTime) {
			if(primStage != null) {
				primStage.close();
			}
		
			threePlayerStage = new Stage(StageStyle.DECORATED);
			firstTime = false;
		}
		threePlayerStage.setScene(new Scene(grid, 1120, 700,Color.AQUA));
		threePlayerStage.getScene().setRoot(grid);
		primStage = threePlayerStage;
		threePlayerStage.show();
	}
	
	//4 player game setup
	public void setUpFor4Players(MouseEvent event, Players players, StoryDeck sDeck, StoryDiscard sDiscard, Quest quest) {
		player1Cards = new HBox();
		player2Cards = new HBox();
		player3Cards = new HBox();
		player4Cards = new HBox();
		
		player1PlayingSurface = new HBox();
		HBox player1ShieldSurface = new HBox();
		//VBox player2PlayingSurface = new VBox();
		player2PlayingSurface = new VBox();
		VBox player2ShieldSurface = new VBox();
		player3PlayingSurface = new HBox();
		HBox player3ShieldSurface = new HBox();
		VBox player4ShieldSurface = new VBox();
		player4PlayingSurface = new VBox();
		HBox storyDeckSpace = new HBox();
		HBox questStageSpace = new HBox(-50);
		
		endButton  = new Button();
		endButton.setText("End Turn");
		endButton.setPrefSize(100, 100);
		endButton.setAlignment(Pos.BASELINE_CENTER);
		
		if(quest != null) {
			for(model.Stage stage : quest.getStages()) {
				stageSpace = new HBox();
				List<Adventure> cards = new ArrayList<Adventure>();
				if(stage.getTest() != null) {
					cards.add(stage.getTest());
					stageSpace = stageCards(cards);
				}
				if(stage.getFoe() != null) {
					cards.add(stage.getFoe());
					cards.addAll(stage.getFoe().getWeapons());
					stageSpace = stageCards(cards);
				}
				questStageSpace.getChildren().add(stageSpace);
			}
			questStageSpace.setMinWidth(187.5);
		}
		
		grid = new GridPane();
		grid.setVgap(0);
		grid.setHgap(0);
		
		player1PlayingSurface = playedCards(players.getPlayers().get(0).getPlayingSurface(), 0);
		player1ShieldSurface.getChildren().add(shields(players.getPlayers().get(0), 0));
		player1ShieldSurface.setAlignment(Pos.BASELINE_CENTER);
		player1ShieldSurface.setMinWidth(337.5);
		
		//storyDeck
		storyDeckSpace = storyDeckSpace(sDeck, sDiscard);
		storyDeckSpace.getChildren().add(questStageSpace);
		
		player1Cards = playerCards(players.getPlayers().get(0), 0);			
		
		//player2Cards
		player2Cards.getChildren().add(verticalPlayerCards(players.getPlayers().get(1), 1));
		
		//player3 cards
		player3Cards.getChildren().add(playerCards(players.getPlayers().get(2), 2));
		
		//player4Cards
		player4Cards.getChildren().add(verticalPlayerCards(players.getPlayers().get(3), 3));
		
		//player2 variables
		player2PlayingSurface.getChildren().add(verticalPlayedCards(players.getPlayers().get(1).getPlayingSurface(), 1));
		player2PlayingSurface.setMinWidth(125);
		player2PlayingSurface.setMinHeight(175);
		player2ShieldSurface.getChildren().add(verticalPlayerShields(players.getPlayers().get(1), 1));
		player2ShieldSurface.setAlignment(Pos.CENTER_RIGHT);
		player2ShieldSurface.setMinHeight(75);
		
		//player3 variables
		player3PlayingSurface.getChildren().add(playedCards(players.getPlayers().get(2).getPlayingSurface(), 2));
		player3PlayingSurface.setMinWidth(125);
		player3ShieldSurface.getChildren().add(shields(players.getPlayers().get(2), 2));
		player3ShieldSurface.setAlignment(Pos.BASELINE_CENTER);
		player3ShieldSurface.setMinWidth(337.5);
		
		//player4 variables
		player4PlayingSurface.getChildren().add(verticalPlayedCards(players.getPlayers().get(3).getPlayingSurface(), 3));
		player4PlayingSurface.setMinWidth(125);
		player4ShieldSurface.getChildren().add(verticalPlayerShields(players.getPlayers().get(3), 3));
		player4ShieldSurface.setAlignment(Pos.CENTER_LEFT);
		player4ShieldSurface.setMinWidth(100);
		
		grid.add(endButton, 0, 4);
		
		grid.add(deckView.playerRank(players.getPlayers().get(0), 0), 1, 4);
		grid.add(player1ShieldSurface, 2, 4);
		grid.add(player1Cards, 3, 4);
		grid.add(player1PlayingSurface, 3, 3);
		
		grid.add(deckView.verticalPlayerRank(players.getPlayers().get(1), 1), 0, 3);
		grid.add(player2ShieldSurface, 0, 2);
		grid.add(player2Cards, 0, 1);
		grid.add(player2PlayingSurface, 1, 2);
		
		
		grid.add(deckView.playerRank(players.getPlayers().get(2), 2), 1, 0);
		grid.add(player3ShieldSurface, 2, 0);
		grid.add(player3Cards, 3, 0);
		grid.add(player3PlayingSurface, 3, 1);
		
		grid.add(deckView.verticalPlayerRank(players.getPlayers().get(3), 3), 5, 3);
		grid.add(player4ShieldSurface, 5, 2);
		
		//player4PlayingSurface.setMinWidth(175);
		grid.add(player4Cards, 5, 1);
		grid.add(player4PlayingSurface, 4, 1);
		
		grid.add(storyDeckSpace, 3, 2);
		
		//grid.setGridLinesVisible(true);
		
		if(firstTime) {
			if(primStage != null) {
				primStage.close();
			}
		
			fourPlayerStage = new Stage(StageStyle.DECORATED);
			firstTime = false;
		}
		fourPlayerStage.setScene(new Scene(grid, 1120, 700, Color.AQUA));
		fourPlayerStage.getScene().setRoot(grid);
		primStage = fourPlayerStage;
		fourPlayerStage.show();
		
	}
	
	//setting up display for top and bottom of screen
		public HBox playerCards(Player player, int index) {
			HBox playerCards = new HBox();
			
			for(Adventure a : player.getHand()) {
				Image card;
				if(a.getState() == CardStates.FACE_UP) {
					card = new Image("/playingCards/" + a.getName() + ".jpg", 75, 100, true, true);
				} else {
					card = new Image("/playingCards/adventure_back.jpg", 75, 100, true, true);
				}
				
				
				
				ImageView theCard = new ImageView(card);
				theCard.setSmooth(true);
				
				if(index == 1 || index == 2) {
					theCard.setRotate(180);
				}
				
				HBox theCardBox = new HBox();
				theCardBox.getChildren().add(theCard);
				theCardBox.setPadding(new Insets(0, -50, 0, 0));
				playerCards.getChildren().add(theCardBox);
			
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
			if(index == 3) {
				theCard.setRotate(270);
			} else if (index ==  1) {
				theCard.setRotate(90);
			}
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
		
		for(Adventure a : playingSurface) {
			Image card;
			if(a.getState() == CardStates.FACE_UP) {
				card = new Image("/playingCards/" + a.getName() + ".jpg", 75, 100, true, true);
			} else {
				card = new Image("/playingCards/adventure_back.jpg", 75, 100, true, true);
			}
			
			
			
			ImageView theCard = new ImageView(card);
			theCard.setSmooth(true);
			
			if(index == 1 || index == 2) {
				theCard.setRotate(180);
			}
			
			HBox theCardBox = new HBox();
			theCardBox.getChildren().add(theCard);
			theCardBox.setPadding(new Insets(0, -50, 0, 0));
			playedCards.getChildren().add(theCardBox);
		
		}
		if(playedCards.getChildren().isEmpty()) {
			playedCards.setMinHeight(100);
		}
		return playedCards;
	}
	
	public VBox verticalPlayedCards(List<Adventure> playingSurface, int index) {
		VBox playerCards = new VBox(-90);
		for(Adventure a : playingSurface) {
			Image card;
			if(a.getState() == CardStates.FACE_UP) {
				card = new Image("/playingCards/" + a.getName() + ".jpg", 75, 100, true, true);
			} else {
				card = new Image("/playingCards/adventure_back.jpg", 75, 100, true, true);
			}
			
			ImageView theCard = new ImageView(card);
			if(index == 3) {
				theCard.setRotate(270);
			} else if (index ==  1) {
				theCard.setRotate(90);
			}
			playerCards.getChildren().add(theCard);
		
		}
		return playerCards;
	}
	
	public HBox shields(Player p, int pIndex) {
		HBox shields = new HBox(-50);
		for(int i = 0; i < p.getShields(); i++) {
			ImageView theCard = new ImageView(new Image("/playingCards/" + p.getShieldName() + ".jpg", 75, 100, true, true));
			if(pIndex == 1) {
				theCard.setRotate(180);
			}else if (pIndex == 2){
				theCard.setRotate(180);
				
			}
			shields.getChildren().add(theCard);
		}
		return shields;
	}
	
	public VBox verticalPlayerShields(Player p, int pIndex) {
		VBox shields = new VBox(-80);
		for(int i = 0; i < p.getShields(); i++) {
			ImageView theCard = new ImageView(new Image("/playingCards/" + p.getShieldName() + ".jpg", 75, 100, true, true));
			if(pIndex == 3) {
				theCard.setRotate(270);
			}else if (pIndex == 1){
				theCard.setRotate(90);
			}
			shields.getChildren().add(theCard);
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
	
	public HBox getPlayer2Cards() {
		return player2Cards;
	}
	
	public HBox getPlayer3Cards() {
		return player3Cards;
	}
	
	public HBox getPlayer4Cards() {
		return player4Cards;
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
	
	public Background makeBground(String name) {
		Background bck = new Background(new BackgroundImage(new Image("/playingCards/" + name + ".jpg", 75, 100, true, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, true)));  
		return bck;
	}
	
	// ADDED FOR QUEST/TOURNEY PROMPT
	public boolean prompt(String type) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		if(type.equals("Tournament") || type.equals("Quest Participate")) {
			type = type.split(" ")[0];
			alert.setTitle("Participant Dialog");
			alert.setHeaderText(type + " Participant Request");
			alert.setContentText("Would you like to participate?");
		}
		else if(type.equals("Quest Sponsor") ) {
			alert.setTitle("Sponsor Dialog");
			alert.setHeaderText(type + " Request");
			alert.setContentText("Would you like to sponsor the Quest?");	
		}
		
		alert.initModality(Modality.NONE);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			return true;
		} else {
		    // ... user chose CANCEL or closed the dialog
			return false;
		}
		
		
		/*alert.setContentText("Would you like to participate?");
		alert.initModality(Modality.NONE);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
		    
			return true;
		} else {
		    // ... user chose CANCEL or closed the dialog
			return false;
		}*/
	}
	
	public int bidPrompt(int minBid, int maxBid) {
		TextInputDialog dialog = new TextInputDialog("Bid Request");
		dialog.setTitle("Maximum amount you can bid (including played ally cards): " + maxBid);
		//dialog.setHeaderText("Look, a Text Input Dialog");
		dialog.setContentText("Please enter the amount of cards you'd like to bid: " + "(max " + maxBid + ")");

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
		    System.out.println("Bid amount: " + result.get());
		}

		// The Java 8 way to get the response value (with lambda expression).
		result.ifPresent(bid -> System.out.println("Bid Amount: " + bid));
		
		if(result.isPresent() == false) {
			return -1;
		}
		
		int returnBid;
		String toParse = result.get();
		try {
			String number = "10";
			returnBid = Integer.parseInt(toParse);			
		}
		catch(NumberFormatException nfe) {
			return -1;
		}
		
		
		
		return returnBid;
	}
	
	public boolean fightingFoePrompt() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Fighting Foe");
		alert.setContentText("Please select the cards you wish to play against this stage's foe.");
		alert.initModality(Modality.NONE);
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean sponsorPrompt() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Sponsor Dialog");
		alert.setHeaderText("Sponsor Request");
		alert.setContentText("Would you like to sponsor?");
		alert.initModality(Modality.NONE);
		
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean seeCardPrompt(Player p) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		PlayGame pg = PlayGame.getInstance();
		QuestHandler qh = QuestHandler.getInstance();
		
		if(qh != null && qh.getCard() != null) {
			update(null, pg.getPlayers(), pg.getSDeck(), pg.getSDiscard(), qh.getCard());
		} else { 
			update(null, pg.getPlayers(), pg.getSDeck(), pg.getSDiscard(), null);
		}
		
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
	
	public boolean promptGameEnd(Player p) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		
		
		alert.setTitle("Player Win Dialog");
		alert.setHeaderText("Congratulations " + p.getName() + "You win!!");
		alert.setContentText("Would you like to play again?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			if(players.getPlayers().size() == 2) {
				twoPlayerStage.close();
			}if(players.getPlayers().size() == 3) {
				threePlayerStage.close();
			}if(players.getPlayers().size() == 4) {
				fourPlayerStage.close();
			}
			return true;
		} else {
			if(players.getPlayers().size() == 2) {
				twoPlayerStage.close();
			}if(players.getPlayers().size() == 3) {
				threePlayerStage.close();
			}if(players.getPlayers().size() == 4) {
				fourPlayerStage.close();
			}
			return false;
		}
		
	}
	
	private boolean cardClicked; 
	private boolean buttonClicked;
	private CustomDialog dialog;
	public boolean promptAddCardToStage(Player p) {
		cardClicked = false;
		dialog = null;
		List<Button> cards = new ArrayList<>();
		QuestHandler qh = QuestHandler.getInstance(); //For catching if there's already a test in the stage.
		for(int cardIndex = 0; cardIndex < p.getHand().size(); cardIndex++) {
			if(p.getHand().get(cardIndex) instanceof Foe || p.getHand().get(cardIndex) instanceof Test) {
				Button button = new Button();
				final int index = cardIndex;
				button.setBackground(makeBground(p.getHand().get(index).getName()));
				button.setMinWidth(75);
				button.setMinHeight(100);
				button.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						boolean testAlready = false;
						for(model.Stage s : qh.getCard().getStages()) {
							if(s.getTest() != null) {
								testAlready = true;
							}
						}
						if(testAlready && p.getHand().get(index) instanceof Test) {
							promptTestAlreadyAdded();
							dialog.close();
							promptAddCardToStage(p);
						} else {
							primStage.getScene().getRoot().setEffect(null);
							p.getHand().get(index).setState(CardStates.FACE_DOWN);
							notifyStageCardChosen(p, p.getHand().get(index));
							cardClicked = true;
							dialog.close();
						}
					}
				});
				cards.add(button);
			}
		}
		HBox foesAndTests = new HBox();
		foesAndTests.getChildren().addAll(cards);
		foesAndTests.setMaxHeight(100);
		VBox window = new VBox();
		window.setAlignment(Pos.CENTER);
		Scene scene = new Scene(window, (75 * cards.size()) + 100, 150, Color.AQUA);
		dialog = new CustomDialog("Please choose either a foe or a test", primStage, scene);
		window.getChildren().addAll(foesAndTests);
		dialog.showAndWait();
		if(cardClicked) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean promptAddWeaponsToFoe(Player p, ArrayList<Weapon> weapons) {
		//ArrayList<Weapon> weapons = new ArrayList<>();
		cardClicked = false;
		buttonClicked = false;
		VBox window = new VBox();
		List<Button> cards = new ArrayList<>();
		for(int cardIndex = 0; cardIndex < p.getHand().size(); cardIndex++) {
			if(p.getHand().get(cardIndex) instanceof Weapon) {
				Button button = new Button();
				final int index = cardIndex;
				button.setBackground(makeBground(p.getHand().get(index).getName()));
				button.setMinWidth(75);
				button.setMinHeight(100);
				button.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						boolean dup = false;
						for(Weapon w : weapons) {
							if(w.getName().equals(p.getHand().get(index).getName())) {
								dup = true;
								logger.info("Weapon Duplicate Caught! (Caught in UI)");
							}
						}
						if(!dup) {
							weapons.add((Weapon)p.getHand().get(index));
							p.getHand().get(index).setState(CardStates.FACE_DOWN);
							notifyStageWeaponChosen(p, (Weapon)p.getHand().get(index));
							dialog.close();
							cardClicked = true; 
						} else {
							promptWeaponDuplicate("Foe");
							p.getHand().get(index).setState(CardStates.FACE_UP);
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
				if(twoPlayerStage != null) {
					twoPlayerStage.getScene().getRoot().setEffect(null);
				} else if(threePlayerStage != null) {
					threePlayerStage.getScene().getRoot().setEffect(null);
				} else if(fourPlayerStage != null) {
					fourPlayerStage.getScene().getRoot().setEffect(null);
				}
				dialog.close();
				buttonClicked = true;
			}
		});
		finishedButton.setAlignment(Pos.BASELINE_RIGHT);
		finishedButton.setPrefSize(50, 20);
		HBox weaponsBox = new HBox();
		cards.add(finishedButton);
		weaponsBox.getChildren().addAll(cards);
		window.getChildren().add(weaponsBox);
		window.setAlignment(Pos.CENTER);
		Scene scene = new Scene(window, (75 * cards.size()) + 100, 150, Color.AQUA);
		dialog = new CustomDialog("Please choose the weapons you wish to play", primStage, scene);
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
		logger.info("Not enough BP enforced by UI.");
		alert.showAndWait();
	}
	
	public void promptWeaponDuplicate(String type) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Stage Error Dialog");
		alert.setHeaderText("Error: Weapon Duplicate");
		alert.setContentText(type + " already has this weapon! Please choose a different one!");

		alert.showAndWait();
	}
	
	public boolean playCards() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Play Cards");
		alert.setContentText("Please select the cards you wish to play (weapons, allies, amours)");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			notifyPlaying();
		    return true;
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
		
		return true;
	}
	
	public boolean tiePlay() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Play Cards");
		alert.setContentText("The tournament ended in a tie. Initiating sudden death round. Please select the cards you wish to play (weapons, allies, amours)");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			notifyPlaying();
		    return true;
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
		
		return true;
	}
	
	public boolean playPrompt(String name, Player p, ArrayList<Adventure> playedCards) {
		cardClicked = false;
		buttonClicked = false;
		
		
		dialog.initModality(Modality.NONE);
		
		
		VBox window = new VBox();
		List<Button> cards = new ArrayList<>();
		for(int cardIndex = 0; cardIndex < p.getHand().size(); cardIndex++) {
			if(p.getHand().get(cardIndex) instanceof Ally || p.getHand().get(cardIndex) instanceof Weapon || p.getHand().get(cardIndex) instanceof Amour || p.getHand().get(cardIndex).getName().equals("mordred")) {
				Button button = new Button();
				final int index = cardIndex;
				button.setBackground(makeBground(p.getHand().get(index).getName()));
				button.setMinWidth(75);
				button.setMinHeight(100);
				button.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						if(p.getHand().get(index).getName().equals("mordred")) {
							notifyMordredPicked(p, (Foe)p.getHand().get(index));
							cardClicked = true;
							dialog.close();
						} else {
							p.getHand().get(index).setState(CardStates.FACE_DOWN);
							notifyPlayerCardPlayed(arg0, p, p.getHand().get(index));
							dialog.close();
							cardClicked = true;
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
				if(twoPlayerStage != null) {
					twoPlayerStage.getScene().getRoot().setEffect(null);
				} else if(threePlayerStage != null) {
					threePlayerStage.getScene().getRoot().setEffect(null);
				} else if(fourPlayerStage != null) {
					fourPlayerStage.getScene().getRoot().setEffect(null);
				}
				dialog.close();
				buttonClicked = true;
			}
		});
		finishedButton.setAlignment(Pos.BASELINE_RIGHT);
		finishedButton.setPrefSize(50, 20);
		HBox alliesWeaponsAndAmour = new HBox();
		cards.add(finishedButton);
		alliesWeaponsAndAmour.getChildren().addAll(cards);
		alliesWeaponsAndAmour.setMaxHeight(100);
		window.getChildren().add(alliesWeaponsAndAmour);
		window.setAlignment(Pos.CENTER);
		window.setMaxHeight(alliesWeaponsAndAmour.getHeight());
		Scene scene = new Scene(window, (75 * cards.size()) + 100, 150, Color.AQUA);
		dialog = new CustomDialog("Please choose the cards you wish to play", primStage, scene);
		dialog.showAndWait();
		if(cardClicked) {
			if(!buttonClicked) {
				playPrompt(name, p, playedCards);
			}
			return true;
		} else {
			if(buttonClicked) {
				logger.info(p.getName() + " played 0 cards.");
				return true;
			}
			return false;
		}
	}
	
	public Player promptBid(int currBid, Player p, int iter) {
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
		
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			int bid = Integer.parseInt(result.get());
			p.bid(bid);
			if(bid <= p.getMaxBid() && bid >= currBid && iter == 0) {
				return p;
			} else if(bid <= p.getMaxBid() && bid > currBid) {
				return p;
			} else {
				promptInvalidBid(p);
				return null;
			}
		} else {
			return null;
		}
		
	}
	
	public void promptInvalidBid(Player p) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Test Card Error Dialog");
		alert.setHeaderText("Error: Bid not high enough.");
		alert.setContentText(p.getName() + " either bid too much, or did not bid high enough, they can no longer participate in the quest.");

		alert.showAndWait();
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
		Player temp = persons.get(0);
		persons.add(temp);
		persons.remove(0);
		
		//Collections.reverse(persons);
		
		reversed.setPlayers(persons);

	//	reversed.setPlayers(Collections.reverse(reversed.getPlayers()));
		
		if(twoPlayerStage != null) {
			setupFor2Players(null, reversed, game.getSDeck(), game.getSDiscard(), null);
		} else if(threePlayerStage != null) {
			setUpFor3Players(null, reversed, game.getSDeck(), game.getSDiscard(), null);
		} else if(fourPlayerStage != null) {
			setUpFor4Players(null, reversed, game.getSDeck(), game.getSDiscard(), null);
		}
		
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
	
	public void promptPlayerLost(ArrayList<Adventure> stageCards, Player p, int foeBP) {
		buttonClicked = false;
		logger.info("GUI showing that " + p.getName() + "(" + p.getBattlePoints() + ")" + " has lost the battle against " + stageCards.get(0).getName() + "(" + foeBP + ")");
		
		VBox window = new VBox();
		Label foePointsCount = new Label();
		Label playerPointsCount = new Label();
		List<Button> foeWeaponCards = new ArrayList<>();
		for(int cardIndex = 0; cardIndex < stageCards.size(); cardIndex++) {
			Button button = new Button();
			button.setBackground(makeBground(stageCards.get(cardIndex).getName()));
			button.setMinWidth(75);
			button.setMinHeight(100);
			foeWeaponCards.add(button);
		}
		List<Button> playerSurfaceCards = new ArrayList<>();
		for(int cardIndex = 0; cardIndex < p.getPlayingSurface().size(); cardIndex++) {
			Button button = new Button();
			button.setBackground(makeBground(p.getPlayingSurface().get(cardIndex).getName()));
			button.setMinWidth(75);
			button.setMinHeight(100);
			playerSurfaceCards.add(button);
		}
		Button finishedButton = new Button();
		finishedButton.setText("done");
		finishedButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				dialog.close();
			}
		});
		HBox stageCardButtons = new HBox();
		//cards.add(finishedButton);
		stageCardButtons.getChildren().addAll(foeWeaponCards);
		stageCardButtons.setMaxHeight(100);
		HBox playerSurfaceButtons = new HBox();
		//cards.add(finishedButton);
		playerSurfaceButtons.getChildren().addAll(playerSurfaceCards);
		playerSurfaceButtons.setMaxHeight(100);
		foePointsCount.setText("Number of foe battlepoints: " + foeBP);
		playerPointsCount.setText("Number of player battlepoints: " + p.getBattlePoints());
		window.getChildren().add(foePointsCount);
		window.getChildren().add(stageCardButtons);
		window.getChildren().add(playerPointsCount);
		window.getChildren().add(playerSurfaceButtons);
		window.setAlignment(Pos.CENTER);
		window.setMaxHeight(stageCardButtons.getHeight() + foePointsCount.getHeight() + playerSurfaceButtons.getHeight() + playerPointsCount.getHeight());
		
		Scene scene = new Scene(window, (75 * 10) + 100, 250, Color.AQUA);
		dialog = new CustomDialog(p.getName() + " has lost!", primStage, scene);
		dialog.showAndWait();
	}
	
	public void promptPlayerWon(ArrayList<Adventure> stageCards, Player p, int foeBP) {
		logger.info("GUI showing that " + p.getName() + "(" + p.getBattlePoints() + ")" + " has won the battle against " + stageCards.get(0).getName() + "(" + foeBP + ")");
		buttonClicked = false;
		
		VBox window = new VBox();
		Label foePointsCount = new Label();
		Label playerPointsCount = new Label();
		List<Button> foeWeaponCards = new ArrayList<>();
		System.out.println("Stage Cards size: " + stageCards.size());
		for(int cardIndex = 0; cardIndex < stageCards.size(); cardIndex++) {
			Button button = new Button();
			button.setBackground(makeBground(stageCards.get(cardIndex).getName()));
			button.setMinWidth(75);
			button.setMinHeight(100);
			foeWeaponCards.add(button);
		}
		List<Button> playerSurfaceCards = new ArrayList<>();
		for(int cardIndex = 0; cardIndex < p.getPlayingSurface().size(); cardIndex++) {
			Button button = new Button();
			button.setBackground(makeBground(p.getPlayingSurface().get(cardIndex).getName()));
			button.setMinWidth(75);
			button.setMinHeight(100);
			playerSurfaceCards.add(button);
		}
		Button finishedButton = new Button();
		finishedButton.setText("done");
		finishedButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				// TODO Auto-generated method stub
				dialog.close();
			}
		});
		HBox stageCardButtons = new HBox();
		stageCardButtons.getChildren().addAll(foeWeaponCards);
		stageCardButtons.setMaxHeight(100);
		HBox playerSurfaceButtons = new HBox();
		playerSurfaceButtons.getChildren().addAll(playerSurfaceCards);
		playerSurfaceButtons.setMaxHeight(100);
		foePointsCount.setText("Number of foe battlepoints: " + foeBP);
		playerPointsCount.setText("Number of player battlepoints: " + p.getBattlePoints());
		window.getChildren().add(foePointsCount);
		window.getChildren().add(stageCardButtons);
		window.getChildren().add(playerPointsCount);
		window.getChildren().add(playerSurfaceButtons);
		window.setAlignment(Pos.CENTER);
		window.setMaxHeight(stageCardButtons.getHeight());
		Scene scene = new Scene(window, (75 * 10) + 100, 250, Color.AQUA);
		dialog = new CustomDialog(p.getName() + " has won!", primStage, scene);
		dialog.showAndWait();
	}
	
	public ArrayList<Adventure> bidDiscardPrompt(Player p, int numCards, boolean firstTime) {
		ArrayList<Adventure> discardedCards = new ArrayList<>();
		cardClicked = false;
		buttonClicked = false;
		if(firstTime) {
			if(p.getAllies().size() > 0) { //this is to lessen the required discard count if allies are in play that have bids
				for(Ally a : p.getAllies()) {
					numCards -= a.getBids();
				}
			}
		}
		System.out.println(numCards);
		
		VBox window = new VBox();
		Label discardCountLabel = new Label();
		discardCountLabel.setText("Number of cards to discard: " + numCards);
		List<Button> cards = new ArrayList<>();
		for(int cardIndex = 0; cardIndex < p.getHand().size(); cardIndex++) {
			Button button = new Button();
			final int index = cardIndex;
			button.setBackground(makeBground(p.getHand().get(cardIndex).getName()));
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
		Scene scene = new Scene(window, (75 * cards.size()) + 100, 150, Color.AQUA);
		dialog = new CustomDialog("Please choose the cards you wish to discard", primStage, scene);
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
		System.out.println("test overflow prompt");
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Play Cards");
		alert.setContentText("Please select " + numCards + " card(s) you wish to discard");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			notifyPlaying();
		    return true;
		} else {
		    // ... user chose CANCEL or closed the dialog
		}
		
		return true;
	}
	
	
	
	
	public void promptTooManyAmour() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Too Many Amour Error Dialog");
		alert.setHeaderText("Error: Too Many Amour");
		alert.setContentText("Amour is already played, choose something else!");

		alert.showAndWait();
	}
	
	public boolean kingsCallToArmsPrompt(Player p, int count, boolean hasWeapon) {
		VBox window = new VBox();
		List<Button> btn = new ArrayList<Button>();
		Label discardCountLabel = new Label();
		if(hasWeapon) {
			discardCountLabel.setText("please discard 1 weapon");
			for (Adventure a: p.getHand()) {
				if(a instanceof Weapon) {
					Button b = new Button();	
					b.setMinWidth(75);
					b.setMinHeight(100);
					b.setBackground(makeBground(a.getName()));
					b.setOnMouseClicked(new EventHandler<MouseEvent>() {

						@Override
						public void handle(MouseEvent arg0) {
							notifyPlayerCardDiscarded(p, a, false);
							cardClicked = true;
							dialog.close();
						}
						
					});
					btn.add(b);
				}
			}
		}
		else {
			discardCountLabel.setText("please discount " + count + "foes");
			
			for (Adventure a: p.getHand()) {
				if(a instanceof Foe) {
					Button b = new Button();	
					b.setMinWidth(75);
					b.setMinHeight(100);
					b.setBackground(makeBground(a.getName()));
					b.setOnMouseClicked(new EventHandler<MouseEvent>() {
						
						@Override
						public void handle(MouseEvent arg0) {
							notifyPlayerCardDiscarded(p, a, false);
							cardClicked = true;
							dialog.close();
						}
						
					});
					btn.add(b);
				}
			}
		}
		window.getChildren().add(discardCountLabel);
		window.getChildren().addAll(btn);
		Scene scene = new Scene(window, 400, 300);
		dialog = new CustomDialog("Please choose the cards you wish to play", primStage, scene);
		dialog.showAndWait();
		
		if(cardClicked) {
			
			if(count > 1) {
				kingsCallToArmsPrompt(p, (count -= 1), hasWeapon);
			}
			return true;
		}
		else {
			return false;
		}
	}
	
	public void promptNoAdventureCardsLeft() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("No Adventure Cards Dialog");
		alert.setHeaderText("Error: No Adventure Cards left");
		alert.setContentText("There are no Adventure Cards left, sorry!");

		alert.showAndWait();
	}
	
	public void promptTestAlreadyAdded() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Stage Test Error");
		alert.setHeaderText("Error: Test already added.");
		alert.setContentText("There already exists a test in this quest, please try again!");

		alert.showAndWait();
	}
	
	public boolean promptToKillAlly(Players players, Player perp) {
		cardClicked = false;
		
		VBox window = new VBox();
		
		for(Player p : players.getPlayers()) {
			if(! p.equals(perp)) {
				Label playerLabel = new Label(p.getName());
				HBox allieCards = new HBox();
				allieCards.getChildren().add(playerLabel);
				List <Button> playerAllies = new ArrayList<Button>();
				for(Ally a : p.getAllies()) {
					Button allyButton = new Button();
					allyButton.setBackground(makeBground(a.getName()));
					allyButton.setPrefSize(75, 100);
					allyButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
	
						@Override
						public void handle(MouseEvent event) {
							// TODO Auto-generated method stub
							cardClicked = true;
							notifyPlayerCardDiscarded(p, a , true);
							dialog.close();
						}
						
					});
					playerAllies.add(allyButton);
					
				}
				allieCards.getChildren().addAll(playerAllies);
				window.getChildren().add(allieCards);
			}
		}
		window.setAlignment(Pos.CENTER);
		Scene scene = new Scene(window, window.getWidth(), 100 * players.getPlayers().size(), Color.AQUA);
		dialog = new CustomDialog("Please choose the Ally you wish to kill", primStage, scene);
		dialog.showAndWait();
		//p.getPlayers().get(index)
		return cardClicked;
	}
	
	public boolean mordredPrompt() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Mordred Dialog");
		alert.setContentText("Would you like to use Mordred's ability to delete an opponent's ally card?");	
		alert.initModality(Modality.NONE);
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			return true;
		} else {
		    // ... user chose CANCEL or closed the dialog
			return false;
		}	
	}
	
	public boolean notEnoughPrompt() {
		System.out.println("TEST PROMPT");
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Not enough cards");
		alert.setContentText("You do not have enough cards to sponsor this quest");	
		alert.initModality(Modality.NONE);
		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.OK){
			return true;
		} else {
		    // ... user chose CANCEL or closed the dialog
			return false;
		}	
	}
	
	//notify when a story card has been clicked
	public void notifyStoryCardClicked(MouseEvent event, Story card) {
		System.out.println("test view");
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
	
	//notify when a player has discarded a card
	public void notifyPlayerCardDiscarded(Player p, Adventure card, boolean onPlayingSurface) {
		if(listeners.get(0) != null) {
			listeners.get(0).onDiscardCard(p, card, onPlayingSurface);
		}
	}
	
	//notify when mordred is picked
	public void notifyMordredPicked(Player p , Foe mordred) {
		if(listeners.get(0) != null) {
			listeners.get(0).onMordredPicked(p, mordred);
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
	
	public void notifyUpdate() {
		//System.out.println("TESTSETESTSAGDSAGDSGSADGASD");
		if(listeners.get(0) != null) {
			listeners.get(0).onUpdate();
		}
	}
	
	public void notifyPlaying() {
		if(listeners.get(0) != null) {
			listeners.get(0).onPlaying();
		}
	}
	
	public void allDown() { // will set all cards face down
	}
	
	public void announceFoe() {
		
	}
}