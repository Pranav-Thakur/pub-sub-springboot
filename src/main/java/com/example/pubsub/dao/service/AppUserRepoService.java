package com.example.pubsub.dao.service;

import com.example.pubsub.dao.model.AppUserDO;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

public interface AppUserRepoService {
    List<AppUserDO> getAllAppUsers();
    AppUserDO getAppUserById(@NonNull UUID id);
    AppUserDO save(@NonNull AppUserDO appUserDO);
}