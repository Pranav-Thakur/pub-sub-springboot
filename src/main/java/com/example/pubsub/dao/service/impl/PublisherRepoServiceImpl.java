package com.example.pubsub.dao.service.impl;

import com.example.pubsub.dao.repository.PublisherRepository;
import com.example.pubsub.dao.service.PublisherRepoService;
import com.example.pubsub.dao.model.PublisherDO;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
public class PublisherRepoServiceImpl implements PublisherRepoService {

    @Autowired
    private PublisherRepository publisherRepository;

    @Override
    public List<PublisherDO> getAllPublishers() {
        return publisherRepository.findAll();
    }

    @Override
    public PublisherDO getPublisherById(@NonNull UUID id) {
        return publisherRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Publisher with id " + id + " not found"));
    }

    @Override
    public PublisherDO getPublisherByName(@NonNull String companyName) {
        return publisherRepository.findByCompanyName(companyName).orElseThrow(() ->
                new EntityNotFoundException("Publisher with name " + companyName + " already found."));
    }

    @Override
    public PublisherDO save(@NonNull PublisherDO publisherDO) {
        fillBaseDO(publisherDO);
        return publisherRepository.save(publisherDO);
    }

    @Override
    public PublisherDO update(@NonNull PublisherDO updatedPublisherDO) {
        return publisherRepository.save(updatedPublisherDO);
    }

    @Override
    public void delete(@NonNull UUID id) {

    }
}
