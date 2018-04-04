package questClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.io.IOException;
import java.lang.reflect.Type;
import org.springframework.lang.Nullable;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Person;
import model.Player;

public class QuestClient extends Application {
	static StompSession session = null;
	static SessionHandler sessionHandler = null;
	
	 //view parameters
    TextField userNameField;
    String userName;
    Stage connectStage;
    static QuestUserLobby lobby = new QuestUserLobby();
	
    public static void main(String... argv) {
        WebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
    
        String url = "ws://127.0.0.1:8080/register";
        sessionHandler = new SessionHandler();
        
        try {
			session = stompClient.connect(url, sessionHandler).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        stompClient.start();
       // System.out.println("session id: " + session.getSessionId());
        //new Scanner(System.in).nextLine(); //Don't close immediately.
        Application.launch(argv);
    }
    
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		connectStage = primaryStage;
		VBox startPane = new VBox(50);
		HBox buttons = new HBox(20);
		HBox userSpace = new HBox(5);
		Label userLabel = new Label("User Name: ");
		Button connect = new Button("Connect");
		//buttons.getChildren().add(connect);
		buttons.getChildren().addAll(connect);
		userNameField = new TextField();
		connect.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				System.out.println("connectButton clicked");
				String name = "{\"name\":\"" + userNameField.getText() + "\"}";
				userName = userNameField.getText();
				session.subscribe("/users", new StompFrameHandler() {

					@Override
					public Type getPayloadType(StompHeaders arg0) {
						// TODO Auto-generated method stub
						return ServerMessage.class;
					}

					@Override
					public void handleFrame(StompHeaders headers, Object payload) {
						// TODO Auto-generated method stub
						System.out.println("GOT REPLY");
						Object message = ((ServerMessage)payload).getMessage();
						HashMap<String, HashMap<String, String>> receivedMap = (HashMap<String, HashMap<String, String>>)message;
						HashMap<String, Player> userMap = new HashMap<>();
						for(Entry<String, HashMap<String, String>> entry : receivedMap.entrySet()) {
							System.out.println(entry.getKey());
							Player player = new Person();
							player = player.fromMap(entry.getValue());
							userMap.put(entry.getKey(), player);
						}
						String time = ((ServerMessage)payload).getTime().toString();
						System.out.println("i got to lobby 222");
						Platform.runLater(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									System.out.println("i got to lobby");
									lobby.start(connectStage);
									lobby.update(userName, userMap, time);
									
								}catch(Exception e) {
									e.printStackTrace();
								}
							}
							
						});
					}
					
				});
				session.send("/app/register", name.getBytes());
			}
			
		});
		userSpace.getChildren().addAll(userLabel, userNameField);
		startPane.getChildren().add(userSpace);
		startPane.getChildren().add(buttons);
		primaryStage.setScene(new Scene(startPane));
		primaryStage.show();
		
	}
    
	
	
	static class SessionHandler extends StompSessionHandlerAdapter{
		
		private void showHeaders(StompHeaders headers) {
	    		for (Map.Entry<String,List<String>> e:headers.entrySet()) {
	    			System.out.print("  " + e.getKey() + ": ");
	    			boolean first = true;
	    			for (String value : e.getValue()) {
	    				if (!first) 
	    					System.out.print(", ");
	    				System.out.print(value);
	    				first = false;
	    			}
	    			System.out.println();
	    		}	
    		}
		
		/*@Override
		public void handleFrame(StompHeaders headers, @Nullable java.lang.Object payload) {
			Object message = ((ServerMessage)payload).getMessage();
			HashMap<String, String> userMap = (HashMap<String, String>)message;
			for(Entry<String, String> entry : userMap.entrySet()) {
				System.out.println(entry.getKey());
				System.out.println(entry.getValue());
			}
			String time = ((ServerMessage)payload).getTime().toString();
			
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					
				}
				
			});
			
		}*/
		
		@Override
		public Type getPayloadType(StompHeaders headers) {
			return ServerMessage.class;
		}
		
		@Override
		public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
    		System.out.println("Connected! Headers:");
    		showHeaders(connectedHeaders);
    		System.out.println("sessionId: " + session.getSessionId());
    		
    		}
	}
}
