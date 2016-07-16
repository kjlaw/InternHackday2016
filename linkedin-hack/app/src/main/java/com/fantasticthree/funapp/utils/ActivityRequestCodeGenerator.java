package com.fantasticthree.funapp.utils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Utility to generate unique values for request codes to be used in {@link Activity#startActivityForResult(Intent, int)}
 * This should ALWAYS be used so different activity request codes across the app don't conflict.
 */
public class ActivityRequestCodeGenerator {
    private static final AtomicInteger sCodeGenerationSeed = new AtomicInteger();

    /**
     * @return a new unique activity request code number.
     */
    public static int next() {
        return sCodeGenerationSeed.incrementAndGet();
    }
}