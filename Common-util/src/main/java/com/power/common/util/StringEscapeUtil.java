package com.power.common.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author yu 2020/4/24.
 */
public class StringEscapeUtil {


    private static final Map<String, String> lookupMap = new HashMap<>();
    /**
     * A Map&lt;CharSequence, CharSequence&gt; to escape the Java
     * control characters.
     * <p>
     * Namely: {@code \b \n \t \f \r}
     */
    public static final Map<CharSequence, CharSequence> JAVA_CTRL_CHARS_ESCAPE;

    static {
        final Map<CharSequence, CharSequence> initialMap = new HashMap<>();
        initialMap.put("\b", "\\b");
        initialMap.put("\n", "\\n");
        initialMap.put("\t", "\\t");
        initialMap.put("\f", "\\f");
        initialMap.put("\r", "\\r");
        initialMap.put("\"", "\\\"");
        initialMap.put("\\", "\\\\");
        JAVA_CTRL_CHARS_ESCAPE = Collections.unmodifiableMap(initialMap);
        for (final Map.Entry<CharSequence, CharSequence> pair : JAVA_CTRL_CHARS_ESCAPE.entrySet()) {
            char c = pair.getKey().charAt(0);
            String index = Integer.toHexString(c);
            lookupMap.put(index, pair.getValue().toString());
        }
    }

    /**
     * <p>Escapes the characters in a {@code String} using Java String rules.</p>
     * This method does not encode ASCII letters and numbers, nor does it encode the following ASCII punctuation marks: * @-_ +. / <br>
     * All other characters will be replaced by escape sequences.
     *
     * @param content String to escape values in, may be null
     * @return String with escaped values, {@code null} if null string input
     */
    public static String escapeJava(String content) {
        return escapeJava(content, Boolean.FALSE);
    }

    /**
     * <p>Escapes the characters in a {@code String} using Java String rules.</p>
     * This method does not encode ASCII letters and numbers, nor does it encode the following ASCII punctuation marks: * @-_ +. / <br>
     * All other characters will be replaced by escape sequences.
     *
     * @param content String to escape values in, may be null
     * @return String with escaped values, {@code null} if null string input
     */
    public static String escapeJavaIgnoreChinese(String content) {
        return escapeJava(content, Boolean.TRUE);
    }

    /**
     * <p>Escapes the characters in a {@code String} using Java String rules.</p>
     * This method does not encode ASCII letters and numbers. All other characters will be replaced by escape sequences.
     *
     * @param content       String to escape values in, may be null
     * @param ignoreChinese ignore chinese
     * @return String with escaped values, {@code null} if null string input
     */
    public static String escapeJava(String content, Boolean ignoreChinese) {
        if (Objects.isNull(content)) {
            return null;
        }
        final StringBuilder tmp = new StringBuilder(content.length() * 6);
        char j;
        for (int i = 0; i < content.length(); i++) {
            j = content.charAt(i);
            String index = Integer.toHexString(j);
            if (lookupMap.containsKey(index)) {
                tmp.append(lookupMap.get(index));
            } else if ((j >= 0x4e00) && (j <= 0x9fbb) && ignoreChinese) {
                tmp.append(j);
            } else if ((j >= 32) && (j <= 0xf7)) {
                tmp.append(j);
            } else {
                tmp.append("\\u");
                tmp.append(Character.toUpperCase(Character.forDigit((j >>> 12) & 0xf, 16)));
                tmp.append(Character.toUpperCase(Character.forDigit((j >>> 8) & 0xf, 16)));
                tmp.append(Character.toUpperCase(Character.forDigit((j >>> 4) & 0xf, 16)));
                tmp.append(Character.toUpperCase(Character.forDigit((j) & 0xf, 16)));
            }
        }
        return tmp.toString();
    }
}
