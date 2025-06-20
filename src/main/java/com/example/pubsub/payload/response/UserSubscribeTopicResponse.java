package com.example.pubsub.payload.response;

import lombok.Data;

import java.util.UUID;

@Data
public class UserSubscribeTopicResponse {
    private UUID subscriberId;
}
