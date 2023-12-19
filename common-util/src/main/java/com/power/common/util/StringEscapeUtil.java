/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.power.common.util;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Reference apache comment-text StringEscapeUtils
 *
 * @author yu 2020/4/24.
 */
public class StringEscapeUtil {


    /**
     * A Map&lt;CharSequence, CharSequence&gt; to escape the Java
     * control characters.
     * <p>
     * Namely: {@code \b \n \t \f \r}
     */
    public static final Map<CharSequence, CharSequence> JAVA_CTRL_CHARS_ESCAPE;
    private static final Map<String, String> lookupMap = new HashMap<>();

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


    public static final Map<CharSequence, CharSequence> JAVA_CTRL_CHARS_UNESCAPE;
    private static final Map<String, String> unescapeLookupMap = new HashMap<>();

    static {
        final Map<CharSequence, CharSequence> unescapeJavaMap = new HashMap<>();
        unescapeJavaMap.put("\\\\", "\\");
        unescapeJavaMap.put("\\\"", "\"");
        unescapeJavaMap.put("\\'", "'");
        unescapeJavaMap.put("\\", StringUtils.EMPTY);
        unescapeJavaMap.put("\\b", "\b");
        unescapeJavaMap.put("\\n", "\n");
        unescapeJavaMap.put("\\t", "\t");
        unescapeJavaMap.put("\\f", "\f");
        unescapeJavaMap.put("\\r", "\r");
        JAVA_CTRL_CHARS_UNESCAPE = Collections.unmodifiableMap(unescapeJavaMap);
        for (final Map.Entry<CharSequence, CharSequence> pair : JAVA_CTRL_CHARS_UNESCAPE.entrySet()) {
            char c = pair.getKey().charAt(0);
            String index = Integer.toHexString(c);
            unescapeLookupMap.put(index, pair.getValue().toString());
        }
    }

    public static Map<CharSequence, CharSequence> invert(final Map<CharSequence, CharSequence> map) {
        return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
    }

    private static boolean isOctalDigit(char ch) {
        return ch >= '0' && ch <= '7';
    }

    private static boolean isZeroToThree(char ch) {
        return ch >= '0' && ch <= '3';
    }



    /**
     * Unescapes any Java literals found in the {@code String}.
     * For example, it will turn a sequence of {@code '\'} and
     * {@code 'n'} into a newline character, unless the {@code '\'}
     * is preceded by another {@code '\'}.
     *
     * @param content the {@code String} to unescape, may be null
     * @return a new unescaped {@code String}, {@code null} if null string input
     */
    public static String unescapeJava(String content) {
        if (Objects.isNull(content)) {
            return null;
        }
        final StringBuilder tmp = new StringBuilder(content.length() * 6);
        char j;
        int index = 0;
        int len = content.length();
        while (index < len) {
            j = content.charAt(index);
            String hexString = Integer.toHexString(j);
            if (content.charAt(index) == '\\' && (content.length() - index - 1) > 0 && isOctalDigit(content.charAt(index + 1))) {
                int consumed = octalUnescape(content, index, tmp);
                for (int pt = 0; pt < consumed; pt++) {
                    index += Character.charCount(Character.codePointAt(content, index));
                }
            } else if (content.charAt(index) == '\\' && index + 1 < content.length() && content.charAt(index + 1) == 'u') {
                int consumed = unicodeUnescape(content, index, tmp);
                for (int pt = 0; pt < consumed; pt++) {
                    index += Character.charCount(Character.codePointAt(content, index));
                }
            } else if (unescapeLookupMap.containsKey(hexString)) {
                tmp.append(unescapeLookupMap.get(hexString));
                index++;
            } else {
                tmp.append(j);
                index++;
                if (Character.isHighSurrogate(j) && index < len) {
                    final char c2 = content.charAt(index);
                    if (Character.isLowSurrogate(c2)) {
                        tmp.append(c2);
                        index++;
                    }
                }
            }
        }
        return tmp.toString();
    }

    /**
     * Translates escaped Unicode values of the form \\u+\d\d\d\d back to
     * Unicode. It supports multiple 'u' characters and will work with or
     * without the +.
     *
     * @since 1.0
     */
    private static int unicodeUnescape(String input, int index, StringBuilder tmp) {
        // consume optional additional 'u' chars
        int i = 2;
        while (index + i < input.length() && input.charAt(index + i) == 'u') {
            i++;
        }

        if (index + i < input.length() && input.charAt(index + i) == '+') {
            i++;
        }

        if (index + i + 4 <= input.length()) {
            // Get 4 hex digits
            final CharSequence unicode = input.subSequence(index + i, index + i + 4);

            try {
                final int value = Integer.parseInt(unicode.toString(), 16);
                tmp.append((char)value);
            } catch (final NumberFormatException nfe) {
                throw new IllegalArgumentException("Unable to parse unicode value: " + unicode, nfe);
            }
            return i + 4;
        }
        throw new IllegalArgumentException("Less than 4 hex digits in unicode value: '"
                + input.subSequence(index, input.length())
                + "' due to end of CharSequence");
    }

    /**
     * Translate escaped octal Strings back to their octal values.
     * <p>
     * For example, "\45" should go back to being the specific value (a %).
     * <p>
     * Note that this currently only supports the viable range of octal for Java; namely
     * 1 to 377. This is because parsing Java is the main use case.
     *
     * @since 1.0
     */
    private static int octalUnescape(String content, int index, StringBuilder tmp) {
        int remaining = content.length() - index - 1;
        StringBuilder builder = new StringBuilder();
        int next = index + 1;
        int next2 = index + 2;
        int next3 = index + 3;
        builder.append(content.charAt(next));
        if (remaining > 1 && isOctalDigit(content.charAt(next2))) {
            builder.append(content.charAt(next2));
            if (remaining > 2 && isZeroToThree(content.charAt(next)) && isOctalDigit(content.charAt(next3))) {
                builder.append(content.charAt(next3));
            }
        }

        tmp.append(Integer.parseInt(builder.toString(), 8));
        return 1 + builder.length();
    }
}
