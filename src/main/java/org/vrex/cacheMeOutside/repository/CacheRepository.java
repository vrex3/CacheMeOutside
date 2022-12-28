package org.vrex.cacheMeOutside.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vrex.cacheMeOutside.entity.mongo.Cache;

public interface CacheRepository extends MongoRepository<Cache, String> {
}
