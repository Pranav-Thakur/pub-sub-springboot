package com.example.pubsub.dao.service.impl;

import com.example.pubsub.dao.repository.AppUserRepository;
import com.example.pubsub.dao.service.AppUserRepoService;
import com.example.pubsub.dao.model.AppUserDO;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppUserRepoServiceImpl implements AppUserRepoService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public List<AppUserDO> getAllAppUsers() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUserDO getAppUserById(@NonNull UUID id) {
        return appUserRepository.findById(id).orElse(null);
    }

    @Override
    public AppUserDO save(@NonNull AppUserDO appUserDO) {
        fillBaseDO(appUserDO);
        return appUserRepository.save(appUserDO);
    }

    @Override
    public AppUserDO update(@NonNull AppUserDO appUserDO) {
        return appUserRepository.save(appUserDO);
    }
}
