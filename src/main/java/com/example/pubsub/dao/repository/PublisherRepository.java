package com.example.pubsub.dao.repository;

import com.example.pubsub.dao.model.PublisherDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PublisherRepository extends JpaRepository<PublisherDO, UUID> {
    Optional<PublisherDO> findByCompanyName(String companyName);
}
