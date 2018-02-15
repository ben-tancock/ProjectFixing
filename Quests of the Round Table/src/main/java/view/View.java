package view;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Adventure;
import model.AdventureDeck;
import model.Player;
import model.Player.Person;

public class View extends Application {
	
	private BorderPane border;
	AdventureDeck adventureDeck = new AdventureDeck();
	private HBox playerSpace;
	private HBox secondPlayerSpace;
	private VBox verticalPlayerSpace;
	
	private Scene gameTable;
	DeckView deckView = new DeckView();
	verticalView verticalView = new verticalView();
	ArrayList<Adventure> adventureCards = new ArrayList<>();
	int numberOfPlayers = 0;
	public List<Person> persons = new ArrayList<Person>();
	Player players = new Player();
	
	//declare it outside the start method to be called in Update()
	private Stage primaryStage;
	
	//Declare buttons on starting page
	public Button rulesButton;
	public Button twoPlayerButton;
	public Button threePlayerButton;
	public Button fourPlayerButton;
	
	public View() {
		border = new BorderPane();
		rulesButton = new Button("RULES OF THE GAME");
		twoPlayerButton = new Button("TWO PLAYER QUEST");
		threePlayerButton = new Button("THREE PLAYER QUEST");
		fourPlayerButton  = new Button("FOUR PLAYER QUEST");
		playerSpace = new HBox();
		secondPlayerSpace = new HBox();
		verticalPlayerSpace = new VBox();
		
		gameTable = new Scene(border, 1120, 700,Color.AQUA);
	}
	
	public HBox getPlayerSpace() {
		return playerSpace;
	}
	
	public HBox getsecondPlayerSpace() {
		return secondPlayerSpace;
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
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage = primaryStage;
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
		Scene scene = new Scene(startPane, 1120, 700,Color.GRAY);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Quest of the Round Table");
		primaryStage.show();
	}
	public HBox storyCards() {
		HBox storyCards = new HBox(-60);
		
		return storyCards;
	}
	
	//story deck with both story cards and the discard pile
	public VBox storyDeckCards() {
		VBox storyDeck = new VBox();
		
		return storyDeck;
	}
	
	public Rectangle rectangle() {
		Rectangle storyDeckRectangle = new Rectangle(50, 50);
		//storyDeck.widthProperty().bind(text.wrappingWidthProperty().add(10));
		
		storyDeckRectangle.setFill(Color.TRANSPARENT);
		return storyDeckRectangle;
	}
	
	public StackPane storyDeck() {
		StackPane storyDeckPane = new StackPane();
		//Text text = new Text ("THIS IS THE STORY DECK....WE NEED TO FILL UP WITH STORY CARDS");
		//rect.widthProperty().bind(text.widthProperty().add(10));
		//storyDeck.widthProperty().bind(text.wrappingWidthProperty().add(10));
		//storyDeck.setFill(Color.TRANSPARENT);
		
		//storyDeckPane.getChildren().addAll(storyDeck, text);
		return storyDeckPane;
	}
	
	//need multiple of these for supporting different situations
	public void update() {
		
	}

}
