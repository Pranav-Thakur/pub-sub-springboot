package com.example.pubsub.dao.service.impl;

import com.example.pubsub.dao.constants.MessageStatus;
import com.example.pubsub.dao.model.MessageDO;
import com.example.pubsub.dao.repository.MessageRepository;
import com.example.pubsub.dao.service.MessageRepoService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MessageRepoServiceImpl implements MessageRepoService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<MessageDO> getAllMessages() {
        return messageRepository.findAll();
    }

    @Override
    public MessageDO getMessageById(UUID id) {
        return messageRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Message with id " + id + " not found"));
    }

    @Override
    public MessageDO save(@NonNull MessageDO messageDO) {
        fillBaseDO(messageDO);
        return messageRepository.save(messageDO);
    }

    @Override
    public MessageDO update(@NonNull MessageDO messageDO) {
        return messageRepository.save(messageDO);
    }

    @Override
    public List<MessageDO> getAllMessagesBy(@NonNull UUID id, MessageStatus received, LocalDateTime localDateTime) {
        return messageRepository.findByTopicIdAndStatusAndCreatedDate(id, received, localDateTime).orElse(new ArrayList<>());
    }
}
