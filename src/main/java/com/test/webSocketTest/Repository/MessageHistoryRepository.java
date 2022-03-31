package com.test.webSocketTest.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.webSocketTest.Entity.MessageHistoryEntity;

@Transactional
@Repository
public interface MessageHistoryRepository extends JpaRepository<MessageHistoryEntity, String> {
}
