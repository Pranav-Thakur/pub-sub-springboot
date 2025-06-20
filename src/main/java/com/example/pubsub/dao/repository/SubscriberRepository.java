package com.example.pubsub.dao.repository;

import com.example.pubsub.dao.model.SubscriberDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SubscriberRepository extends JpaRepository<SubscriberDO, UUID> {
}
