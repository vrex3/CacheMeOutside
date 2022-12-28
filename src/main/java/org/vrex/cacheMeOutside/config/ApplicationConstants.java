package org.vrex.cacheMeOutside.config;

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
    public static final Integer DEFAULT_TIME_TO_LIVE_SECONDS = 3600; // 1 hour
}
