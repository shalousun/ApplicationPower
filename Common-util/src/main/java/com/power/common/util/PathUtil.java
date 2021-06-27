/*
 * smart-doc https://github.com/shalousun/smart-doc
 *
 * Copyright (C) 2018-2021 smart-doc
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.power.common.util;

import java.util.List;

/**
 * reference Spring
 * Created by yu on 2021/6/27.
 */
public class PathUtil {

    /**
     * Determine a match for the given lookup path.
     *
     * @param lookupPath      the request path
     * @param includePatterns the path patterns to map (empty for matching to all paths)
     * @param excludePatterns the path patterns to exclude (empty for no specific excludes)
     * @return {@code true} if matched the request path
     */
    public static boolean matches(String lookupPath, List<String> includePatterns, List<String> excludePatterns) {
        PathMatcher pathMatcherToUse = new PathMatcher();
        if (CollectionUtil.isNotEmpty(excludePatterns)) {
            for (String pattern : excludePatterns) {
                if (pathMatcherToUse.match(pattern, lookupPath)) {
                    return false;
                }
            }
        }
        if (CollectionUtil.isEmpty(includePatterns)) {
            return true;
        }
        for (String pattern : includePatterns) {
            if (pathMatcherToUse.match(pattern, lookupPath)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determine a match for the given lookup path.
     *
     * @param lookupPath   the request path
     * @param pathPatterns the path patterns to map (empty for matching to all paths)
     * @return {@code true} if matched the request path
     */
    public static boolean matches(String lookupPath, List<String> pathPatterns) {
        return matches(lookupPath, pathPatterns, null);
    }
}
