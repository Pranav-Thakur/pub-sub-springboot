package com.example.pubsub.dao.service.impl;

import com.example.pubsub.dao.repository.TransactionRepository;
import com.example.pubsub.dao.service.TransactionRepoService;
import com.example.pubsub.dao.model.TransactionDO;
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
}
