package com.example.pubsub.dao.repository;

import com.example.pubsub.dao.constants.MessageStatus;
import com.example.pubsub.dao.model.MessageDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<MessageDO, UUID> {
    @Query("SELECT m FROM MessageDO m WHERE m.topic.id = :topicId AND m.createdDate > :lastSeen AND m.status = :status ORDER BY m.createdDate ASC")
    Optional<List<MessageDO>> findByTopicIdAndStatusAndCreatedDateAfter(@Param("topicId") UUID topicId, @Param("status") MessageStatus status, @Param("lastSeen") LocalDateTime lastSeen);
}
