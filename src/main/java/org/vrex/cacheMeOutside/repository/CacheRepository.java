package org.vrex.cacheMeOutside.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.vrex.cacheMeOutside.entity.mongo.Cache;

@Repository
public interface CacheRepository extends MongoRepository<Cache, String> {

    @Query("{uuid:?0, key:?1}")
    public Cache findData(String cacheUUID, String key);
}
