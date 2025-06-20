package com.example.pubsub.service.impl;

import com.example.pubsub.dao.model.MessageDO;
import com.example.pubsub.service.ConsumerPublisher;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import reactor.core.publisher.FluxSink;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ConsumerPublisherImpl implements ConsumerPublisher {

    private final Map<UUID, List<FluxSink<String>>> subscribers = new ConcurrentHashMap<>();

    @Override
    public void registerListener(@NonNull UUID topicId, @NonNull FluxSink<String> listener) {
        subscribers.computeIfAbsent(topicId, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    @Override
    public void publishToTopic(@NonNull UUID topicId, @NonNull MessageDO messageDO) {
        List<FluxSink<String>> sinks = subscribers.get(topicId);
        if (sinks != null) {
            for (FluxSink<String> sink : sinks) {
                sink.next(messageDO.getData());
            }
        }
    }
}
