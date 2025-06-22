package com.example.pubsub.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import reactor.core.publisher.FluxSink;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConsumerPublisherDTO extends CopyOnWriteArrayList<FluxSink<String>> {
    private UUID subscriberId;
    private UUID userId;
    private UUID topicId;
    private LocalDateTime offsetTime;
}
