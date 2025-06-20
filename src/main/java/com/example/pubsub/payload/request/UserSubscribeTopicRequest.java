package com.example.pubsub.payload.request;

import lombok.Data;

import java.util.UUID;

@Data
public class UserSubscribeTopicRequest {
    private UUID topicId;
}
