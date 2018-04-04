package questClient;

import java.util.HashMap;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Player;

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
	
	public void update(String user, HashMap<String, Player> users, String time) {
		System.out.println("Setting up connected users stage");
		VBox userSetup = new VBox();
		Label connectedTimeLabel = new Label("Connected Time " + time);
		Label connectedUsersLabel = new Label("List of connected Users: ");
		
		HBox userInfo = new HBox();
		final ObservableList<Image> shields = getShieldImages();
		final ObservableList<ComboBox<Image>> shieldBoxes = FXCollections.observableArrayList();
		ListView<String>userListView = new ListView<>();
		ObservableList<String> userList = FXCollections.observableArrayList();
		for(Entry<String, Player> entry : users.entrySet()) {
			userList.add(entry.getValue().getName());
			shieldBoxes.add(createShieldComboBox(shields));
		}
		userListView.setItems(userList);
		userInfo.getChildren().add(userListView);
		userInfo.getChildren().addAll(shieldBoxes);
		userSetup.getChildren().add(connectedTimeLabel);
		userSetup.getChildren().add(connectedUsersLabel);
		userSetup.getChildren().add(userInfo);
		
		System.out.println(connectedTimeLabel.getText());
		System.out.println(connectedUsersLabel.getText());
		System.out.println(userListView.getItems().toString());
		
		Button startGame = new Button("Start Game");
		startGame.setPrefSize(300, 100);
		if(user.equals(users.get("0").getName())) {
			userSetup.getChildren().add(startGame);
		}
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
			shields.add(new Image("/playingCards/shield_"+ i + ".jpg", 20, 20, true, true));
		}
		
		return shields;
	}
	
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
	
	private ComboBox<Image> createShieldComboBox(ObservableList<Image> shields) {
		ComboBox<Image> shieldCombo = new ComboBox<>();
		shieldCombo.getItems().addAll(shields);
		shieldCombo.setButtonCell(new ImageListCell());
		shieldCombo.setCellFactory(listView -> new ImageListCell());
		shieldCombo.getSelectionModel().select(0);
		return shieldCombo;
	}

}
