package com.example.pubsub.dao.service;

import com.example.pubsub.dao.model.PublisherDO;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface PublisherRepoService {
    List<PublisherDO> getAllPublishers();
    PublisherDO getPublisherById(@NonNull UUID id);
    PublisherDO getPublisherByName(@NonNull String companyName);
    PublisherDO save(@NonNull PublisherDO publisherDO);
    void delete(@NonNull UUID id);
}
