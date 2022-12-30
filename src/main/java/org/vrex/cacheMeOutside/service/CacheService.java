package org.vrex.cacheMeOutside.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.vrex.cacheMeOutside.dto.CacheData;
import org.vrex.cacheMeOutside.dto.NewCacheRequest;
import org.vrex.cacheMeOutside.entity.mongo.Cache;
import org.vrex.cacheMeOutside.entity.mysql.Application;
import org.vrex.cacheMeOutside.entity.mysql.CacheMetadata;
import org.vrex.cacheMeOutside.recognito.Client;
import org.vrex.cacheMeOutside.repository.ApplicationRepository;
import org.vrex.cacheMeOutside.repository.CacheMetadataRepository;
import org.vrex.cacheMeOutside.repository.CacheRepository;
import org.vrex.cacheMeOutside.utility.AppUtil;
import org.vrex.cacheMeOutside.utility.ApplicationConstants;
import org.vrex.cacheMeOutside.utility.ApplicationException;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class CacheService {

    private final String LOG_TEXT = "CACHE_MODULE : ";
    private final String ERROR_TEXT = "CACHE_MODULE_ERROR : ";

    @Autowired
    private CacheMetadataRepository cacheMetadataRepository;

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private Client recognitoClient;

    private final Gson mapper = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Saves a new cache
     * Generates UUID for cache
     * If linked application is not found, exception is thrown
     *
     * @param request
     * @return
     */
    @Transactional
    public CacheMetadata saveNewCache(NewCacheRequest request) {
        String cacheName = request.getCacheName();
        String app = request.getAppUUID();

        log.info("{} Adding cache {} for app {}", LOG_TEXT, cacheName, app);
        CacheMetadata cacheMetadata = null;
        try {
            Optional<Application> applicationRequest = applicationRepository.findById(request.getAppUUID());
            if (applicationRequest.isEmpty()) {
                log.error("{} Could not locate app {}", ERROR_TEXT, app);
                throw ApplicationException.builder().
                        errorMessage(ApplicationConstants.INVALID_APPLICATION_IDENTIFIER).
                        status(HttpStatus.BAD_REQUEST).
                        build();
            }

            Application application = applicationRequest.get();
            if (application.getCache().contains(cacheName)) {
                log.error("{} Cache {} already exists for app {}", ERROR_TEXT, cacheName, app);
                throw ApplicationException.builder().
                        errorMessage(ApplicationConstants.DUPLICATE_CACHE).
                        status(HttpStatus.BAD_REQUEST).
                        build();
            }
            String cacheUUID = UUID.randomUUID().toString();

            log.info("{} Attempting to persist cache {} with UUID {} for app {}", LOG_TEXT, cacheName, cacheUUID, app);
            cacheMetadata = new CacheMetadata(
                    cacheUUID,
                    application,
                    request.getCacheName(),
                    request.getTtlMinutes() != null ? request.getTtlMinutes() : ApplicationConstants.DEFAULT_TIME_TO_LIVE_MINUTES,
                    AppUtil.currentTime());

            cacheMetadata = cacheMetadataRepository.saveAndFlush(cacheMetadata);
            application.addCache(cacheMetadata);
            applicationRepository.saveAndFlush(application);

            log.info("{} Saved cache {} with UUID {} for app {}", LOG_TEXT, cacheName, cacheUUID, app);
        } catch (ApplicationException exception) {
            throw exception;
        } catch (Exception exception) {
            log.error("{} Adding cache {} for app {} -> {}", ERROR_TEXT, cacheName, app, exception);
            throw ApplicationException.builder().
                    errorMessage(exception.getMessage()).
                    status(HttpStatus.INTERNAL_SERVER_ERROR).
                    build();
        }
        return cacheMetadata;
    }

    /**
     * Adds data (key-value) pair to specified cache name
     * TTL is handled by mongo - expiry decided by ttl attribute in cache metadata
     * Data is not persisted if value is null
     *
     * @param data
     */
    @Transactional
    @Async
    public void saveDataToCache(CacheData data) {
        String cacheName = data.getCacheName();
        String app = data.getAppId();

        log.info("{} Adding data to cache {} for app {}", LOG_TEXT, cacheName, app);
        if (!ObjectUtils.isEmpty(data.getValue())) {
            try {
                CacheMetadata metadata = cacheMetadataRepository.findByAppAndName(app, cacheName);
                if (ObjectUtils.isEmpty(metadata)) {
                    log.error("{} Could not locate cache {}", ERROR_TEXT, cacheName);
                    throw ApplicationException.builder().
                            errorMessage(ApplicationConstants.INVALID_CACHE_IDENTIFIER).
                            status(HttpStatus.BAD_REQUEST).
                            build();
                }
                Cache cacheData = new Cache(
                        metadata.getUuid(),
                        mapper.toJson(data.getKey()),
                        mapper.toJson(data.getValue()),
                        AppUtil.currentTime().plusMinutes(metadata.getTtl()));

                cacheData = cacheRepository.save(cacheData);
                log.info("{} Added data to cache {} for app {}", LOG_TEXT, cacheName, app);
            } catch (ApplicationException exception) {
                throw exception;
            } catch (Exception exception) {
                log.error("{} Adding data cache {} for app {} -> {}", ERROR_TEXT, cacheName, app, exception);
                throw ApplicationException.builder().
                        errorMessage(exception.getMessage()).
                        status(HttpStatus.INTERNAL_SERVER_ERROR).
                        build();
            }
        }
    }

    /**
     * Extracts data of type T from specified cache for specified key
     * Null is returned if nothing is found
     *
     * @param data
     * @param type
     * @param <T>
     * @return
     */
    public <T> T getDataFromCache(CacheData data, Class<T> type) {
        T response = null;
        String cacheName = data.getCacheName();
        String app = data.getAppId();

        log.info("{} Extracting data to cache {} for app {}", LOG_TEXT, cacheName, app);
        try {
            CacheMetadata metadata = cacheMetadataRepository.findByAppAndName(data.getAppId(), data.getCacheName());
            if (ObjectUtils.isEmpty(metadata)) {
                log.error("{} Could not locate cache {}", ERROR_TEXT, cacheName);
                throw ApplicationException.builder().
                        errorMessage(ApplicationConstants.INVALID_CACHE_IDENTIFIER).
                        status(HttpStatus.BAD_REQUEST).
                        build();
            }

            Cache cacheData = cacheRepository.findData(metadata.getUuid(), mapper.toJson(data.getKey()));
            if (!ObjectUtils.isEmpty(cacheData))
                response = mapper.fromJson(cacheData.getValue(), type);
        } catch (ApplicationException exception) {
            throw exception;
        } catch (JsonSyntaxException exception) {
            log.error("{} Syntax error while extracting data cache {} for app {} -> {}", ERROR_TEXT, cacheName, app, exception);
            throw ApplicationException.builder().
                    errorMessage(ApplicationConstants.INVALID_VALUE_SYNTAX).
                    status(HttpStatus.INTERNAL_SERVER_ERROR).
                    build();
        } catch (Exception exception) {
            log.error("{} Extracting data cache {} for app {} -> {}", ERROR_TEXT, cacheName, app, exception);
            throw ApplicationException.builder().
                    errorMessage(exception.getMessage()).
                    status(HttpStatus.INTERNAL_SERVER_ERROR).
                    build();
        }
        return response;
    }
}
