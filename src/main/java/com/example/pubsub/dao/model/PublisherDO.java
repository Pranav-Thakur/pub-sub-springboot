package com.example.pubsub.dao.model;

import com.example.pubsub.dao.constants.PublisherStatus;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Data
@Table(name = "publisher", indexes = {
        @Index(name = "idx_publisher_company_name", columnList = "companyName")
})
public class PublisherDO extends AuditableBaseDO {
    @Id
    @GeneratedValue
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;
    private String companyName;
    @Lob
    private String info;

    @Enumerated(EnumType.STRING)
    private PublisherStatus status;
}
