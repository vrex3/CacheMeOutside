package org.vrex.cacheMeOutside.config;

public class ApplicationConstants {

    private ApplicationConstants() {
        //enforces singleton pattern
    }

    /**
     * DATABASE CONSTANTS
     */
    public static final String APPLICATION = "APPLICATION";
    public static final String APPLICATION_CACHE = "CMO_CACHE";

    /**
     * CACHE CONSTANTS
     */
    public static final Integer DEFAULT_TIME_TO_LIVE_SECONDS = 3600; // 1 hour
}
