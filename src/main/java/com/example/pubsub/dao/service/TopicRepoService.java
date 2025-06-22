package com.example.pubsub.dao.service;

import com.example.pubsub.dao.model.TopicDO;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface TopicRepoService {
    List<TopicDO> getAllTopics();
    TopicDO getTopicById(@NonNull UUID id);
    TopicDO getTopicByName(@NonNull String topicName);
    TopicDO save(@NonNull TopicDO topicDO);
}