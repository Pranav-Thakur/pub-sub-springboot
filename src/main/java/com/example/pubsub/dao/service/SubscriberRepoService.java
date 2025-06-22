package com.example.pubsub.dao.service;

import com.example.pubsub.dao.model.SubscriberDO;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface SubscriberRepoService {
    List<SubscriberDO> getAllSubscribers();
    SubscriberDO getSubscriberById(@NonNull UUID id);
    SubscriberDO save(@NonNull SubscriberDO subscriberDO);
}
