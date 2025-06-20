package com.example.pubsub.dao.repository;

import com.example.pubsub.dao.model.AppUserDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AppUserRepository extends JpaRepository<AppUserDO, UUID> {
}
