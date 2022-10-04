package com.power.common.util;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.UUID;

/**
 * UUID util
 * @author yu 2018/10/14.
 */
public class UUIDUtil {


    /**
     * uuid
     *
     * @return string
     */
    public static String getUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * Generated string id length to 32
     *
     * @return string
     */
    public static String getUuid32() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 32-bit string is restored to Uuid
     *
     * @param uuid uuid
     * @return UUID
     */
    public static UUID fromString32(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            throw new NullPointerException("uuid can't be null");
        }
        if (uuid.length() == 36) {
            return UUID.fromString(uuid);
        } else if (uuid.length() == 32) {
            StringBuilder uuidBuilder = new StringBuilder();
            uuidBuilder.append(uuid, 0, 8).append("-")
                    .append(uuid, 8, 12).append("-")
                    .append(uuid, 12, 16).append("-")
                    .append(uuid, 16, 24).append("-")
                    .append(uuid, 24, 32);
            return UUID.fromString(uuidBuilder.toString());
        }
        return null;
    }

    /**
     * Convert uuid to BigInteger
     *
     * @param uuid uuid
     * @return BigInteger
     */
    public static BigInteger uuidToBigInteger(UUID uuid) {
        BigInteger value1 = BigInteger.valueOf(uuid.getMostSignificantBits());
        BigInteger value2 = BigInteger.valueOf(uuid.getLeastSignificantBits());
        if (value1.compareTo(value2) < 0) {
            return value2.multiply(value2).add(value1);
        }
        return value1.multiply(value1).add(value1).add(value2);
    }
}
