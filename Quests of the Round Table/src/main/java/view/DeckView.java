package view;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.Adventure;
import model.AdventureDeck;
import model.CardStates;
import model.Player;
import model.Players;

public class DeckView extends HBox {
	Players player = new Players();
	Players players = new Players();
	AdventureDeck adventureDeck;
	public DeckView() {
		adventureDeck  = new AdventureDeck();
	}
	
	//playersCards
	public HBox playerCards(int playerPosition) {
		HBox playerCards = new HBox(-50);
		
		adventureDeck.shuffle();
		players.persons.get(playerPosition).drawCard(12, adventureDeck);
		
		for(Adventure a : players.persons.get(playerPosition).getHand()) {
			Image card = new Image("/playingCards/" + a.getName() + ".jpg", 75, 100, true, true);
			ImageView theCard = new ImageView(card);
			theCard.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					System.out.println( "This is a " + a.getName());
				}
				
			});
			playerCards.getChildren().add(theCard);
		
		}
		return playerCards;
	}
	
	//playerRank
	public HBox playerRank(Player p, int index) {
		HBox hbox = new HBox();
		Image card = null;
		if(p.getRank() == 0) {
			card = new Image("/playingCards/squire.jpg", 75, 100, true, true);
		} else if(p.getRank() == 5) {
			card = new Image("/playingCards/knight.jpg", 75, 100, true, true);
		} else if(p.getRank() == 12) {
			card = new Image("/playingCards/champion_knight.jpg", 75, 100, true, true);
		}
		ImageView theCard = new ImageView(card);
		//for 2 players for now.
		if(index == 1 || index == 2) {
			theCard.setRotate(180);
		}
		hbox.getChildren().add(theCard);
		return hbox;
	}
	
	public HBox verticalPlayerRank(Player p, int index) {
		HBox hbox = new HBox();
		Image card = null;
		if(p.getRank() == 0) {
			card = new Image("/playingCards/squire.jpg", 75, 100, true, true);
		} else if(p.getRank() == 5) {
			card = new Image("/playingCards/knight.jpg", 75, 100, true, true);
		} else if(p.getRank() == 12) {
			card = new Image("/playingCards/champion_knight.jpg", 75, 100, true, true);
		} else if(p.getRank() == 22) {
			//notify win
		}
		ImageView theCard = new ImageView(card);
		//for 2 players for now.
		if(index == 3) {
			theCard.setRotate(270);
		} else if (index == 1) {
			theCard.setRotate(90);
		}
		hbox.getChildren().add(theCard);
		return hbox;
	}
	
	public void makePlayer1() {
		
	}

}
