package com.example.pubsub.dao.repository;

import com.example.pubsub.dao.constants.MessageStatus;
import com.example.pubsub.dao.model.MessageDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<MessageDO, UUID> {
    Optional<List<MessageDO>> findByTopicIdAndStatusAndCreatedDate(UUID topicId, MessageStatus status, LocalDateTime localDateTime);
}
