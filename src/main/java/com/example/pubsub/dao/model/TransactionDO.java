package com.example.pubsub.dao.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "transaction", indexes = {
        @Index(name = "idx_message_id", columnList = "message_id"),
        @Index(name = "idx_subscriber_id", columnList = "subscriber_id")
})
public class TransactionDO {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private MessageDO message;

    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private SubscriberDO subscriber;

    private String status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private Integer version;
}
