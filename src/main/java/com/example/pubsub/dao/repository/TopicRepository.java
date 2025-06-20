package com.example.pubsub.dao.repository;

import com.example.pubsub.dao.model.TopicDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TopicRepository extends JpaRepository<TopicDO, UUID> {
    Optional<TopicDO> findByTopicName(String topicName);
}
