package server;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.server.ServerEndpoint;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
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

import model.Adventure;
import model.AdventureDeck;
import model.AdventureDiscard;
import model.Ally;
import model.Amour;
import model.Foe;
import model.Player;
import model.Players;
import model.StoryDeck;
import model.StoryDiscard;
import model.Test;
import model.Weapon;
import model.pojo.PlayerPOJO;
import util.ServerSubscribeEndpoints;

@Controller
public class ServerMessageController implements ApplicationListener<SessionDisconnectEvent>{

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
	private SubscriptionRegistry registry;
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
		numUsersLeft = 0;
	}
	
	@MessageMapping(ServerMaps.REGISTER)
	@SendTo(ServerSubscribeEndpoints.REGISTER)
	public ServerMessage connect(ConnectMessage message, @Header("simpSessionId") String sessionId) {
		System.out.println(message.getName() + " connected.");
		players.addHuman();
		players.getPlayers().get(userCounter).setName(message.getName());
		players.getPlayers().get(userCounter).setShieldName("shield_" + userCounter);
		players.getPlayers().get(userCounter).drawCard(12, aDeck);
		users.put(Integer.valueOf(userCounter), players.getPlayers().get(userCounter));
		ServerMessage serverMessage = new ServerMessage(users);
		userCounter++;
		connectMessage = message;
		return serverMessage;
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
	@SendTo(ServerSubscribeEndpoints.STORY_DRAW)
	public ServerMessage drawStoryCard(ConnectMessage message) {
		for(Player p : players.getPlayers()) {
			if(p.getName().equals(message.getName())) {
				p.drawCard(sDeck, sDiscard);
				break;
			}
		}
		return mapGameStuffWithoutPlayers();
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
}
