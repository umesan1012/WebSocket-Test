package com.test.webSocketTest.Handler;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MessageHandler extends TextWebSocketHandler{
	private ArrayList<WebSocketSession> users;
	public MessageHandler() {
		users = new ArrayList<>();
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		// 接続が確立されたら呼ばれる
		if (users.stream().noneMatch(user -> user.getId().equals(session.getId()))) {
			// ユーザーを追加
			System.out.println(session.getId());
			users.add(session);
		}
	}
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		users.stream().filter(user -> !user.getId().equals(session.getId()))
		.forEach(user -> {
			try {
				user.sendMessage(message);
			}catch(IOException ex) {
			}
		});
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
		// 接続が閉じられたらリストから外す
		users.stream()
			.filter(user -> user.getId().equals(session.getId()))
			.findFirst()
			.ifPresent(user -> users.remove(user));
	}
}
