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
public class CacheData implements Serializable {

    private String appId;
    private String cacheName;
    private Object key;
    private Object value;
}
