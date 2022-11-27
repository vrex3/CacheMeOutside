package org.vrex.cacheMeOutside.entity.mongo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.json.JsonObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.vrex.cacheMeOutside.config.ApplicationConstants;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Document(collection = ApplicationConstants.APPLICATION_CACHE)
public class Cache {

    @Id
    private String id;

    @Indexed
    @EqualsAndHashCode.Include
    private String name;

    @EqualsAndHashCode.Include
    @Field("key")
    @Indexed
    private JsonObject key;

    @Field("value")
    private JsonObject value;

    /**
     * Should ideally expire document at timestamp mentioned here
     */
    @Field("lastUpdated")
    @Indexed(name = "expiry", expireAfterSeconds = 0)
    private LocalDateTime expireAt;
}
