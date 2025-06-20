package com.example.pubsub.payload.request;

import lombok.Data;

import java.util.Map;

@Data
public class PublisherCreateTopicRequest {
    private String topicName;
    private Map<String, Object> data;
}
