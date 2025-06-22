package com.example.pubsub.dao.model;

import com.example.pubsub.dao.constants.TransactionStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "transaction", indexes = {
        @Index(name = "idx_message_id", columnList = "message_id"),
        @Index(name = "idx_subscriber_id", columnList = "subscriber_id")
})
public class TransactionDO extends AuditableBaseDO {
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

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
}
