package com.example.pubsub.service.impl;

import com.example.pubsub.dao.constants.AppUserStatus;
import com.example.pubsub.dao.constants.MessageStatus;
import com.example.pubsub.dao.constants.SubscriberStatus;
import com.example.pubsub.dao.model.AppUserDO;
import com.example.pubsub.dao.model.MessageDO;
import com.example.pubsub.dao.model.SubscriberDO;
import com.example.pubsub.dao.model.TopicDO;
import com.example.pubsub.dao.service.AppUserRepoService;
import com.example.pubsub.dao.service.MessageRepoService;
import com.example.pubsub.dao.service.SubscriberRepoService;
import com.example.pubsub.dao.service.TopicRepoService;
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
import java.util.List;

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
        subscriberDO.setLastMessageDeliveredDate(null);
        subscriberDO = subscriberRepoService.save(subscriberDO);

        UserSubscribeTopicResponse response = new UserSubscribeTopicResponse();
        response.setSubscriberId(subscriberDO.getId());
        return response;
    }

    @Override
    public void consume(@NonNull UUID subscriberId, @NonNull FluxSink<String> emitter) {
        SubscriberDO subscriberDO = subscriberRepoService.getSubscriberById(subscriberId);
        LocalDateTime localDateTime = subscriberDO.getLastMessageDeliveredDate() == null ? LocalDateTime.of(1970, 1, 1, 0, 0) : subscriberDO.getLastMessageDeliveredDate();
        consumerPublisher.registerListener(subscriberDO.getTopic().getId(), emitter);
        List<MessageDO> messageDOList = messageRepoService.getAllMessagesBy(
                subscriberDO.getTopic().getId(), MessageStatus.RECEIVED, localDateTime);

    }
}
