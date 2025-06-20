package com.example.pubsub.dao.service;

import com.example.pubsub.dao.model.TransactionDO;

import java.util.List;
import java.util.UUID;

public interface TransactionRepoService {
    List<TransactionDO> getAllTransactions();
    TransactionDO getTransactionById(UUID id);
}