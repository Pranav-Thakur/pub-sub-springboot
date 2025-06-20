package com.example.pubsub.payload.response;

import com.example.pubsub.dao.constants.TopicStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class PublisherCreateTopicResponse {
    private String topicName;
    private UUID publisherId;
    private UUID topicId;
    private TopicStatus status;
}
