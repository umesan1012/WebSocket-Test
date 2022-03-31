package com.test.webSocketTest.Handler;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.test.webSocketTest.Entity.MessageHistoryEntity;
import com.test.webSocketTest.Entity.SessionEntity;
import com.test.webSocketTest.Repository.MessageHistoryRepository;
import com.test.webSocketTest.Repository.SessionRepository;

public class MessageHandler extends TextWebSocketHandler{
	private ArrayList<WebSocketSession> users;
	private SessionRepository sessionRepository;
	private MessageHistoryRepository messageHistoryRepository;
	public MessageHandler(SessionRepository sessionRepository, MessageHistoryRepository messageHistoryRepository) {
		users = new ArrayList<>();
		this.sessionRepository = sessionRepository;
		this.messageHistoryRepository = messageHistoryRepository;
	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		// 接続が確立されたら呼ばれる
		if (users.stream().noneMatch(user -> user.getId().equals(session.getId()))) {
			// ユーザーを追加
			SessionEntity sessionEntity = new SessionEntity();
			sessionEntity.setSession_id(session.getId());
			sessionEntity.setConnection_start_time(new Timestamp(System.currentTimeMillis()));
			sessionRepository.save(sessionEntity);
			users.add(session);
		}
	}
	
	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) {
		System.out.println("send Message user session:" + session.getId());
		users.stream()
		.forEach(user -> {
			try {
				System.out.println("send Message:" + message);
				user.sendMessage(message);
				MessageHistoryEntity messageHistory = new MessageHistoryEntity();
				messageHistory.setSession_id(session.getId());
				messageHistory.setMessage(message.toString());
				messageHistory.setRegistered_date(new Timestamp(System.currentTimeMillis()));
				messageHistoryRepository.save(messageHistory);
			}catch(IOException ex) {
			}
		});
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
		// 接続が閉じられたらリストから外す
		System.out.println("close session:" + session.getId());
		sessionRepository.setConnectionEndTime(new Timestamp(System.currentTimeMillis()), session.getId());
		users.stream()
			.filter(user -> user.getId().equals(session.getId()))
			.findFirst()
			.ifPresent(user -> users.remove(user));
	}
}
