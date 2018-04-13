package server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

public class CustomSubProtocolWebSocketHandler extends SubProtocolWebSocketHandler{
	
	@Autowired
	private SessionHandler sessionHandler;

	public CustomSubProtocolWebSocketHandler(MessageChannel clientInboundChannel,
			SubscribableChannel clientOutboundChannel) {
		super(clientInboundChannel, clientOutboundChannel);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionHandler.register(session);
        super.afterConnectionEstablished(session);
    }

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		if(!status.equals(session)) {
			sessionHandler.equals(session);
			super.afterConnectionClosed(session, status);
		}
	}
}
