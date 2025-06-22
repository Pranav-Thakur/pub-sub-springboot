package com.example.pubsub.service;

import com.example.pubsub.dao.model.MessageDO;
import com.example.pubsub.dto.ConsumerPublisherDTO;
import lombok.NonNull;
import reactor.core.publisher.FluxSink;

import java.util.UUID;

public interface ConsumerPublisher {
    void registerListener(@NonNull ConsumerPublisherDTO dto, @NonNull FluxSink<String> listener);
    boolean publishForTopic(@NonNull UUID topicId, @NonNull MessageDO messageDO);
}
