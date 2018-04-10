package server;

import java.io.IOException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

@Configuration
@EnableAutoConfiguration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer{
	
	//
	@Override
	public void configureMessageBroker(MessageBrokerRegistry config){
		config.enableSimpleBroker("/users");
		config.setApplicationDestinationPrefixes("/app");
	}
	
	@Override
	public void configureWebSocketTransport(final WebSocketTransportRegistration registration) {
		/*registration.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {

			@Override
			public WebSocketHandler decorate(WebSocketHandler handler) {
				// TODO Auto-generated method stub
				return new WebSocketHandlerDecorator(handler) {
					@Override
					public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
						try {
							System.out.println("closed the session");
							session.close(status);
							
							System.out.println(session.getId());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				};
			}
			
		});*/
	}
	
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/register");
		registry.addEndpoint("/selectShield");
		registry.addEndpoint("/storyDraw");
    }
	
	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}
	
	@Bean
	public ServletServerContainerFactoryBean createWebSocketContainer() {
		ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
		container.setMaxTextMessageBufferSize(99999999);
		container.setMaxBinaryMessageBufferSize(99999999);
		return container;
	}
	
}
