package com.example.pubsub.dao.service;

import com.example.pubsub.dao.constants.MessageStatus;
import com.example.pubsub.dao.model.MessageDO;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface MessageRepoService {
    List<MessageDO> getAllMessages();
    MessageDO getMessageById(@NonNull UUID id);
    MessageDO save(@NonNull MessageDO messageDO);
    MessageDO update(@NonNull MessageDO messageDO);
    List<MessageDO> getAllMessagesBy(@NonNull UUID topicId, MessageStatus received, LocalDateTime localDateTime);

    default void fillBaseDO(@NonNull MessageDO messageDO) {
        messageDO.setVersion(1);
        messageDO.setCreatedDate(LocalDateTime.now());
        messageDO.setUpdatedDate(LocalDateTime.now());
    }

}