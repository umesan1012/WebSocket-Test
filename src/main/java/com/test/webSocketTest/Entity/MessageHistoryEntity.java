package com.test.webSocketTest.Entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="t_message_history")
public class MessageHistoryEntity {
	@Id
	@Column(name="session_id")
	private String session_id;
	
	@Column(name="message")
	private String message;
	
	@Column(name="registered_date")
	private Timestamp registered_date;
}
