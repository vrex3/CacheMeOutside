package org.vrex.cacheMeOutside.entity.mysql;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
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

    /**
     * Create application entity
     * Register applications in this app.
     * Each application is registered as inidividual application
     * Store application invite secrets here
     * OR
     * register this application
     * treat applications using this app as users in Recognito
     * lack of session management in recognito will deal with multiple instances of app logging on then
     */
    //private Application application;

    @Column(name = "time_to_live", nullable = false)
    private Integer ttl;

}
