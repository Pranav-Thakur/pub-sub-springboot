package com.example.pubsub.dao.service;

import com.example.pubsub.dao.model.TransactionDO;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface TransactionRepoService {
    List<TransactionDO> getAllTransactions();
    TransactionDO getTransactionById(@NonNull UUID id);
    TransactionDO save(@NonNull TransactionDO transactionDO);
    List<TransactionDO> getTransactionsByMessageIDOrSubscriberId(UUID messageId, UUID subscriberId);
}