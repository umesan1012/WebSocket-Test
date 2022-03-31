package com.test.webSocketTest.Repository;

import java.sql.Timestamp;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.test.webSocketTest.Entity.SessionEntity;

@Transactional
@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, String> {
	@Modifying
	@Query("update SessionEntity set connection_end_time = :connection_end_time where session_id = :session_id")
	void setConnectionEndTime(@Param("connection_end_time") Timestamp connectionEndTime, @Param("session_id") String session_id);
}
