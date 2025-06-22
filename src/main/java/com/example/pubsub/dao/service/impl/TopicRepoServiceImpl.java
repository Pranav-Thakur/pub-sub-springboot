package com.example.pubsub.dao.service.impl;

import com.example.pubsub.dao.model.TopicDO;
import com.example.pubsub.dao.repository.TopicRepository;
import com.example.pubsub.dao.service.TopicRepoService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class TopicRepoServiceImpl implements TopicRepoService {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<TopicDO> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public TopicDO getTopicById(UUID id) {
        return topicRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Topic with id " + id + " not found"));
    }

    @Override
    public TopicDO getTopicByName(@NonNull String topicName) {
        return topicRepository.findByTopicName(topicName).orElse(null);
    }

    @Override
    public TopicDO save(@NonNull TopicDO topicDO) {
        return topicRepository.save(topicDO);
    }
}
