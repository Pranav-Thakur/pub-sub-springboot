package com.example.pubsub.service.impl;

import com.example.pubsub.dao.constants.MessageStatus;
import com.example.pubsub.dao.constants.PublisherStatus;
import com.example.pubsub.dao.constants.TopicStatus;
import com.example.pubsub.dao.model.MessageDO;
import com.example.pubsub.dao.model.PublisherDO;
import com.example.pubsub.dao.model.TopicDO;
import com.example.pubsub.dao.service.MessageRepoService;
import com.example.pubsub.dao.service.PublisherRepoService;
import com.example.pubsub.dao.service.TopicRepoService;
import com.example.pubsub.dto.PublisherCreateTopicDTO;
import com.example.pubsub.dto.PublisherMessageTopicDTO;
import com.example.pubsub.dto.PublisherRegisterDTO;
import com.example.pubsub.payload.request.PublisherCreateTopicRequest;
import com.example.pubsub.payload.request.PublisherMessageTopicRequest;
import com.example.pubsub.payload.request.PublisherRegisterRequest;
import com.example.pubsub.payload.response.PublisherCreateTopicResponse;
import com.example.pubsub.payload.response.PublisherMessageTopicResponse;
import com.example.pubsub.payload.response.PublisherRegisterResponse;
import com.example.pubsub.service.ConsumerPublisher;
import com.example.pubsub.service.PublisherService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PublisherServiceImpl implements PublisherService {
    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private PublisherRepoService publisherRepoService;

    @Autowired
    private TopicRepoService topicRepoService;

    @Autowired
    private MessageRepoService messageRepoService;

    @Autowired
    private ConsumerPublisher consumerPublisher;

    @Override
    public PublisherRegisterResponse register(@NonNull PublisherRegisterRequest registerRequest) {
        PublisherRegisterDTO registerDTO = new PublisherRegisterDTO();
        registerDTO.setName(registerRequest.getName());
        registerDTO.setData(registerRequest.getData());

        PublisherDO publisherDO = null;
        try {
            publisherDO = publisherRepoService.getPublisherByName(registerRequest.getName());
        } catch (Exception e) {
            System.err.println("Publisher with name " + registerRequest.getName() + " not found. Handling gracefully.");
        }

        if (publisherDO != null)
            throw new RuntimeException("Company Name given is not unique.");

        publisherDO = new PublisherDO();
        publisherDO.setCompanyName(registerDTO.getName());
        publisherDO.setInfo(registerDTO.getData().toString());
        publisherDO.setStatus(PublisherStatus.ACTIVE);
        publisherDO = publisherRepoService.save(publisherDO);

        PublisherRegisterResponse registerResponse = new PublisherRegisterResponse();
        registerResponse.setPublisherId(publisherDO.getId());
        registerResponse.setPublisherName(publisherDO.getCompanyName());
        registerResponse.setPublisherStatus(publisherDO.getStatus());
        return registerResponse;
    }

    @Override
    public PublisherCreateTopicResponse createTopic(@NonNull UUID id, @NonNull PublisherCreateTopicRequest createTopicRequest) {
        PublisherDO publisherDO = publisherRepoService.getPublisherById(id);
        PublisherCreateTopicDTO createTopicDTO = new PublisherCreateTopicDTO();
        createTopicDTO.setTopicName(createTopicRequest.getTopicName());

        TopicDO topicDO = new TopicDO();
        topicDO.setTopicName(createTopicRequest.getTopicName());
        topicDO.setPublisher(publisherDO);
        topicDO.setStatus(TopicStatus.ACTIVE);
        topicDO = topicRepoService.save(topicDO);

        PublisherCreateTopicResponse createTopicResponse = new PublisherCreateTopicResponse();
        createTopicResponse.setTopicId(topicDO.getId());
        createTopicResponse.setTopicName(topicDO.getTopicName());
        createTopicResponse.setPublisherId(publisherDO.getId());
        createTopicResponse.setStatus(topicDO.getStatus());
        return createTopicResponse;
    }

    @Override
    public List<PublisherDO> getAllPublishers() {
        return publisherRepoService.getAllPublishers();
    }

    @Override
    public PublisherMessageTopicResponse publishMessage(@NonNull UUID id, @NonNull PublisherMessageTopicRequest request) {
        PublisherDO publisherDO = publisherRepoService.getPublisherById(id);
        TopicDO topicDO = topicRepoService.getTopicById(request.getTopicId());
        PublisherMessageTopicDTO messageTopicDTO = new PublisherMessageTopicDTO();
        messageTopicDTO.setPublisherId(publisherDO.getId());
        messageTopicDTO.setMessage(request.getMessage());
        messageTopicDTO.setTopicId(topicDO.getId());

        MessageDO messageDO = new MessageDO();
        messageDO.setTopic(topicDO);

        try {
            String jsonData = mapper.writeValueAsString(messageTopicDTO.getMessage());
            messageDO.setData(jsonData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        messageDO.setStatus(MessageStatus.RECEIVED);
        messageDO = messageRepoService.save(messageDO);

        consumerPublisher.publishToTopic(topicDO.getId(), messageDO);

        PublisherMessageTopicResponse response = new PublisherMessageTopicResponse();
        response.setPublisherId(publisherDO.getId());
        response.setMessageId(messageDO.getId());
        response.setTopicId(topicDO.getId());
        return response;
    }
}
