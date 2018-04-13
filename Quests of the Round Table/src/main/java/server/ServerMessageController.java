package server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpAttributes;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.broker.SubscriptionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.fasterxml.jackson.databind.ObjectMapper;

import control.EventHandler;
import control.PlayGame;
import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Ally;
import model.Amour;
import model.CardStates;
import model.Event;
import model.Foe;
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

@Controller
public class ServerMessageController implements ApplicationListener<SessionDisconnectEvent>{
	
	private static final Logger logger = LogManager.getLogger(ServerMessageController.class);

	ConcurrentHashMap<Integer, Player> users;
	ArrayList<String> shieldNames;
	
	private SimpMessageSendingOperations template;
	private Players players;
	private int userCounter;
	private AdventureDeck aDeck;
	private StoryDeck sDeck;
	private AdventureDiscard aDiscard;
	private StoryDiscard sDiscard;
	private ConnectMessage connectMessage;
	private List<Player> potentialSponsors;
	private SubscriptionRegistry registry;
	private Quest currentQuest;
	private int currentFocusIndex;
	private int numUsersLeft;
	
	public ServerMessageController(SimpMessageSendingOperations template) {
		this.template = template;
		users = new ConcurrentHashMap<Integer, Player>();
		players = new Players();
		shieldNames = new ArrayList<>();
		userCounter = 0;
		aDeck = new AdventureDeck();
		aDeck.shuffle();
		aDiscard = new AdventureDiscard();
		sDeck = new StoryDeck();
		sDeck.shuffle();
		sDiscard = new StoryDiscard();
		potentialSponsors = new ArrayList<>();
		currentFocusIndex = 0;
		currentQuest = null;
		numUsersLeft = 0;
	}
	
	@MessageMapping(ServerMaps.REGISTER)
	@SendTo(ServerSubscribeEndpoints.REGISTER)
	public ServerMessage connect(ConnectMessage message, @Header("simpSessionId") String sessionId) {
		System.out.println(message.getName() + " connected.");
		players.addHuman();
		players.getPlayers().get(currentFocusIndex).setFocused(true);
		players.getPlayers().get(userCounter).setName(message.getName());
		players.getPlayers().get(userCounter).setShieldName("shield_" + (userCounter + 1));
		players.getPlayers().get(userCounter).drawCard(12, aDeck);
		users.put(Integer.valueOf(userCounter), players.getPlayers().get(userCounter));
		ServerMessage serverMessage = new ServerMessage(users);
		userCounter++;
		connectMessage = message;
		return serverMessage;
	}
	
	public Player fetchPlayer(String name) {
		for(Player p : players.getPlayers()) {
			if(p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
	
	/*
	@MessageMapping("/selectShield")
	@SendTo("/users/shieldChange")
	public ServerMessage shieldChange(ConnectMessage message, @Header("simpSessionId") String sessionId) {
		Player user = users.get(sessionId);
		
		return null;
	}*/
	
	@MessageMapping(ServerMaps.START_GAME)
	public void startGame() {
		System.out.println("STARTING GAME FROM SERVER");
		
		mapGameStuffWithPlayersAndSend(ServerSubscribeEndpoints.START_GAME);
	}
	
	public ArrayList<PlayerPOJO> rotate(ArrayList<PlayerPOJO> sendingList) {
		PlayerPOJO temp = sendingList.get(0);
		sendingList.add(temp);
		sendingList.remove(0);
		return sendingList;
	}
	
	@MessageMapping(ServerMaps.STORY_DRAW)
	public void drawStoryCard(ConnectMessage message) {
		Player storyCardDrawer = fetchPlayer(message.getName());
		Player nextPlayer = players.getPlayers().get((players.getPlayers().indexOf(storyCardDrawer) + 1) % players.getPlayers().size());
		storyCardDrawer.setFocused(false);
		nextPlayer.setFocused(true);
		storyCardDrawer.drawCard(sDeck, sDiscard);
		
		mapGameStuffWithPlayersAndSend(ServerSubscribeEndpoints.STORY_DRAW);
		
		Story topDiscardCard = sDiscard.get(sDiscard.size() - 1);
		System.out.println(topDiscardCard.getClass());
		ServerMessage serverMessage = new ServerMessage("");
		if(topDiscardCard instanceof Quest) {
			System.out.println("sending quest start");
			currentQuest = (Quest)topDiscardCard;
			template.convertAndSend(ServerSubscribeEndpoints.QUEST_START, serverMessage);
			potentialSponsors.clear();
			potentialSponsors.addAll(players.getPlayers());
			askingForSponsor(storyCardDrawer);
		} else if (topDiscardCard instanceof Tournament) {
			System.out.println("sending tournament start");
			template.convertAndSend(ServerSubscribeEndpoints.TOURNAMENT_START, serverMessage);
		} else if (topDiscardCard instanceof Event) {
			eventTriggerResponse(topDiscardCard.getName(), storyCardDrawer);
		}
		
		
	}
	
	@MessageMapping(ServerMaps.PLAYED_CARD)
	public void playCard(List<Object> playerAndCard) {
		ObjectMapper mapper = new ObjectMapper();
		PlayerPOJO playerSent = mapper.convertValue(playerAndCard.get(0), PlayerPOJO.class);
		for(Player p : players.getPlayers()) {
			if(p.getName().equals(playerSent.getName())) {
				p.getHand().clear();
				p.getHand().addAll(playerSent.getHand());
				p.getAllies().clear();
				p.getAllies().addAll(playerSent.getAllies());
				p.getAmour().clear();
				p.getAmour().addAll(playerSent.getAmour());
				p.getWeapons().clear();
				p.getWeapons().addAll(playerSent.getWeapons());
				break;
			}
		}
		mapGameStuffWithPlayersAndSend(ServerSubscribeEndpoints.PLAYED_CARD);
	}
	
	public void askingForSponsor(Player p) {
		ServerMessage serverMessage = new ServerMessage("");
		template.convertAndSend(ServerSubscribeEndpoints.SPONSOR_ASK, serverMessage);
	}
	
	@MessageMapping(ServerMaps.SPONSOR_YES)
	public void sponsorYes(ConnectMessage message) {
		potentialSponsors.clear();
		Player sponsor = fetchPlayer(message.getName());
		
	}
	
	@MessageMapping(ServerMaps.SPONSOR_NO)
	public void sponsorNo(ConnectMessage message) {
		Player currentAsk = fetchPlayer(message.getName());
		Player nextPlayer = players.getPlayers().get((players.getPlayers().indexOf(currentAsk) + 1) % players.getPlayers().size());
		potentialSponsors.remove(currentAsk);
		if(potentialSponsors.isEmpty()) {
			ServerMessage serverMessage = new ServerMessage("");
			template.convertAndSend(ServerSubscribeEndpoints.QUEST_END, serverMessage);
		} else {
			askingForSponsor(nextPlayer);
		}
	}

	@Override
	public void onApplicationEvent(SessionDisconnectEvent disconnectEvent) {
		System.out.println("Client disconnected: " + (Integer.parseInt(disconnectEvent.getSessionId()) - numUsersLeft));
		users.remove(Integer.parseInt(disconnectEvent.getSessionId()) - numUsersLeft);
		players.getPlayers().remove(Integer.parseInt(disconnectEvent.getSessionId()) - numUsersLeft);
		userCounter--;
		System.out.println(users.size());
		System.out.println(players.getPlayers().size());
		ServerMessage serverMessage = new ServerMessage(users);
		numUsersLeft++;
		template.convertAndSend(ServerSubscribeEndpoints.REGISTER, serverMessage);
	}
	
	public ServerMessage mapGameStuffWithoutPlayers() {
		List<Object> gameStuff = new ArrayList<>();
		gameStuff.add(sDeck);
		gameStuff.add(sDiscard);
		ServerMessage serverMessage = new ServerMessage(gameStuff);
		return serverMessage;
	}
	
	public void mapGameStuffWithPlayersAndSend(String endpoint) {
		List<Object> gameStuff = new ArrayList<>();
		ArrayList<PlayerPOJO> sendingPlayers = new ArrayList<>();
		for(Player p : players.getPlayers()) {
			PlayerPOJO pPojo = new PlayerPOJO(p.getName(), p.getRankString(), p.getDealer(), p.isFocused(),
					p.getShields(), p.getShieldName(), p.getHand(), p.getAllies(), p.getWeapons(), 
					p.getAmour(), p.getBid(), p.getABP());
			sendingPlayers.add(pPojo);
		}
		
		gameStuff.add(sDeck);
		System.out.println(sDeck.size());
		gameStuff.add(sDiscard);
		gameStuff.add(sendingPlayers);
		ServerMessage serverMessage = new ServerMessage(gameStuff);
		
		for(int i = 0; i < sendingPlayers.size(); i++) {
			template.convertAndSend(endpoint + users.get(i).getName(), serverMessage);
			gameStuff.remove(sendingPlayers);
			sendingPlayers = rotate(sendingPlayers);
			gameStuff.add(sendingPlayers);
		}
	}
	
	
	public void eventTriggerResponse(String name, Player p) {
		switch(name) {
			case "king's_recognition":
				kingsRecognition();
				break;
			case "plague":
				plague(p);
				break;
			case "chivalrous_deed":
				chivalrousDeed(players);
				break;
			case "pox":
				pox(players, p);
				break;
			case "prosperity_throughout_the_realm":
				prosperity(players, aDeck);
				break;
			case "queen's_favor":
				queensFavor(players, aDeck);
				break;
			case "court_called_to_camelot":
				courtCalled(players, aDiscard);
				break;
			case "king's_call_to_arms":
				kingsCallToArms(players, aDiscard);
				break;
		}
	}
	
	public void kingsRecognition() {// notify that the next player to finish a quest gets 2 bonus shields
		//logger.info("King's Recognition drawn, the next player to finish a quest will receive 2 bonus shields.");
		//listeners.get(0).onKingsRecognition();
	}
	
	public void plague(Player p) { // drawer loses 2 shields if possible
		logger.info("Plague drawn, " + p.getName() + " loses 2 shields if possible.");
		if(p.getShields() > 2) {
			p.setShields(p.getShields() -2);
		}
		else {
			p.setShields(0);
		}
		mapGameStuffWithPlayersAndSend(ServerSubscribeEndpoints.EVENT_TRIGGERED);
	}
	
	public void chivalrousDeed(Players p) { // player(s) with BOTH lowest rank and least amount of shields receives 3 shields 
		logger.info("Chivalrous Deed drawn, player(s) with BOTH lowest rank and least amount of shields receive 3 shields.");
		Integer pShields[] =  p.getPlayers().stream().map(Player::getShields).toArray(Integer[]::new);
		int minS = Collections.min(Arrays.asList(pShields));
			
		Integer pRanks[] =  p.getPlayers().stream().map(Player::getRank).toArray(Integer[]::new);
		int minR = Collections.min(Arrays.asList(pRanks));
			
		int min = minR + minS;
			
		for(Player pr : p.getPlayers()) { // scenario: squire w/ 4 shields and knight w/ 3 shields --> squire, not knight
			if(pr.getRank() + pr.getShields() <= min) {
				logger.info(pr.getName() + " receives 3 shields.");
				pr.setShields(pr.getShields() + 3);
			}
		}
		mapGameStuffWithPlayersAndSend(ServerSubscribeEndpoints.EVENT_TRIGGERED);
	}
	
	public void pox(Players p, Player pr) { // all players except drawer lose a shield
		logger.info("Pox drawn, all but " + pr.getName() + " (player who drew this card) lose a shield.");
		for (Player ele : p.getPlayers()) {
			if(!ele.equals(pr)) {
				if(ele.getShields() > 0) {
					logger.info(ele.getName() + " loses a shield.");
					ele.setShields(ele.getShields()-1);
				}
			}
		}
		mapGameStuffWithPlayersAndSend(ServerSubscribeEndpoints.EVENT_TRIGGERED);
	}
	
	public void prosperity(Players p, AdventureDeck d) { // all players must draw two cards
		logger.info("Prosperity throughout the realm drawn, all players must draw 2 cards.");
		
		ArrayList<Player> prClone = new ArrayList<>(); // had to do this to avoid concurrent modification exception
		prClone.addAll(players.getPlayers());
		for(int i = 0; i < prClone.size(); i++) {
			prClone.get(i).drawCard(2, d);
			prClone.get(i).setHandState(CardStates.FACE_DOWN);
		}
		players.setPlayers(prClone);
		mapGameStuffWithPlayersAndSend(ServerSubscribeEndpoints.EVENT_TRIGGERED);
	}
	
	public void queensFavor(Players p, AdventureDeck d) { // player(s) with lowest rank receive 2 cards
		//boolean lower = false; 
		Integer pranks[] =  p.getPlayers().stream().map(Player::getRank).toArray(Integer[]::new);
		int minR = Collections.min(Arrays.asList(pranks));
				
		logger.info("Queen's Favor drawn, all lowest ranked players receive 2 cards.");
		ArrayList<Player> prClone = new ArrayList<>(); // had to do this to avoid concurrent modification exception
		prClone.addAll(players.getPlayers());
		for(int i = 0; i < prClone.size(); i++) {
			if(prClone.get(i).getRank() == minR) {
				//logger.info(prClone.get(i).getName() + " has drawn 2 cards.");
				prClone.get(i).drawCard(2, d);
			}
		}
		players.setPlayers(prClone);
		mapGameStuffWithPlayersAndSend(ServerSubscribeEndpoints.EVENT_TRIGGERED);
	}
	
	public void courtCalled(Players p, AdventureDiscard d) { // all allies in play must be discarded
		logger.info("Court Called to Camelot drawn, all allies in play must be discarded.");
		
		ArrayList<Player> prClone = new ArrayList<>(); // had to do this to avoid concurrent modification exception
		prClone.addAll(players.getPlayers());
		for(int i = 0; i < prClone.size(); i++) {
			prClone.get(i).getAllies().removeAll(prClone.get(i).getAllies());
		}
		players.setPlayers(prClone);
		mapGameStuffWithPlayersAndSend(ServerSubscribeEndpoints.EVENT_TRIGGERED);
	}
	
	public void kingsCallToArms(Players p, AdventureDiscard d) {
		//logger.info("King's Call To Arms drawn, all highest ranked players must either discard 1 weapon or 2 foes.");
		Integer pranks[] =  p.getPlayers().stream().map(Player::getRank).toArray(Integer[]::new);//map ranks of players to integer array
		int maxR = Collections.max(Arrays.asList(pranks));
		
		for(Player pr : p.getPlayers()) { // scenario: squire w/ 4 shields and knight w/ 3 shields --> squire, not knight
			if(pr.getRank() == maxR) {
				//notify view method added, it's just empty.
				boolean weaponFound = false;
				for(Adventure a : pr.getHand()) {
					if(a instanceof Weapon) {
						weaponFound = true;
						break;
					}
				}
				
				
				if(!weaponFound) {
					int count = 0;
					for(Adventure a : pr.getHand()) {
						if(a instanceof Foe) {
							count++;
						}
					}
					if (count >= 2 ) {
						//logger.info("Forcing player to discard 2 foes through view because a weapon was not found.");
						//pg.getView().kingsCallToArmsPrompt(pr, 2, false);
					} else if (count == 1) {
						//logger.info(pr.getName() + " has only 1 foe, forcing them to remove it.");
						//pg.getView().kingsCallToArmsPrompt(pr, 1, false);
					} else {
						//logger.info(pr.getName() + " didn't have any weapons or foes.");
					}
				} else {
					//logger.info("Forcing player to discard a weapon because a weapon was found.");
					//pg.getView().kingsCallToArmsPrompt(pr, 1, true);
				}
				
			}
		}	
	}
	
	
}
