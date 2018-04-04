package server;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import model.Player;
import model.Players;

@Controller
public class ServerMessageController {

	ConcurrentHashMap<String, Player> users;
	
	private SimpMessagingTemplate template;
	private Players players;
	private int i;
	
	public ServerMessageController(SimpMessagingTemplate template) {
		this.template = template;
		users = new ConcurrentHashMap<String, Player>();
		players = new Players();
		i = 0;
	}
	
	@MessageMapping("/register")
	@SendTo("/users")
	public ServerMessage connect( ConnectMessage message, @Header("simpSessionId") String sessionId) {
		System.out.println(message.getName() + " connected.");
		players.addHuman();
		players.getPlayers().get(i).setName(message.getName());
		users.put(sessionId, players.getPlayers().get(i));
		ServerMessage serverMessage = new ServerMessage(users);
		System.out.println("Sending: " + serverMessage.toString());
		System.out.println(sessionId);
		i++;
		return serverMessage;
	}
	
	@MessageMapping("selectShield")
	@SendTo("/shieldChange")
	public ServerMessage shieldChange(@Header("simpSessionId") String sessionId) {
		return null;
	}
}
