package view;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
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
	public Button rulesButton;
	public Button twoPlayerButton;
	public Button threePlayerButton;
	public Button fourPlayerButton;
	public View() {
		rulesButton = new Button("RULES OF THE GAME");
		twoPlayerButton = new Button("TWO PLAYER QUEST");
		threePlayerButton = new Button("THREE PLAYER QUEST");
		fourPlayerButton  = new Button("FOUR PLAYER QUEST");
		
	}
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		HBox startPane = new HBox(50);
		
		//rules button
		rulesButton.setMaxSize(200, 100);
		//2 player button
		twoPlayerButton.setMaxSize(200, 100);
		//3 player button
		threePlayerButton.setMaxSize(200, 100);
		//4 player button
		fourPlayerButton.setMaxSize(200, 100);
		
		startPane.getChildren().addAll(rulesButton, twoPlayerButton, threePlayerButton, fourPlayerButton);
		
		Scene scene = new Scene(startPane, 1120, 700,Color.GRAY);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Quest of the Round Table");
		primaryStage.show();
	}
	
	//setting up the number of players
	public void PlayerSetup(int playerNumber) {
		for(int i = 0; i< playerNumber ; i++) {
			players.add();
		}
	}
	
	public StackPane storyDeck() {
		Rectangle storyDeck = new Rectangle(50, 50);
		Text text = new Text ("THIS IS THE STORY DECK....WE NEED TO FILL UP WITH STORY CARDS");
		//rect.widthProperty().bind(text.widthProperty().add(10));
		storyDeck.widthProperty().bind(text.wrappingWidthProperty().add(10));
		storyDeck.setFill(Color.TRANSPARENT);
		StackPane storyDeckPane = new StackPane();
		storyDeckPane.getChildren().addAll(storyDeck, text);
		return storyDeckPane;
	}

}
