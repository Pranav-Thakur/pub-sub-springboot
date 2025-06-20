package com.example.pubsub.service;

import com.example.pubsub.payload.request.UserRegisterRequest;
import com.example.pubsub.payload.request.UserSubscribeTopicRequest;
import com.example.pubsub.payload.response.UserRegisterResponse;
import com.example.pubsub.payload.response.UserSubscribeTopicResponse;
import lombok.NonNull;
import reactor.core.publisher.FluxSink;

import java.util.UUID;

public interface UserService {
    UserRegisterResponse register(@NonNull UserRegisterRequest registerRequest);
    UserSubscribeTopicResponse subscribe(@NonNull UUID id, @NonNull UserSubscribeTopicRequest request);
    void consume(@NonNull UUID subcriberId, @NonNull FluxSink<String> emitter);
}
