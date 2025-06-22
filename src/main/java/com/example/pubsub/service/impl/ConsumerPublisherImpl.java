package com.example.pubsub.service.impl;

import com.example.pubsub.dao.constants.MessageStatus;
import com.example.pubsub.dao.constants.TransactionStatus;
import com.example.pubsub.dao.model.MessageDO;
import com.example.pubsub.dao.model.SubscriberDO;
import com.example.pubsub.dao.model.TransactionDO;
import com.example.pubsub.dao.service.MessageRepoService;
import com.example.pubsub.dao.service.TransactionRepoService;
import com.example.pubsub.dto.ConsumerPublisherDTO;
import com.example.pubsub.service.ConsumerPublisher;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.FluxSink;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ConsumerPublisherImpl implements ConsumerPublisher {

    private final Map<UUID, ConsumerPublisherDTO> subscribers = new ConcurrentHashMap<>();

    @Autowired
    private TransactionRepoService transactionRepoService;

    @Autowired
    private MessageRepoService messageRepoService;

    @Override
    public void registerListener(@NonNull ConsumerPublisherDTO dto, @NonNull FluxSink<String> listener) {
        subscribers.computeIfAbsent(dto.getTopicId(), k -> dto).add(listener);
    }

    // Runs every 10 seconds (example)
    @Scheduled(cron = "0 */10 * * * *")
    public void deliverPendingMessages() {
        System.out.println("🔁 Running cron to deliver messages...");

        LocalDateTime localDateTime = LocalDateTime.of(1970, 1, 1, 0, 0);
        List<MessageDO> messageDOList = new ArrayList<>();
        for (UUID topicId : subscribers.keySet())
            messageDOList.addAll(messageRepoService.getAllMessagesBy(topicId, MessageStatus.RECEIVED, localDateTime));

        for (MessageDO messageDO : messageDOList) {
            if (publishForTopic(messageDO.getTopic().getId(), messageDO)) {
                messageDO.setStatus(MessageStatus.SENT);
                messageDO = messageRepoService.save(messageDO);
            }
        }
    }

    @Override
    public boolean publishForTopic(@NonNull UUID topicId, @NonNull MessageDO messageDO) {
        if (!messageDO.getStatus().equals(MessageStatus.RECEIVED)) return false;

        ConsumerPublisherDTO dto = subscribers.get(topicId);
        if (dto != null && !dto.isEmpty()) {
            if (dto.getOffsetTime().isAfter(messageDO.getCreatedDate())) {
                // older messages
            }
            int sinkClosed = 0, sinkOpen = 0;
            while (sinkClosed < dto.size()) {
                FluxSink<String> sink = dto.get(new Random().nextInt(dto.size()));
                if (!sink.isCancelled()) {
                    sink.next(messageDO.getData());
                    sinkOpen++;
                    break;
                } else sinkClosed++;
            }

            if (sinkOpen == 0) return false;

            TransactionDO transactionDO = new TransactionDO();
            transactionDO.setMessage(messageDO);
            SubscriberDO subscriberDO = new SubscriberDO();
            subscriberDO.setId(dto.getSubscriberId());
            transactionDO.setSubscriber(subscriberDO);
            transactionDO.setStatus(TransactionStatus.SUCCESS);
            transactionRepoService.save(transactionDO);
            return true;
        }

        return false;
    }
}
