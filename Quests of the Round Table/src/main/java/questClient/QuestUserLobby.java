package questClient;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.CardStates;
import model.Player;
import model.Person;
import model.Players;
import model.Story;
import model.StoryDeck;
import model.StoryDiscard;
import model.pojo.PlayerPOJO;
import view.View;

public class QuestUserLobby extends Application {

	private static Stage primStage;
	private String userName;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primStage = primaryStage;
		primStage.setTitle("Game Lobby");
		primStage.show();
		
	}
	//main method
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	public void update(String user, HashMap<String, Player> users, String time) {
		System.out.println("Setting up connected users stage");
		VBox userSetup = new VBox();
		Label connectedTimeLabel = new Label("Connected Time " + time);
		Label connectedUsersLabel = new Label("List of connected Users: ");
		userName = user;
		System.out.println("USER = " + userName);
		HBox userInfo = new HBox();
		final ObservableList<Image> shields = getShieldImages();
		final ObservableList<ImageView> shieldBoxList = FXCollections.observableArrayList();
		ListView<HBox>userListView = new ListView<>();
		ObservableList<HBox> userList = FXCollections.observableArrayList();
		for(Entry<String, Player> entry : users.entrySet()) {
			HBox listBox = new HBox(5);
			listBox.getChildren().add(new Label(entry.getValue().getName()));
			listBox.getChildren().add(new ImageView(shields.get(Integer.parseInt(entry.getKey()))));
			userList.add(listBox);
		}
		userListView.setItems(userList);
		userInfo.getChildren().add(userListView);
		userInfo.getChildren().addAll(shieldBoxList);
		userSetup.getChildren().add(connectedTimeLabel);
		userSetup.getChildren().add(connectedUsersLabel);
		userSetup.getChildren().add(userInfo);
		
		System.out.println(users.get("0").getShields());
		System.out.println(users.get("0").getRankString());
		
		System.out.println(connectedTimeLabel.getText());
		System.out.println(connectedUsersLabel.getText());
		System.out.println(userListView.getItems().toString());
		
		subscribeToStartGame();
		
		Button startGame = new Button("Start Game");
		startGame.setPrefSize(300, 100);
		startGame.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				startGame(users.size());
			}
		});
		if(user.equals(users.get("0").getName())) {
			userSetup.getChildren().add(startGame);
		}
		showStage(userSetup);
	}
	
	public void showStage(VBox userSetup) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				primStage.setScene(new Scene(userSetup, 800, 600));
				primStage.show();	
			}
		});
	}
	
	public ObservableList<Image> getShieldImages() {
		final ObservableList<Image> shields = FXCollections.observableArrayList();
		
		for(int i = 1; i < 5; i++) {
			shields.add(new Image("/playingCards/shield_"+ i + ".jpg", 50, 50, true, true));
		}
		
		return shields;
	}
	
	public void startGame(int numPlayers) {
		if(numPlayersBetween2And4(numPlayers)) {
			ClientGame clientGame = new ClientGame();
			clientGame.startGame(userName, primStage);
		}
	}
	
	public boolean numPlayersBetween2And4(int numPlayers) {
		return numPlayers > 1 && numPlayers < 5;
	}
	
	boolean started;
	public void subscribeToStartGame() {
		QuestClient.session.subscribe("/users/startGame-" + userName, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payLoad) {
				System.out.println("GOT START GAME REPLY");
				if(!started) {
					ClientGame clientGame = new ClientGame();
					clientGame.startView(payLoad, primStage, userName);
					started = true;
				}
			}
		});
	}
	
	/* For use if we get there...
	class ImageListCell extends ListCell<Image> {
		private final ImageView view;
		
		ImageListCell() {
			setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
			view = new ImageView();
		}
		
		@Override
		protected void updateItem(Image item, boolean empty) {
			super.updateItem(item, empty);
			
			if(item == null || empty) {
				setGraphic(null);
			} else {
				view.setImage(item);
				setGraphic(view);
			}
		}
	}
	
	private ComboBox<Image> createShieldComboBox(ObservableList<Image> shields, int i) {
		ComboBox<Image> shieldCombo = new ComboBox<>();
		shieldCombo.getItems().addAll(shields);
		shieldCombo.setButtonCell(new ImageListCell());
		shieldCombo.setCellFactory(listView -> new ImageListCell());
		shieldCombo.getSelectionModel().select(i);
		return shieldCombo;
	}*/
	
	
}
