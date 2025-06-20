package com.example.pubsub.payload.response;

import lombok.Data;

import java.util.UUID;

@Data
public class PublisherMessageTopicResponse {
    private UUID messageId;
    private UUID topicId;
    private UUID publisherId;
}
