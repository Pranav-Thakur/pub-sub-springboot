package com.example.pubsub.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PublisherMessageTopicDTO {
    private UUID topicId;
    private UUID publisherId;
    private Object message;
}
