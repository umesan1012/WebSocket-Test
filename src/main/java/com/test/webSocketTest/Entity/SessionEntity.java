package com.test.webSocketTest.Entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="t_session")
public class SessionEntity {
	@Id
	@Column(name="session_id")
	private String session_id;
	
	@Column(name="connection_start_time")
	private Timestamp connection_start_time;
	
	@Column(name="connection_end_time")
	private Timestamp connection_end_time;
}
