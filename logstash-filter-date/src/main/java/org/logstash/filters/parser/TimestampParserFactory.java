/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.logstash.filters.parser;

import org.joda.time.DateTimeZone;

import java.util.Locale;

public class TimestampParserFactory {
  private DateTimeZone timezone;

  private static final String ISO8601 = "ISO8601";
  private static final String UNIX = "UNIX";
  private static final String UNIX_MS = "UNIX_MS";
  private static final String TAI64N = "TAI64N";

  /*
   * zone is a String because it can be dynamic and come from the event while we parse it.
   */
  public static TimestampParser makeParser(String pattern, Locale locale, String zone) {
    if (locale == null) {
      locale = Locale.getDefault();
    }

    String tz = zone;

    if (tz == null) {
      tz = DateTimeZone.getDefault().getID();
    } else if (zone.contains("%{")) {
      tz = null;
    }

    switch (pattern) {
      case ISO8601: // Short-hand for a few ISO8601-ish formats
        return new CasualISO8601Parser(tz);
      case UNIX: // Unix epoch in seconds
        return new UnixEpochParser();
      case TAI64N: // TAI64N format
        return new TAI64NParser();
      case UNIX_MS: // Unix epoch in milliseconds
        return new UnixMillisEpochParser();
      default:
        return new JodaParser(pattern, locale, tz);
    }
  }

  public static TimestampParser makeParser(String pattern) {
    return makeParser(pattern, (Locale)null, null);
  }

  public static TimestampParser makeParser(String pattern, String locale, String zone) {
    return makeParser(pattern, locale == null ? null : Locale.forLanguageTag(locale), zone);
  }
}
