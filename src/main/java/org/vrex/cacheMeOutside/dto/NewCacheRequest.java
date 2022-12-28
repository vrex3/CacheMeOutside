package org.vrex.cacheMeOutside.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewCacheRequest implements Serializable {

    private String appUUID;
    private String cacheName;
    private int ttlMinutes;
}
