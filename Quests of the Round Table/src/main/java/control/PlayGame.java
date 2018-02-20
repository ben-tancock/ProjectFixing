package control;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import model.Adventure;
import model.Player.Person;
import view.View;

public class PlayGame extends Application{
	
	//private static final Logger logger = LogManager.getLogger(PlayGame.class);

	public static void main(String[] args) {
		//logger.info("Game Menu starting!");
		Application.launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		// TODO Auto-generated method stub
		View view = new View();
		ViewController viewController = new ViewController();
		viewController.start(arg0);
	}
	
	public static class PlayGameControlHandler extends ControlHandler {
		@Override
		public void onCardOverflow(Person p) {
			System.out.println(p.getName() + " Has too many cards, must either discard or play an ally.");
			for(int i = 0; i < p.getHand().size(); i++) {
				if(p.getHand().get(i).getClass().getSimpleName().equals("Ally") && p.getHand().size() > 12) {
					p.remove(p.getHand(), p.getAllies(), p.getHand().get(i));
				}
			}
		}
	}
}