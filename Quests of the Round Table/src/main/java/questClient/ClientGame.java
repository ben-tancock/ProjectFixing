package questClient;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import com.fasterxml.jackson.databind.ObjectMapper;

import control.ControlHandler;
import control.EventHandler;
import control.PlayGame;
import control.QuestHandler;
import control.TournamentHandler;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Adventure;
import model.Ally;
import model.Amour;
import model.CardStates;
import model.Event;
import model.Foe;
import model.Person;
import model.Player;
import model.Players;
import model.Quest;
import model.Story;
import model.StoryDeck;
import model.StoryDiscard;
import model.Test;
import model.Tournament;
import model.Weapon;
import model.pojo.PlayerPOJO;
import util.ServerSubscribeEndpoints;
import view.View;

public class ClientGame {
	
	private static View gameView ;
	private static final Logger logger = LogManager.getLogger(ClientGame.class);
	private static boolean isTournament;
	private static boolean isTie; // tournament tie
	private static boolean isQuest;
	private static boolean isSettingUpStage;
	private static boolean overflow;
	private static boolean isFoe;
	private static boolean isBidding;
	private static boolean isPlaying;
	private static Players players;
	private static StoryDeck storyDeck;
	private static StoryDiscard storyDiscard;
	private static String currentUser;
	
	public void startGame(String userName, Stage primStage) {
		
		QuestClient.session.send(ClientSendingEndpoints.START_GAME, "{}");
		
	}
	
	public void startView(Object payLoad, Stage primStage, String userName) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				View view = new View();
				gameView = view;
				try {
					view.start(primStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				currentUser = userName;
				
				mapGameObjectsWithPlayers(payLoad);
				
				subscribeToServerMessages();
				
				gameView.update(null, players, storyDeck, storyDiscard, null);
			}
		});
	}
	
	public void resizeStoryDeck(StoryDeck sDeck, int numPlayers) {
		sDeck.subList(sDeck.size() - sDeck.size()/numPlayers, sDeck.size()).clear();;
	}
	
	public static class PlayGameControlHandler extends ControlHandler {
		
		@Override
		public void onCardOverflow(Player p) {
			overflow = true;
		}
		/*
		@Override
		public void onPlayerVictory(Player p) {
			System.out.println(p.getName() + " has become knight of the round table!");
			winners.add(p);
		}
		
		@Override
		public void onMordredPicked(Player perp, Foe mordred) {
		//	perp.discard(mordred, discardPile, onPlaySurface);
			onDiscardCard(perp, mordred, false);
			gameView.promptToKillAlly(players, perp);
		}*/
		/*
		@Override
		public void onStoryDeckEmpty() {
			QuestHandler qh = QuestHandler.getInstance();
			Story topCard = sDiscard.get(sDiscard.size() - 1);
			sDeck = new StoryDeck();
			sDiscard = new StoryDiscard();
			sDiscard.add(topCard);
			sDeck.shuffle();
			if(qh != null && qh.getCard() != null) {
				gameView.update(null, players, sDeck, sDiscard, qh.getCard());
			} else { 
				gameView.update(null, players, sDeck, sDiscard, null);
			}
		}
		
		@Override
		public void onAdventureDeckEmpty(Player p, int cardsLeftToDraw) {
			if(aDiscard.size() > 0) {
				aDeck.addAll(aDiscard);
				aDeck.shuffle();
				aDiscard.removeAll(aDiscard);
				
				System.out.println("Adventure Deck emptied, cards left to draw: " + cardsLeftToDraw);
				p.drawCard(cardsLeftToDraw, aDeck);
			}
			else {
				/* For Testing only
				int allyCount = 0;
				for(Adventure a : p.getHand()) {
					if(a instanceof Ally) {
						allyCount++;
						System.out.println(a.getName());
					}
				}
				System.out.println("number of allies in " + p.getName() + "'s hand: " + allyCount);
				//--------------------------------------------------------------------------------*/
			/*	
				gameView.promptNoAdventureCardsLeft();
			}
		}*/
		/*
		@Override
		public void onQuestCardDraw(Player p) {
			isQuest = true;
			Story topCard = sDiscard.get(sDiscard.size() - 1);
			QuestHandler questHandler = new QuestHandler((Quest)topCard, players, p, PlayGame.getInstance(), aDeck, aDiscard);
			gameView.update(null, players, sDeck, sDiscard, (Quest) topCard);
			System.out.println("Quest: " + topCard.getName());
			try {
				questHandler.playQuest();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void onEventCardDraw(Player p) {
			PlayGame pg = PlayGame.getInstance();
			Story topCard = sDiscard.get(sDiscard.size() - 1);
			EventHandler eventHandler = new EventHandler((Event)topCard, p, players, aDeck, aDiscard);
			gameView.update(null, players, sDeck, sDiscard, null);
			System.out.println("Event: " + topCard.getName());
			//so that the view rotates back to the player who drew the tournament card for the game to resume to the player on the left.
			while(!pg.getPlayers().getPlayers().get(0).equals(p)) {
				pg.getView().rotate(pg);
			}
		}
		
		@Override
		public void onTournamentCardDraw(Player p) {
			//System.out.println("test tourn draw");
			isTournament = true;
			PlayGame pg = PlayGame.getInstance();
			gameView.update(null, players, sDeck, sDiscard, null);
			Story topCard = sDiscard.get(sDiscard.size() - 1);
			TournamentHandler tourneyHandler = new TournamentHandler((Tournament)topCard, PlayGame.getInstance(), p);
			System.out.println("Tournament: " + topCard.getName());
			try {
				tourneyHandler.playTournament();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//so that the view rotates back to the player who drew the tournament card for the game to resume to the player on the left.
			/*while(!pg.getPlayers().getPlayers().get(0).equals(p)) {
				pg.getView().rotate(pg);
			}*/
			//isTournament = false;
		/*}
		
		@Override
		public void onDiscardCard(Player p, Adventure card, boolean onPlayingSurface) {
			QuestHandler qh = QuestHandler.getInstance();
			if(onPlayingSurface) {
				if(card instanceof Ally) {
					p.remove(p.getAllies(), aDiscard, card);
				} else if(card instanceof Amour) {
					p.remove(p.getAmour(), aDiscard, card);
				} else if(card instanceof Weapon) {
					p.remove(p.getWeapons(), aDiscard, card);
				}
			} else {
				p.remove(p.getHand(), aDiscard, card);
			}
			card.setState(CardStates.FACE_DOWN);
			p.setHandState(CardStates.FACE_DOWN);
			if(qh != null && qh.getCard() != null) {
				gameView.update(null, players, sDeck, sDiscard, qh.getCard());
			} else {
				gameView.update(null, players, sDeck, sDiscard, null);
			}
			logger.info(p.getName() + " discarded a card.");
			logger.info(p.getName() + "'s hand count: " + p.getHand().size() + " Adventure Deck Count: " + aDeck.size() + " Adventure Discard Count: " + aDiscard.size());
		}
		
		
		@Override
		public void onRankSet(Player p) {
			QuestClient.session.send("/app/rankset", p);
		}
		
		@Override
		public void onKingsRecognition() {
			logger.info("King's Recognition set by controller to true, the next player to finish a quest will receive 2 bonus shields.");
			PlayGame.setKingsRecognition(true);
		}
		
		public void onPlaying() {
			System.out.println("test playing");
			isPlaying = true;
		}*/
		
		@Override
		public void onUpdate() {
			System.out.println("update");
			for(Node n : gameView.getPlayerCards().getChildren()) {
				catchCardClick(n);
				allowCardsToBeViewedHorizontal(n);
			}
			
			for(Node n : gameView.getPlayerSurface().getChildren()) {
				n.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						System.out.println("test field card clicked");
					}
				});
			}
			/*
			view.endButton.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					// TODO Auto-generated method stub
					for(Weapon w : players.getPlayers().get(0).getWeapons()) {
						w.setState(CardStates.FACE_DOWN);
					}
					for(Amour a : players.getPlayers().get(0).getAmour()) {
						a.setState(CardStates.FACE_DOWN);
					}
					System.out.println("test end turn button");
					isPlaying = false;
					
					if(isTournament) {
						TournamentHandler th = TournamentHandler.getInstance();
						th.onEnd();
					}
					else if (isQuest) {
						QuestHandler qh = QuestHandler.getInstance();
						qh.onEnd();
					}
					else {
						System.out.println("NORMAL ROTATE");
						view.rotate(PlayGame.getInstance());
						//doTurn(players.getPlayers().get(0));
					}
					doTurn(players.getPlayers().get(0));
				}
			});*/
			
			//QuestHandler qh = QuestHandler.getInstance();
			if(storyDeck.size() > 0) {
				gameView.getStoryCards().getChildren().get(gameView.getCurrentTopStoryCardIndex()).setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						String name = "{\"name\": \"" + currentUser + "\"}";
						QuestClient.session.send(ClientSendingEndpoints.STORY_DRAW, name.getBytes());
						/*gameView.notifyStoryCardClicked(arg0, sDeck.get(gameView.getCurrentTopStoryCardIndex()));
						
						if(winners.size() == 1) {
							System.out.println("Congratulations " + winners.get(0).getName() + "! You Win!");
							boolean gameRestart = view.promptGameEnd(winners.get(0));
							if(gameRestart) {
								primStage.close();
								try {
									PlayGame playGame = new PlayGame();
									playGame.start(primStage);
									return;
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else {
								primStage.close();
								return;
							}
						} else if (winners.size() > 1) {
							//begin final tournament!
						}
						if(qh != null && qh.getCard() != null) {
							gameView.update(null, players, sDeck, sDiscard, qh.getCard());
						} else { 
							gameView.update(null, players, sDeck, sDiscard, null);
						}*/
					}
				
				}); 
			}
		}
		
		
		public void catchCardClick(Node n) {
			n.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					System.out.println("this is a " + players.getPlayers().get(0).getHand().get(gameView.getPlayerCards().getChildren().indexOf(n)).getName());
					Player p = players.getPlayers().get(0);
					Adventure a = players.getPlayers().get(0).getHand().get(gameView.getPlayerCards().getChildren().indexOf(n));
					cardClicked(a, p);						
				}
			});
		}
	}
	
	public void subscribeToServerMessages() {
		subscribeToStoryCardDraw();
		subscribeToCardPlayed();
	}
	
	public void subscribeToStoryCardDraw() {
		QuestClient.session.subscribe(ServerSubscribeEndpoints.STORY_DRAW, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				System.out.println("GOT REPLY!");
				mapGameObjectsWithoutPlayers(payload);
			}
		});
	}
	
	public void subscribeToCardPlayed() {
		QuestClient.session.subscribe("/users/cardPlayed", new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				
			}
		});
	}
	
	public static void cardClicked(Adventure a, Player p) {
		System.out.println("bp before ally check: " + p.getBattlePoints());
		
		if(overflow) {
			if(a instanceof Weapon) {
				discardCard(p, a);
			}
			else if (a instanceof Foe) {
				discardCard(p, a);
			}
			else if (a instanceof Amour) {
				if(p.getAmour().size() == 0) {
					playCard(p, a);
				} else {
					gameView.promptTooManyAmour();
				}
			}
			else if(a instanceof Ally) {
				playCard(p, a);
			}
			
			// when an overflow is done, a prompt MAY follow it, but the prompt needs to wait until the overflow is done
			// for now the only solution i can think of is moving all the prompts to happen after overflow and on doTurn
			if(p.getHand().size() - 12 <= 0) {
				System.out.println("test end overflow");
				overflow = false;
				if(isTournament) {
					//prompt 
				}
				else if (isQuest) {
					//prompt
				}
				else if (isBidding) {
					
				}
				//doTurn(p);
			}
		}
		else if(a.getName().equals("mordred")) {
			if(gameView.mordredPrompt() == true) {
				doMordred(p);
			}
		}
		else if(isPlaying) {
			System.out.println("test is playing execute");
			if(a instanceof Weapon) {
				boolean weaponCheck = false;
				for(Weapon w : p.getWeapons()) {
					if(w.getName().equals(a.getName())) {
						weaponCheck = true;
						break;
					}
				}
				if(weaponCheck) {
					gameView.promptWeaponDuplicate(p.getName());
				} else {
					playCard(p, a);
				}
			}
			else if (a instanceof Amour) {
				if(p.getAmour().size() == 0) {
					playCard(p, a);
				} else {
					gameView.promptTooManyAmour();
				}
			}
			else if(a instanceof Ally) {
				playCard(p, a);
			}
			else {
				
			}
			
		}
		else if(isBidding) {
			
		}
		else if(isSettingUpStage) {
			QuestHandler qh = QuestHandler.getInstance();
			if(qh.getCard().getStages().size() == qh.getCurrentStage() && qh.getCard().getStages().size() < qh.getCard().getNumStages() && !qh.getFoeSelected()) {
				if(a instanceof Foe) {
					//stage counter increased only after the foe has chosen weapons
					qh.setSelectedFoe((Foe)a);
					qh.setFoeSelected(true);
					((HBox)gameView.getPlayerCards().getChildren().get(p.getHand().indexOf(a))).setTranslateY(-50);
					((HBox)gameView.getPlayerCards().getChildren().get(p.getHand().indexOf(a))).translateYProperty();
				} else if(a instanceof Test) {
					p.getHand().remove(a);
					model.Stage stage = new model.Stage((Test)a);
					qh.getCard().addStage(stage);
					//gameView.update(null, players, sDeck, sDiscard, qh.getCard());
					List<Object> playerAndCard = new ArrayList<>();
					playerAndCard.add(p);
					playerAndCard.add(a);
					QuestClient.session.send("/app/sponsoredCard", playerAndCard);
					qh.nextStage();
					qh.onEnd();
				}
			} else if(qh.getCard().getStages().size() == qh.getCurrentStage() && qh.getCard().getStages().size() < qh.getCard().getNumStages() && qh.getFoeSelected()){
				if(a instanceof Weapon) {
					((HBox)gameView.getPlayerCards().getChildren().get(p.getHand().indexOf(a))).setTranslateY(-50);
					((HBox)gameView.getPlayerCards().getChildren().get(p.getHand().indexOf(a))).translateYProperty();
					qh.getSelectedWeapons().add((Weapon)a);
				}
			}
		}
		else {
			if(a instanceof Ally) {
				System.out.println("card bp: " + ((Ally) a).getBattlePoints());
				playCard(p, a);
			} else if (a instanceof Foe && a.getName().equals("mordred")) {
				//play mordred
				doMordred(p);
			}
		}
		//allyCheck(p);
		System.out.println("bp after ally check: " + p.getBattlePoints());
	}
	
	public static void doMordred(Player p) {
		// TO DO: have event handlers for ALL players Ally cards in play
		// have all players ally cards do that bring to front thing to make them easier to see
		for(int i = 0; i < players.getPlayers().size(); i++) {
			Player q = players.getPlayers().get(i);
			
			if(i == 1) {
				setBehaviour(gameView.getPlayer2Surface(), q);
			}
			
			else if(i == 2) {
				setBehaviour(gameView.getPlayer3Surface(), q);
			}
			
			else if(i == 3) {
				setBehaviour(gameView.getPlayer4Surface(), q);
			}	
		}
	}
	
	public static void setBehaviour(HBox field, Player p) {
		for(Node n : field.getChildren()) {
			fieldCardClick(n, field, p);
			allowCardsToBeViewedHorizontal(n);
		}
	}
	
	public static void setBehaviour(VBox field, Player p) {
		for(Node n : field.getChildren()) {
			fieldCardClick(n, field, p);
			allowCardsToBeViewedVertical(n); 
		}
	}
	
	//TODO: need to fix this in the future
	public static void allowCardsToBeViewedHorizontal(Node n) {
		n.setOnMouseEntered(new javafx.event.EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("Moving Card");
				((HBox)n).setPadding(new Insets(0, 0, 0, 0));
			}
		});
		n.setOnMouseExited(new javafx.event.EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("Moving Card Back");
				((HBox)n).setPadding(new Insets(0, -50, 0, 0));	
			}
		});
	}
	
	//TODO: need to add functionality to this in the future
	public static void allowCardsToBeViewedVertical(Node n) {
		
	}
	
	//TODO: need to finish this
	public static void fieldCardClick(Node n, Pane field, Player p) {
		n.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("test field card clicked: Player " + p.getName());
				int i = field.getChildren().indexOf(n);
				System.out.println("test field card clicked: " + p.getHand().get(i).getName());
				//p.remove(p.getAllies(), aDiscard, p.getAllies().get(i));
				//view.update(null, players, sDeck, sDiscard, null);
			}
		});
	}
	
	public static void discardCard(Player p, Adventure a) {
		List<Object> playerAndCard = new ArrayList<>();
		playerAndCard.add(p);
		playerAndCard.add(a);
		QuestClient.session.send(ClientSendingEndpoints.DISCARDED_CARD, playerAndCard);
	}
	
	public static void playCard(Player p, Adventure a) {
		p.remove(p.getHand(), p.getAmour(), a);
		List<Object> playerAndCard = new ArrayList<>();
		playerAndCard.add(p);
		playerAndCard.add(a);
		QuestClient.session.send(ClientSendingEndpoints.PLAYED_CARD, playerAndCard);
	}
	
	public void mapGameObjectsWithPlayers(Object payload) {
		List<Object> gameObjects = (List<Object>) ((ServerMessage)payload).getMessage();
		ObjectMapper mapper = new ObjectMapper();
		StoryDeck sDeck = mapper.convertValue(gameObjects.get(0), StoryDeck.class);
		storyDeck = sDeck;
		StoryDiscard sDiscard = mapper.convertValue(gameObjects.get(1), StoryDiscard.class);
		storyDiscard = sDiscard;
		List<HashMap<String, String>> receivedMap = (List<HashMap<String, String>>)gameObjects.get(2);
		List<Player> playersList = new ArrayList<>();
		for(int i = 0; i < receivedMap.size(); i++) {
			System.out.println(receivedMap.get(i));
			PlayerPOJO pojo = mapper.convertValue(receivedMap.get(i), PlayerPOJO.class);
			Player player = new Person();
			player = player.fromPOJO(pojo);
			if(!currentUser.equals(player.getName())) {
				player.setHandState(CardStates.FACE_DOWN);
			}
			playersList.add(player);
		}
		resizeStoryDeck(sDeck, playersList.size());
		Players receivedPlayers = new Players();
		receivedPlayers.setPlayers(playersList);
		players = receivedPlayers;
		updateView();
	}
	
	public void mapGameObjectsWithoutPlayers(Object payload) {
		List<Object> gameObjects = (List<Object>) ((ServerMessage)payload).getMessage();
		ObjectMapper mapper = new ObjectMapper();
		StoryDeck sDeck = mapper.convertValue(gameObjects.get(0), StoryDeck.class);
		System.out.println("storyDeck: " + sDeck);
		storyDeck = sDeck;
		StoryDiscard sDiscard = mapper.convertValue(gameObjects.get(1), StoryDiscard.class);
		System.out.println("storyDiscard: " + sDiscard);
		storyDiscard = sDiscard;
		updateView();
	}
	
	public static void updateView() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				gameView.update(null, players, storyDeck, storyDiscard, null);
			}
		});
	}
}
