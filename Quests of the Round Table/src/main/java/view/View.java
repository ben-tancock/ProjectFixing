package view;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Adventure;
import model.AdventureDeck;
import model.Amour;

public class View extends Application {
	
	AdventureDeck adventureDeck = new AdventureDeck();
	Amour amourCards = new Amour("test", 5);
	ArrayList<Adventure> adventureCards = new ArrayList<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		//adventureDeck.addCard(8,  amourCards);
		adventureDeck.shuffle();
		Pane hbox = new HBox(5);
		for(Adventure a : adventureDeck) {
			System.out.println(a.getName());
			Image card = new Image("/playingCards/" + a.getName() + ".jpg", 100, 100, false, false);
			//Image card = new Image("/playingCards/Amour.jpg", 100, 100, false, false);
			hbox.getChildren().add(new ImageView(card));
		}
		Image card = new Image("/playingCards/Amour.jpg", 100, 100, false, false);
		hbox.getChildren().add(new ImageView(card));
		Scene scene = new Scene(hbox, 1120, 700, Color.BLUE);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Quest of the Round Table");
		primaryStage.show();
	}

}
