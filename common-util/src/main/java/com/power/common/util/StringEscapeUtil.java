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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Reference apache comment-text StringEscapeUtils
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
}
