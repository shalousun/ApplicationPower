package com.power.common.util;

import com.power.common.exception.AssertException;

import java.util.Objects;

/**
 * The Assert class provides static methods for performing assertion checks.
 * @javadoc
 * @author yu 2019/11/1.
 */
public class Assert {

    /**
     * Checks if an object is not null. If it is null, it throws an AssertException.
     * @apiNote This method is typically used to validate that essential inputs are not null, ensuring the correctness of method execution.
     *
     * @param object The object to check for null. Should not be null.
     * @param message The error message to show when the assertion fails. Can contain format specifiers.
     * @param args Arguments to be used for formatting the error message.
     * @throws AssertException If the object is null, an AssertException is thrown, containing the formatted error message.
     */
    public static void notNull(Object object, String message, Object... args) {
        if (Objects.isNull(object)) {
            throw new AssertException(String.format(message, args));
        }
    }
}
