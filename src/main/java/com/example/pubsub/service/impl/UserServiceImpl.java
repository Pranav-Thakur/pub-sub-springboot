package com.example.pubsub.service.impl;

import com.example.pubsub.dao.constants.AppUserStatus;
import com.example.pubsub.dao.constants.SubscriberStatus;
import com.example.pubsub.dao.model.*;
import com.example.pubsub.dao.service.*;
import com.example.pubsub.dto.ConsumerPublisherDTO;
import com.example.pubsub.payload.request.UserRegisterRequest;
import com.example.pubsub.payload.request.UserSubscribeTopicRequest;
import com.example.pubsub.payload.response.UserRegisterResponse;
import com.example.pubsub.payload.response.UserSubscribeTopicResponse;
import com.example.pubsub.service.ConsumerPublisher;
import com.example.pubsub.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.FluxSink;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AppUserRepoService appUserRepoService;

    @Autowired
    private TopicRepoService topicRepoService;

    @Autowired
    private SubscriberRepoService subscriberRepoService;

    @Autowired
    private MessageRepoService messageRepoService;

    @Autowired
    private ConsumerPublisher consumerPublisher;

    @Autowired
    private TransactionRepoService transactionRepoService;

    @Override
    public UserRegisterResponse register(@NonNull UserRegisterRequest registerRequest) {
        AppUserDO appUserDO = new  AppUserDO();
        appUserDO.setName(registerRequest.getName());
        appUserDO.setInfo(registerRequest.getData().toString());
        appUserDO.setStatus(AppUserStatus.ACTIVE);
        appUserDO = appUserRepoService.save(appUserDO);

        UserRegisterResponse registerResponse = new UserRegisterResponse();
        registerResponse.setId(appUserDO.getId());
        registerResponse.setName(appUserDO.getName());
        return registerResponse;
    }

    @Override
    public UserSubscribeTopicResponse subscribe(@NonNull UUID id, @NonNull UserSubscribeTopicRequest request) {
        AppUserDO appUserDO = appUserRepoService.getAppUserById(id);
        TopicDO topicDO = topicRepoService.getTopicById(request.getTopicId());

        SubscriberDO subscriberDO = new SubscriberDO();
        subscriberDO.setUser(appUserDO);
        subscriberDO.setTopic(topicDO);
        subscriberDO.setStatus(SubscriberStatus.ACTIVE);
        subscriberDO.setOffsetTime(null);
        subscriberDO = subscriberRepoService.save(subscriberDO);

        UserSubscribeTopicResponse response = new UserSubscribeTopicResponse();
        response.setSubscriberId(subscriberDO.getId());
        return response;
    }

    @Override
    public void consume(@NonNull UUID subscriberId, LocalDateTime offsetTime, @NonNull FluxSink<String> emitter) {
        SubscriberDO subscriberDO = subscriberRepoService.getSubscriberById(subscriberId);
        ConsumerPublisherDTO consumerPublisherDTO = new ConsumerPublisherDTO();
        consumerPublisherDTO.setSubscriberId(subscriberId);
        consumerPublisherDTO.setTopicId(subscriberDO.getTopic().getId());
        consumerPublisherDTO.setUserId(subscriberDO.getUser().getId());
        // ie getting also unsent msgs from either consumer given offset or default offset of 1970.
        consumerPublisherDTO.setOffsetTime(offsetTime != null ? offsetTime : LocalDateTime.of(1970, 1, 1, 0, 0));
        consumerPublisher.registerListener(consumerPublisherDTO, emitter);
        emitter.next("{\"data\": \"connection established.\"}");
    }
}
