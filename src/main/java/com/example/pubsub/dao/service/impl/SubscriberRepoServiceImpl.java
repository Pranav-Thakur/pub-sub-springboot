package com.example.pubsub.dao.service.impl;

import com.example.pubsub.dao.model.SubscriberDO;
import com.example.pubsub.dao.repository.SubscriberRepository;
import com.example.pubsub.dao.service.SubscriberRepoService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubscriberRepoServiceImpl implements SubscriberRepoService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Override
    public List<SubscriberDO> getAllSubscribers() {
        return subscriberRepository.findAll();
    }

    @Override
    public SubscriberDO getSubscriberById(@NonNull UUID id) {
        return subscriberRepository.findById(id).orElse(null);
    }

    @Override
    public SubscriberDO save(@NonNull SubscriberDO subscriberDO) {
        fillBaseDO(subscriberDO);
        return subscriberRepository.save(subscriberDO);
    }

    @Override
    public SubscriberDO update(@NonNull SubscriberDO subscriberDO) {
        return subscriberRepository.save(subscriberDO);
    }
}
