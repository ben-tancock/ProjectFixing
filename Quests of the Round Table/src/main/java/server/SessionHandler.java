package server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.WebSocketSession;

public class SessionHandler {

	private final Map<String, WebSocketSession> sessionMap = new ConcurrentHashMap<>();
	
	public SessionHandler() {
		
	}
	
	public void deleteSession(WebSocketSession session) {
		sessionMap.remove(session.getId(), session);
	}
	
	public void register(WebSocketSession session) {
		sessionMap.put(session.getId(), session);
	}
	
	public Map<String, WebSocketSession> getSessionMap(){
		return sessionMap;
	}
}
