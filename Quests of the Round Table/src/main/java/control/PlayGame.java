package control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import view.View;

public class PlayGame extends Application{
	
	private static final Logger logger = LogManager.getLogger(PlayGame.class);

	public static void main(String[] args) {
		logger.info("Game Menu starting!");
		Application.launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		View view = new View();
		view.start(arg0);
	}
}