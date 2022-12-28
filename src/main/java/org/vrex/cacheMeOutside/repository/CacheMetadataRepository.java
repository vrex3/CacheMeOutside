package org.vrex.cacheMeOutside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.vrex.cacheMeOutside.entity.mysql.Application;
import org.vrex.cacheMeOutside.entity.mysql.CacheMetadata;

@Repository
public interface CacheMetadataRepository extends JpaRepository<CacheMetadata, String> {

    // CAFFIENE CACHE THIS
    @Query("select c from CacheMetadata c where c.application.id = :app and c.name = :name")
    public CacheMetadata findByAppAndName(@Param("app") String appId, @Param("name") String cacheName);
}
