package com.example.pubsub.dao.service.impl;

import com.example.pubsub.dao.repository.TransactionRepository;
import com.example.pubsub.dao.service.TransactionRepoService;
import com.example.pubsub.dao.model.TransactionDO;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionRepoServiceImpl implements TransactionRepoService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<TransactionDO> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public TransactionDO getTransactionById(UUID id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public TransactionDO save(@NonNull TransactionDO transactionDO) {
        return transactionRepository.save(transactionDO);
    }

    @Override
    public List<TransactionDO> getTransactionsByMessageIDOrSubscriberId(UUID messageId, UUID subscriberId) {
        return List.of();
    }
}
