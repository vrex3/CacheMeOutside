package org.vrex.cacheMeOutside.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vrex.cacheMeOutside.utility.ApplicationConstants;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CacheData implements Serializable {

    @NotNull(message = ApplicationConstants.EMPTY_APPLICATION_IDENTIFIER)
    private String appId;
    @NotNull(message = ApplicationConstants.EMPTY_CACHE_NAME)
    private String cacheName;
    @NotNull(message = ApplicationConstants.EMPTY_CACHE_KEY)
    private Object key;
    private Object value;
}
