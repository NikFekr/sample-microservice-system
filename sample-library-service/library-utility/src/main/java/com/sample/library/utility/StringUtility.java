package com.sample.library.utility;

import java.nio.charset.StandardCharsets;

/**
 * @author Esmaeil NikFekr on 3/28/21.
 */
public class StringUtility {
    public static byte[] getSabtAhvalStringEncoding(String entry) {
        return entry.getBytes(StandardCharsets.UTF_8);
    }

    public static String getStringFormSabtAhvalStringEncoding(byte[] entry) {
        return new String(entry, StandardCharsets.UTF_8);
    }

    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isBlank();
    }
}
