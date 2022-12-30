package org.vrex.cacheMeOutside.recognito;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class Client {

    private final String LOG_TEXT = "RECOGNITO_CLIENT : ";
    private final String ERROR_TEXT = "RECOGNITO_CLIENT_ERROR : ";

    private final String INSERT_APPLICATION_URL = "/application";

    @Autowired
    private RestTemplate restTemplate;




}
