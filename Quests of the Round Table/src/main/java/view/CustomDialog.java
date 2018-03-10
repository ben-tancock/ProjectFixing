package view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class CustomDialog extends Stage {
	private Stage dialog;
	private Node root;
	private Delta dragDelta;

	public CustomDialog(String title, Stage owner, Scene scene) {
		super(StageStyle.DECORATED);
		this.setTitle(title);
		dialog = this;
		this.initOwner(owner);
		this.initModality(Modality.WINDOW_MODAL);
		owner.getScene().getRoot().setEffect(new BoxBlur());
		
		setScene(scene);
		
		root = this.getScene().getRoot();
		root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				dragDelta.x = arg0.getSceneX();
				dragDelta.y = arg0.getSceneY();
			}
		});
		root.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				dialog.setX(event.getScreenX() - dragDelta.x);
				dialog.setY(event.getScreenY() - dragDelta.y);
			}
		});
	}
	
	
	private class Delta { double x,y; }
	
}