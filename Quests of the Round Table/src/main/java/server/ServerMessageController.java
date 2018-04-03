package server;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;


public class ServerMessageController {

	ConcurrentHashMap<String, String> users;
	
	private SimpMessagingTemplate template;
	
	public ServerMessageController(SimpMessagingTemplate template) {
		this.template = template;
		users = new ConcurrentHashMap<String, String>();
	}
	
	@MessageMapping("/register")
	@SendTo("/users")
	public void connect( ConnectMessage message, @Header("simpSessionId") String sessionId) {
		System.out.println(message.getName() + " connected.");
		users.put(sessionId, message.getName());
		ServerMessage serverMessage = new ServerMessage(users);
		System.out.println("Sending: " + serverMessage.toString());
		System.out.println(sessionId);
	}
}
