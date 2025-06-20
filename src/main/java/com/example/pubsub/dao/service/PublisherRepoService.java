package com.example.pubsub.dao.service;

import com.example.pubsub.dao.model.PublisherDO;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PublisherRepoService {
    List<PublisherDO> getAllPublishers();
    PublisherDO getPublisherById(@NonNull UUID id);
    PublisherDO getPublisherByName(@NonNull String companyName);
    PublisherDO save(@NonNull PublisherDO publisherDO);
    PublisherDO update(@NonNull PublisherDO updatedPublisherDO);
    void delete(@NonNull UUID id);

    default void fillBaseDO(@NonNull PublisherDO publisherDO) {
        publisherDO.setVersion(1);
        publisherDO.setCreatedDate(LocalDateTime.now());
        publisherDO.setUpdatedDate(LocalDateTime.now());
    }
}
