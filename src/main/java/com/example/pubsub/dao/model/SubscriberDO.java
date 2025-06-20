package com.example.pubsub.dao.model;

import com.example.pubsub.dao.constants.SubscriberStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "subscriber", indexes = {
        @Index(name = "idx_topic_id", columnList = "topic_id"),
        @Index(name = "idx_user_id", columnList = "user_id")
})
public class SubscriberDO {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AppUserDO user;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private TopicDO topic;

    @Enumerated(EnumType.STRING)
    private SubscriberStatus status;

    private LocalDateTime lastMessageDeliveredDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer version;
}
