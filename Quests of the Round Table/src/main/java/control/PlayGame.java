package control;

import javafx.application.Application;
import javafx.stage.Stage;
import view.View;

public class PlayGame extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		View view = new View();
		view.start(arg0);
	}
}