package questClient;

import java.util.HashMap;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class QuestUserLobby extends Application {

	private static Stage primStage;
	
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
	
	public void update(String user, HashMap<String, String> users, String time) {
		VBox userSetup = new VBox();
		Label connectedTime = new Label("Connected Time " + time);
		Label connectedUsers = new Label("List of connected Users: ");
		ListView<String>userListView = new ListView<>();
		ObservableList<String> userList = FXCollections.observableArrayList();
		
		for(Entry<String, String> entry : users.entrySet()) {
			userList.add(entry.getValue());
		}
		userListView.setItems(userList);
		userSetup.getChildren().addAll(connectedTime, connectedUsers, userListView);
		
		Button startGame = new Button("Start Game");
		startGame.setPrefSize(300, 100);
		if(user.equals(users.get(0))) {
			userSetup.getChildren().add(startGame);
		}
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				primStage.setScene(new Scene(userSetup, 800, 600, Color.BISQUE));
				primStage.show();
				
			}
			
		});
	}

}
