package org.vrex.cacheMeOutside.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vrex.cacheMeOutside.entity.mysql.Application;

@Repository
public interface ApplicationCacheDataRepository extends JpaRepository<Application, String> {
}
