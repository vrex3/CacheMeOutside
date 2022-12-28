package org.vrex.cacheMeOutside.service;

import com.google.gson.Gson;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vrex.cacheMeOutside.dto.CacheData;
import org.vrex.cacheMeOutside.dto.NewCacheRequest;
import org.vrex.cacheMeOutside.entity.mongo.Cache;
import org.vrex.cacheMeOutside.entity.mysql.Application;
import org.vrex.cacheMeOutside.entity.mysql.CacheMetadata;
import org.vrex.cacheMeOutside.repository.ApplicationRepository;
import org.vrex.cacheMeOutside.repository.CacheMetadataRepository;
import org.vrex.cacheMeOutside.repository.CacheRepository;
import org.vrex.cacheMeOutside.utility.AppUtil;

import java.util.UUID;

@Service
@Slf4j
public class CacheService {

    @Autowired
    private CacheMetadataRepository cacheMetadataRepository;

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    private Gson mapper = new Gson();

    @Transactional
    public CacheMetadata saveNewCache(NewCacheRequest request) {
        Application application = applicationRepository.findById(request.getAppUUID()).get();
        CacheMetadata cacheMetadata = new CacheMetadata(
                UUID.randomUUID().toString(),
                application,
                request.getCacheName(),
                request.getTtlMinutes(),
                AppUtil.currentTime());

        cacheMetadata = cacheMetadataRepository.saveAndFlush(cacheMetadata);
        application.addCache(cacheMetadata);
        applicationRepository.saveAndFlush(application);
        return cacheMetadata;
    }

    @Transactional
    public String saveDataToCache(CacheData data) {
        CacheMetadata metadata = cacheMetadataRepository.findByAppAndName(data.getAppId(), data.getCacheName());
        Cache cacheData = new Cache(metadata.getUuid(), mapper.toJson(data.getKey()), mapper.toJson(data.getValue()), AppUtil.currentTime().plusMinutes(metadata.getTtl()));
        cacheData = cacheRepository.save(cacheData);
        return cacheData.getId();
    }

    public <T> T getDataFromCache(CacheData data, Class<T> type) {
        CacheMetadata metadata = cacheMetadataRepository.findByAppAndName(data.getAppId(), data.getCacheName());
        Cache cacheData = cacheRepository.findData(metadata.getUuid(), mapper.toJson(data.getKey()));
        return mapper.fromJson(cacheData.getValue(), type);
    }
}
