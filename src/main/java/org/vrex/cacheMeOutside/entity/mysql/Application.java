package org.vrex.cacheMeOutside.entity.mysql;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.util.ObjectUtils;
import org.vrex.cacheMeOutside.utility.ApplicationConstants;

import java.util.HashSet;
import java.util.Set;

/**
 * Stores the properties of user defined caches
 */


@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = ApplicationConstants.APPLICATION)
public class Application {

    @Id
    @GenericGenerator(name = "application_id",
            strategy = "org.vrex.cacheMeOutside.entity.mysql.ApplicationCacheDataIdGenerator")
    @GeneratedValue(generator = "application_id_generator")
    @EqualsAndHashCode.Include
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "application")
    private Set<CacheMetadata> cache;

    /**
     * Adds new cache metadata to application
     *
     * @param cache
     */
    public void addCache(CacheMetadata cache) {
        if (ObjectUtils.isEmpty(this.cache))
            this.cache = new HashSet<>();
        this.cache.add(cache);
    }
}
