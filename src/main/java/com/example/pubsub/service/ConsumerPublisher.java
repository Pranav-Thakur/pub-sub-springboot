package com.example.pubsub.service;

import com.example.pubsub.dao.model.MessageDO;
import lombok.NonNull;
import reactor.core.publisher.FluxSink;

import java.util.UUID;

public interface ConsumerPublisher {
    void registerListener(@NonNull UUID topicId, @NonNull FluxSink<String> listener);
    void publishToTopic(@NonNull UUID topicId, @NonNull MessageDO messageDO);
}
