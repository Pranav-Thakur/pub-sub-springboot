package com.example.pubsub.dao.model;

import com.example.pubsub.dao.constants.MessageStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "message", indexes = {
        @Index(name = "idx_message_topic_id", columnList = "topic_id")
})
public class MessageDO extends AuditableBaseDO {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String data;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private TopicDO topic;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;
}
