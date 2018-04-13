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
	
	private static View gameView;
	private static final Logger logger = LogManager.getLogger(ClientGame.class);
	private static boolean isTournament;
	private static boolean isTie; // tournament tie
	private static boolean isQuest;
	private static boolean isSettingUpStage;
	private static boolean beingAskedToSponsor;
	private static boolean sponsorAskResult;
	private static boolean overflow;
	private static boolean isFoe;
	private static boolean isBidding;
	private static boolean isPlaying;
	private static Players players;
	private static StoryDeck storyDeck;
	private static StoryDiscard storyDiscard;
	private static Player currentUser;
	private static String currentUserString;
	private static String jsonUser;
	
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
				
				currentUserString = userName;
				jsonUser = "{\"name\": \"" + userName + "\"}";
				
				mapGameObjectsWithPlayers(payLoad);
				currentUser = players.getPlayers().get(0);
				
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
		
		@Override
		public void onUpdate() {
			System.out.println("update");
			for(Adventure a : players.getPlayers().get(0).getHand()) {
				System.out.println(a.getClass());
			}
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
			
			gameView.endButton.setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
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
						//TournamentHandler th = TournamentHandler.getInstance();
						//th.onEnd();
					}
					else if (isQuest) {
						if(beingAskedToSponsor) {
							beingAskedToSponsor = false;
							if(sponsorAskResult) {
								QuestClient.session.send(ClientSendingEndpoints.SPONSOR_YES, jsonUser.getBytes());
							} else {
								QuestClient.session.send(ClientSendingEndpoints.SPONSOR_NO, jsonUser.getBytes());
							}
						}
						//QuestHandler qh = QuestHandler.getInstance();
						//qh.onEnd();
					}
					else {
					}
				}
			});
			
			//QuestHandler qh = QuestHandler.getInstance();
			if(storyDeck.size() > 0 && currentUser.isFocused() && !isQuest && !overflow) {
				gameView.getStoryCards().getChildren().get(gameView.getCurrentTopStoryCardIndex()).setOnMouseClicked(new javafx.event.EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						QuestClient.session.send(ClientSendingEndpoints.STORY_DRAW, jsonUser.getBytes());
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
					System.out.println("card class: " + a.getClass());
					cardClicked(a, p);						
				}
			});
		}
	}
	
	public void subscribeToServerMessages() {
		subscribeToStoryCardDraw();
		subscribeToCardPlayed();
		subscribeToCardDiscarded();
		subscribeToParticipantAsk();
		subscribeToOverflow();
		subscribeToQuestStart();
		subscribeToSponsorAsk();
		subscribeToSponsoringStart();
		subscribeToSponsoringEnd();
		subscribeToQuestEnd();
		subscribeToTournamentStart();
		subscribeToTournamentEnd();
		subscribeToEventTrigger();
	}

	public void subscribeToOverflow() {
		QuestClient.session.subscribe(ServerSubscribeEndpoints.OVERFLOW + currentUserString, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders arg0, Object arg1) {
				overflow = true;
			}
			
		});
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
				mapGameObjectsWithPlayers(payload);
			}
		});
	}
	
	public void subscribeToCardPlayed() {
		QuestClient.session.subscribe(ServerSubscribeEndpoints.PLAYED_CARD + currentUserString, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				mapGameObjectsWithPlayers(payload);
			}
		});
	}
	
	public void subscribeToCardDiscarded() {
		QuestClient.session.subscribe(ServerSubscribeEndpoints.DISCARDED_CARD + currentUserString, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				mapGameObjectsWithPlayers(payload);
			}
		});
	}
	
	public void subscribeToParticipantAsk() {
		QuestClient.session.subscribe(ServerSubscribeEndpoints.PARTICIPANT_ASK + currentUserString, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				
			}
		});
	}
	
	public void subscribeToQuestStart() {
		QuestClient.session.subscribe(ServerSubscribeEndpoints.QUEST_START, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				System.out.println("Client received quest start");
				isQuest = true;
			}
		});
	}
	
	public void subscribeToSponsorAsk() {
		QuestClient.session.subscribe(ServerSubscribeEndpoints.SPONSOR_ASK + currentUserString, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				beingAskedToSponsor = true;
				sponsorAskResult = gameView.sponsorPrompt();
			}
		});
	}
	
	private void subscribeToSponsoringStart() {
		QuestClient.session.subscribe(ServerSubscribeEndpoints.SPONSORING_START + currentUserString, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				
			}
		});
	}

	private void subscribeToSponsoringEnd() {
		QuestClient.session.subscribe(ServerSubscribeEndpoints.SPONSORING_END + currentUserString, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				
			}
		});
	}
	
	public void subscribeToQuestEnd() {
		QuestClient.session.subscribe(ServerSubscribeEndpoints.QUEST_END, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				isQuest = false;
			}
		});
	}
	
	public void subscribeToTournamentStart() {
		QuestClient.session.subscribe(ServerSubscribeEndpoints.TOURNAMENT_START, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				System.out.println("Client received tournament start");
				isTournament = true;
			}
		});
	}
	
	public void subscribeToTournamentEnd() {
		QuestClient.session.subscribe(ServerSubscribeEndpoints.TOURNAMENT_END, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				isTournament = false;
			}
		});
	}
	
	public void subscribeToEventTrigger() {
		QuestClient.session.subscribe(ServerSubscribeEndpoints.EVENT_TRIGGERED + currentUserString, new StompFrameHandler() {
			@Override
			public Type getPayloadType(StompHeaders headers) {
				return ServerMessage.class;
			}

			@Override
			public void handleFrame(StompHeaders headers, Object payload) {
				System.out.println("Event Triggered");
				mapGameObjectsWithPlayers(payload);
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
			System.out.println("got here: " + a.getClass());
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
		System.out.println("playing card");
		if(a instanceof Amour) {
			p.remove(p.getHand(), p.getAmour(), a);
		} else if( a instanceof Ally) {
			p.remove(p.getHand(), p.getAllies(), a);
			System.out.println("hand size: " + p.getHand().size());
			System.out.println("allies size: " + p.getAllies().size());
		} else if(a instanceof Weapon) {
			p.remove(p.getHand(), p.getWeapons(), a);
		}
		List<Object> playerAndCard = new ArrayList<>();
		playerAndCard.add(p);
		playerAndCard.add(a);
		QuestClient.session.send(ClientSendingEndpoints.PLAYED_CARD, playerAndCard);
	}
	
	public void mapGameObjectsWithPlayers(Object payload) {
		System.out.println("GOT HERE");
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
			if(!currentUserString.equals(player.getName())) {
				player.setHandState(CardStates.FACE_DOWN);
			} else {
				player.setHandState(CardStates.FACE_UP);
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
		resizeStoryDeck(sDeck, players.getPlayers().size());
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
