package server;

import java.util.ArrayList;
import java.util.Collections;
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
		aDeck.shuffle();
		aDiscard = new AdventureDiscard();
		sDeck = new StoryDeck();
		sDeck.shuffle();
		sDiscard = new StoryDiscard();
	}
	
	@MessageMapping("/register")
	@SendTo("/users/register")
	public ServerMessage connect( ConnectMessage message, @Header("simpSessionId") String sessionId) {
		System.out.println(message.getName() + " connected.");
		players.addHuman();
		players.getPlayers().get(userCounter).setName(message.getName());
		players.getPlayers().get(userCounter).setShieldName("shield_" + userCounter);
		players.getPlayers().get(userCounter).drawCard(12, aDeck);
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
			template.convertAndSend("/users/startGame-" + users.get(Integer.toString(i)).getName(), serverMessage);
			gameStuff.remove(sendingPlayers);
			sendingPlayers = rotate(sendingPlayers);
			gameStuff.add(sendingPlayers);
		}
	}
	
	private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
    }
	
	public ArrayList<PlayerPOJO> rotate(ArrayList<PlayerPOJO> sendingList) {
		PlayerPOJO temp = sendingList.get(0);
		sendingList.add(temp);
		sendingList.remove(0);
		return sendingList;
	}
}
