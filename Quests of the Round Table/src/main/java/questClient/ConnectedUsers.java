package questClient;

import java.util.HashMap;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConnectedUsers extends Application {

private static Stage primStage;
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primStage = primaryStage;
		//primStage.setTitle("Game Lobby");
		primStage.show();
		
	}
	
	public void update(String user, HashMap<String, String> users, String time) {
		System.out.println("Setting up the stage");
		VBox setup = new VBox();
		Label connectedTimeLabel = new Label("Connected time: " + time);
		Label connectedUsersLabel = new Label("List of connected users: ");
		ListView<String> userListView = new ListView<>();
		ObservableList<String> userList = FXCollections.observableArrayList();
		for(Entry<String, String> entry : users.entrySet()) {
			userList.add(entry.getValue());
		}
		userListView.setItems(userList);
		setup.getChildren().add(connectedTimeLabel);
		setup.getChildren().add(connectedUsersLabel);
		setup.getChildren().add(userListView);
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				primStage.setScene(new Scene(setup, 500, 400));
				primStage.show();
			}
			
		});
	}
}
