package view;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
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

public class View extends Application {
	
	AdventureDeck adventureDeck = new AdventureDeck();
	DeckView deckView = new DeckView();
	verticalView verticalView = new verticalView();
	ArrayList<Adventure> adventureCards = new ArrayList<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		adventureDeck.shuffle();
		BorderPane border = new BorderPane();
		HBox hbox = deckView.addHbox();
		VBox vbox = verticalView.addVBox();
		//border.setRight(vbox);
		border.setLeft(vbox);
		border.setBottom(hbox);
		//border.setTop(hbox);
		Scene scene = new Scene(border, 1120, 700,Color.GRAY);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Quest of the Round Table");
		primaryStage.show();
	}

}
