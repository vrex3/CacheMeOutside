package org.vrex.cacheMeOutside.utility;

import org.vrex.cacheMeOutside.config.ApplicationConstants;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class AppUtil {

    private AppUtil() {
        //enforces singleton pattern
    }

    public static final LocalDateTime currentTime() {
        return LocalDateTime.now(ZoneId.of(ApplicationConstants.DEFAULT_TIMEZONE));
    }
}
