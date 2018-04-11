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
import model.Tournament;
import model.Weapon;
import model.pojo.PlayerPOJO;
import view.View;

public class ClientGame {
	
	private static View gameView ;
	private static final Logger logger = LogManager.getLogger(ClientGame.class);
	private static boolean isQuest;
	private static boolean isTournament;
	private static boolean isEvent;
	private static Players players;
	private static StoryDeck storyDeck;
	private static StoryDiscard storyDiscard;
	private static String currentUser;
	
	public void startGame(String userName, Stage primStage) {
		
		QuestClient.session.send("/app/startGame", "{}");
		
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
				List<Object> gameObjects = (List<Object>) ((ServerMessage)payLoad).getMessage();
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
					if(!userName.equals(player.getName())) {
						player.setHandState(CardStates.FACE_DOWN);
					}
					playersList.add(player);
				}
				resizeStoryDeck(sDeck, playersList.size());
				Players receivedPlayers = new Players();
				receivedPlayers.setPlayers(playersList);
				players = receivedPlayers;
				subscribeToStoryCardDraw();
				view.update(null, receivedPlayers, sDeck, sDiscard, null);
			}
		});
	}
	
	public void resizeStoryDeck(StoryDeck sDeck, int numPlayers) {
		sDeck.subList(sDeck.size() - sDeck.size()/numPlayers, sDeck.size()).clear();;
	}
	
	public static class PlayGameControlHandler extends ControlHandler {
		/*
		@Override
		public void onCardOverflow(Player p) {
			QuestHandler qh = QuestHandler.getInstance();
			/*while(p.getName() != players.getPlayers().get(0).getName()) {
				view.rotate(PlayGame.getInstance());
			}*/
			
			
			//overflow = true;
		/*}
		
		@Override
		public void onAdventureCardPlayed(Player p, Adventure card, MouseEvent event) {
			QuestHandler qh = QuestHandler.getInstance();
			if(card instanceof Ally) {
				p.remove(p.getHand(), p.getAllies(), card);
				//had to add this so that when the card is played, the change is instantly made.
				if(qh != null && qh.getCard() != null) {
					if(card.getName().equals("sir_percival") && qh.getCard().getName().equals("search_for_the_holy_grail")) {
						((Ally) card).setBattlePoints(20);
						System.out.println(card.getName() + "'s battlepoints are set to: " + ((Ally)card).getBattlePoints() + "(should be 20)");
					} else if(card.getName().equals("sir_gawain") && qh.getCard().getName().equals("test_of_the_green_knight")) {
						((Ally) card).setBattlePoints(20);
						System.out.println(card.getName() + "'s battlepoints are set to: " + ((Ally)card).getBattlePoints() + "(should be 20)");
					} //needs rest of allies that are changed by quests.
				}
				//needs allies that are not changed by quests.
			} else if (card instanceof Amour) {
				if(p.getAmour().size() >= 1) {
					card.setState(CardStates.FACE_UP);
					gameView.promptTooManyAmour();
				}else {
					p.remove(p.getHand(), p.getAmour(), card);
				} 
			} else if (card instanceof Weapon) {
				boolean dup = false;
				for(Weapon w : p.getWeapons()) {
					if(w.getName().equals(card.getName())) {
						dup = true;
					}
				}
				if(dup) {
					card.setState(CardStates.FACE_UP);
					gameView.promptWeaponDuplicate(p.getName() + "'s playing field");
				} else {
					p.remove(p.getHand(), p.getWeapons(), card);
				}
			}
			logger.info(p.getName() + " played " + card.getName() + " to their playing surface.");
			logger.info(p.getName() + "'s hand count: " + p.getHand().size() + " Adventure Deck Count: " + aDeck.size());
			if(qh != null && qh.getCard() != null) {
				gameView.update(event, players, sDeck, sDiscard, qh.getCard());
			} else {
				gameView.update(event, players, sDeck, sDiscard, null);
			}
		}
		
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
		
		@Override
		public void onStoryCardDraw(MouseEvent event) {
			//players.getPlayers().get(0).drawCard(sDeck, sDiscard, "boar_hunt");
			/*System.out.println("test");
			players.getPlayers().get(0).drawCard(sDeck, sDiscard);
			if(sDeck.isEmpty()) {
				onStoryDeckEmpty();
			}*/
			
			
		}/*
		
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
			gameView.update(null, players, sDeck, sDiscard, null);
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
				/*n.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						System.out.println("this is a " + players.getPlayers().get(0).getHand().get(gameView.getPlayerCards().getChildren().indexOf(n)).getName());
						Player p = players.getPlayers().get(0);
						Adventure a = players.getPlayers().get(0).getHand().get(gameView.getPlayerCards().getChildren().indexOf(n));
						cardClicked(a, p);						
					}
				});*/
				
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
			/*
			for(Node n : gameView.getPlayerSurface().getChildren()) {
				n.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent arg0) {
						System.out.println("test field card clicked");
					}
					
				});
			}
	
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
						QuestClient.session.send("/app/storyDraw", name.getBytes());
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
	}
	
	public void subscribeToStoryCardDraw() {
		QuestClient.session.subscribe("/users/storyDraw", new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				System.out.println("GOT REPLY!");
				List<Object> gameObjects = (List<Object>) ((ServerMessage)payload).getMessage();
				ObjectMapper mapper = new ObjectMapper();
				StoryDeck sDeck = mapper.convertValue(gameObjects.get(0), StoryDeck.class);
				System.out.println("storyDeck: " + sDeck);
				storyDeck = sDeck;
				StoryDiscard sDiscard = mapper.convertValue(gameObjects.get(1), StoryDiscard.class);
				System.out.println("storyDiscard: " + sDiscard);
				storyDiscard = sDiscard;
				System.out.println("Updating to show story card draw.");
				gameView.update(null, players, storyDeck, storyDiscard, null);
			}
		});
	}
}