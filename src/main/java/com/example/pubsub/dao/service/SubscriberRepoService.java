package com.example.pubsub.dao.service;

import com.example.pubsub.dao.model.AppUserDO;
import com.example.pubsub.dao.model.SubscriberDO;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface SubscriberRepoService {
    List<SubscriberDO> getAllSubscribers();
    SubscriberDO getSubscriberById(@NonNull UUID id);
    SubscriberDO save(@NonNull SubscriberDO subscriberDO);
    SubscriberDO update(@NonNull SubscriberDO subscriberDO);

    default void fillBaseDO(@NonNull SubscriberDO subscriberDO) {
        subscriberDO.setVersion(1);
        subscriberDO.setCreatedDate(LocalDateTime.now());
        subscriberDO.setUpdatedDate(LocalDateTime.now());
    }
}
