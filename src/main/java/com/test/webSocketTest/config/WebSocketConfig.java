package com.test.webSocketTest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.test.webSocketTest.Handler.MessageHandler;
import com.test.webSocketTest.Repository.MessageHistoryRepository;
import com.test.webSocketTest.Repository.SessionRepository;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
	@Autowired
	private SessionRepository sessionRepository;
	
	@Autowired
	private MessageHistoryRepository messageHistoryRepository;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(new MessageHandler(sessionRepository, messageHistoryRepository), "/ws");
	}
	
	@Bean
	public WebSocketHandler messageHandler() {
		return new MessageHandler(sessionRepository, messageHistoryRepository);
	}
}
