package org.vrex.cacheMeOutside.utility;

public class ApplicationConstants {

    private ApplicationConstants() {
        //enforces singleton pattern
    }

    /**
     * APPLICATION CONSTANTS
     */
    public static final String DEFAULT_TIMEZONE = "UTC";

    /**
     * DATABASE CONSTANTS
     */
    public static final String APPLICATION = "APPLICATION";
    public static final String APPLICATION_CACHE = "CMO_CACHE";
    public static final String CACHE_METADATA = "CACHE_METADATA";

    /**
     * CACHE CONSTANTS
     */
    public static final Integer DEFAULT_TIME_TO_LIVE_MINUTES = 60; // 1 hour

    /**
     * ERROR CONSTANTS
     */

    public static final String EMPTY_APPLICATION_IDENTIFIER = "No app identifier found";
    public static final String INVALID_APPLICATION_IDENTIFIER = "Could not locate app";
    public static final String EMPTY_CACHE_NAME = "No cache name found";
    public static final String INVALID_CACHE_IDENTIFIER = "Could not locate cache";
    public static final String EMPTY_CACHE_KEY = "No key found to save data";
    public static final String DUPLICATE_CACHE = "Cache with same name already exists for application";
    public static final String INVALID_VALUE_SYNTAX = "Could not map value to provided syntax";


}
