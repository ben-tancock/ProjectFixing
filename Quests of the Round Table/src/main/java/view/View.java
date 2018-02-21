package view;

import java.util.ArrayList;
import java.util.List;

import control.ControlHandler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.Adventure;
import model.AdventureDeck;
import model.Card;
import model.CardStates;
import model.Player;
import model.Players;
import model.Story;
import model.StoryDeck;
import model.StoryDiscard;
import control.PlayGame.PlayGameControlHandler;

public class View extends Application {
	
	private BorderPane border;
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
	private Stage twoPlayerStage;
	private boolean firstTime;
	private int topCard;
	
	//Declare buttons on starting page
	public Button rulesButton;
	public Button twoPlayerButton;
	public Button threePlayerButton;
	public Button fourPlayerButton;
	
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
		primaryStage.setScene(scene);
		primaryStage.setTitle("Quest of the Round Table");
		primaryStage.show();
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
		return rulesPane;
	}
	
	public StackPane storyDeck() {
		StackPane storyDeckPane = new StackPane();
		return storyDeckPane;
	}
	
	//need multiple of these for supporting different situations
	public void update(MouseEvent event, Players players, StoryDeck sDeck, StoryDiscard sDiscard) {
		if(players.getPlayers().size() == 2) {
			setupFor2Players(event, players, sDeck, sDiscard);
		} else if (players.getPlayers().size() == 3) {
			
		} else {
			
		}
	}
	
	private void setupFor2Players(MouseEvent event, Players players, StoryDeck sDeck, StoryDiscard sDiscard) {
		HBox player1Cards = new HBox();
		HBox player2Cards = new HBox();
		
		player1Cards.getChildren().add(playerCards(players.getPlayers().get(0)));			
			
		//player2Cards
		player2Cards.getChildren().add(playerCards(players.getPlayers().get(1)));
		GridPane border = new GridPane();
		border.setVgap(150);
		border.setHgap(300);
		border.add(deckView.playerRank(players.getPlayers().get(0)), 0, 0);
		border.add(player1Cards, 1, 0);
		border.add(player2Cards, 1, 2);
		border.add(deckView.playerRank(players.getPlayers().get(1)),0, 2);
		border.add(storyDeckSpace(sDeck, sDiscard), 1, 1);
		//border.setGridLinesVisible(true);

		//BorderPane.setAlignment(storyDeckCards(), Pos.CENTER_RIGHT);
		
		//Scene twoPlayerScene = new Scene(border, 1120, 700,Color.AQUA);
		if(firstTime) {
			twoPlayerStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
			firstTime = false;
		}
		twoPlayerStage.setScene(new Scene(border, 1120, 700,Color.AQUA));
		twoPlayerStage.getScene().setRoot(border);
		twoPlayerStage.show();
	}
	
	public HBox playerCards(Player player) {
		HBox playerCards = new HBox(-50);
		for(Adventure a : player.getHand()) {
			Image card;
			if(a.getState() == CardStates.FACE_UP) {
				card = new Image("/playingCards/" + a.getName() + ".jpg", 75, 100, true, true);
			} else {
				card = new Image("/playingCards/adventure_back.jpg", 75, 100, true, true);
			}
			
			ImageView theCard = new ImageView(card);
			//for 2 players for now
			if(player.getName().equals("Player 1")) {
				theCard.setRotate(180);
			}
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
	
	public HBox storyDeckSpace(StoryDeck sDeck, StoryDiscard sDiscard) {
		HBox storyDeckSpace = new HBox(10);
		storyDeckSpace.getChildren().addAll(storyDeckPile(sDeck), discardPileForStoryDeck(sDiscard));
		return storyDeckSpace;
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
			theCard.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					//notifyStoryCardClicked()
				}
				
			});
			discardPile.getChildren().add(theCard);
		}
		return discardPile;
	}
	
	public void notifyStoryCardClicked(MouseEvent event, Story card) {
		if(listeners.get(0) != null) {
			listeners.get(0).onStoryCardDraw(event);
		}
	}
}
