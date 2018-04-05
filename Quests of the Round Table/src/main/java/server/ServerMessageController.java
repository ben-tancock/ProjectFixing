package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import model.AdventureDeck;
import model.AdventureDiscard;
import model.Player;
import model.Players;
import model.StoryDeck;
import model.StoryDiscard;

@Controller
public class ServerMessageController {

	ConcurrentHashMap<String, Player> users;
	ArrayList<String> shieldNames;
	
	private SimpMessageSendingOperations template;
	private Players players;
	private int userCounter;
	private AdventureDeck aDeck;
	private StoryDeck sDeck;
	private AdventureDiscard aDiscard;
	private StoryDiscard sDiscard;
	
	public ServerMessageController(SimpMessageSendingOperations template) {
		this.template = template;
		users = new ConcurrentHashMap<String, Player>();
		players = new Players();
		shieldNames = new ArrayList<>();
		userCounter = 0;
		aDeck = new AdventureDeck();
		aDiscard = new AdventureDiscard();
		sDeck = new StoryDeck();
		sDiscard = new StoryDiscard();
	}
	
	@MessageMapping("/register")
	@SendTo("/users")
	public ServerMessage connect( ConnectMessage message, @Header("simpSessionId") String sessionId) {
		System.out.println(message.getName() + " connected.");
		players.addHuman();
		players.getPlayers().get(userCounter).setName(message.getName());
		players.getPlayers().get(userCounter).setShieldName("shield_" + userCounter);
		users.put(sessionId, players.getPlayers().get(userCounter));
		ServerMessage serverMessage = new ServerMessage(users);
		System.out.println("Sending: " + serverMessage.toString());
		System.out.println(sessionId);
		userCounter++;
		return serverMessage;
	}
	
	/*
	@MessageMapping("/selectShield")
	@SendTo("/users/shieldChange")
	public ServerMessage shieldChange(ConnectMessage message, @Header("simpSessionId") String sessionId) {
		Player user = users.get(sessionId);
		
		return null;
	}*/
	
	@MessageMapping("/startGame")
	public void startGame(SimpMessageHeaderAccessor headerAccessor) {
		SimpMessageHeaderAccessor ha = SimpMessageHeaderAccessor
	            .create(SimpMessageType.MESSAGE);
		System.out.println("STARTING GAME FROM SERVER");
		List<Object> gameStuff = new ArrayList<>();
		gameStuff.add(players);
		gameStuff.add(sDeck);
		System.out.println(sDeck.size());
		gameStuff.add(sDiscard);
		ServerMessage serverMessage = new ServerMessage(gameStuff);
		
		for(Entry<String, Player> entry : users.entrySet()) {
			System.out.println("Sending to: " + entry.getValue().getName());
			template.convertAndSend("/users/startGame-" + entry.getValue().getName(), serverMessage, createHeaders(entry.getKey()));
			players.rotate();
		}
	}
	
	private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
}
