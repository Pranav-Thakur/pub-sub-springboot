package com.example.pubsub.service;


import com.example.pubsub.dao.model.PublisherDO;
import com.example.pubsub.payload.request.PublisherCreateTopicRequest;
import com.example.pubsub.payload.request.PublisherMessageTopicRequest;
import com.example.pubsub.payload.request.PublisherRegisterRequest;
import com.example.pubsub.payload.response.PublisherCreateTopicResponse;
import com.example.pubsub.payload.response.PublisherMessageTopicResponse;
import com.example.pubsub.payload.response.PublisherRegisterResponse;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface PublisherService {
    PublisherRegisterResponse register(@NonNull PublisherRegisterRequest registerRequest);
    PublisherCreateTopicResponse createTopic(@NonNull UUID id, @NonNull PublisherCreateTopicRequest createTopicRequest);
    List<PublisherDO> getAllPublishers();
    PublisherMessageTopicResponse publishMessage(@NonNull UUID id, @NonNull PublisherMessageTopicRequest request);
}
