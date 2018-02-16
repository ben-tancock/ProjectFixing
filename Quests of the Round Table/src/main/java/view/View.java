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
import javafx.scene.text.Font;
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
	private HBox thirdPlayerSpace;
	private HBox fourthPlayerSpace;
	private VBox verticalPlayerSpace;
	
	private Scene gameTable;
	DeckView deckView = new DeckView();
	verticalView verticalView = new verticalView();
	ArrayList<Adventure> adventureCards = new ArrayList<>();
	int numberOfPlayers = 0;
	public List<Person> persons = new ArrayList<Person>();
	Player players = new Player();
	
	//declare it outside the start method to be called in Update()
	private Stage primaryStage; //y do we need this pls....
	
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
		thirdPlayerSpace = new HBox();
		fourthPlayerSpace = new HBox();
		
		gameTable = new Scene(border, 1120, 700,Color.AQUA);
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
		Scene scene = new Scene(startPane, 1220, 700,Color.BISQUE);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Quest of the Round Table");
		primaryStage.show();
	}
	public HBox storyCards() {
		HBox storyCards = new HBox(-50);
		
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
	public void update() {
		
	}

}
