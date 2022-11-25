package org.vrex.cacheMeOutside.entity.mysql;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.vrex.cacheMeOutside.config.ApplicationConstants;

/**
 * Stores the properties of user defined caches
 */


@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = ApplicationConstants.APPLICATION_CACHE_DATA)
public class ApplicationCacheData {

    @Id
    @GenericGenerator(name = "application_cache_id",
            strategy = "org.vrex.cacheMeOutside.entity.mysql.ApplicationCacheDataIdGenerator")
    @GeneratedValue(generator = "application_cache_id")
    @EqualsAndHashCode.Include
    private String id;

}
