package org.vrex.cacheMeOutside.entity.mysql;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.util.Random;
import java.util.UUID;

/**
 * Custom ID generator for cache
 */
public class CacheIdGenerator implements IdentifierGenerator {

    /**
     * Generates a timestamp based UUID
     *
     * @param sharedSessionContractImplementor
     * @param o
     * @return
     * @throws HibernateException
     */
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return generateUUID().toString();
    }

    /**
     * Generates a version 1 UUID
     * Uses current timestamp and Random generated number
     * Refer : https://www.baeldung.com/java-uuid
     *
     * @return
     */
    public UUID generateUUID() {
        return new UUID(get64MostSignificantBits(), get64LeastSignificantBits());
    }

    /**
     * Generates the least significant 64 bits for UUID
     * Uses random 48 bit number (instead of system MAC address)
     * Also includes 3 bit variant flag
     *
     * @return
     */
    private long get64LeastSignificantBits() {
        return (new Random().nextLong() & 0x3FFFFFFFFFFFFFFFL) + (0x8000000000000000L);
    }

    /**
     * Generates the most significant 64 bit bits for UUID
     * Uses timestamp measured in units of 100 nanoseconds from October 15, 1582
     *
     * @return
     */
    private long get64MostSignificantBits() {
        final long timeForUuidIn100Nanos = System.currentTimeMillis();
        final long time_low = (timeForUuidIn100Nanos & 0x0000_0000_FFFF_FFFFL) << 32;
        final long time_mid = ((timeForUuidIn100Nanos >> 32) & 0xFFFF) << 16;
        final long version = 1 << 12;
        final long time_hi = ((timeForUuidIn100Nanos >> 48) & 0x0FFF);
        return time_low + time_mid + version + time_hi;
    }


}
