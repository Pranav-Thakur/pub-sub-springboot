package com.example.pubsub.dao.model;

import com.example.pubsub.dao.constants.TopicStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "topic", indexes = {
        @Index(name = "idx_topic_name", columnList = "topicName")
})
public class TopicDO extends AuditableBaseDO {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private PublisherDO publisher;

    private String topicName;

    @Enumerated(EnumType.STRING)
    private TopicStatus status;
}
