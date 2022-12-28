package org.vrex.cacheMeOutside.recognito.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecognitoApplication implements Serializable {

    private String name;
    private String description;
    private boolean resourcesEnabled;
    private String email;


}
