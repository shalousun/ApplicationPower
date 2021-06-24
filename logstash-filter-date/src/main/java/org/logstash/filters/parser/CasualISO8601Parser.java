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
import org.joda.time.Instant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.util.Arrays;
import java.math.BigDecimal;

/**
 * Created by jls on 11/2/16.
 */
public class CasualISO8601Parser implements TimestampParser {
  private static final DateTimeFormatter[] baseParsers;
  private final DateTimeFormatter[] parsers;

  static {
    baseParsers = new DateTimeFormatter[] {
            ISODateTimeFormat.dateTimeParser(),
            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSSZ"),
            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.SSS"),
            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSSZ"),
            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss,SSS")
    };
  }

  private String timeZone;

  public CasualISO8601Parser(String timeZone) {
    this.timeZone = timeZone;
    if (timeZone == null) {
      parsers = baseParsers;
    } else {
      parsers = Arrays.stream(baseParsers).map(p ->
          p.withZone(DateTimeZone.forID(timeZone))).toArray(DateTimeFormatter[]::new);
    }
  }

  @Override
  public Instant parse(String value) {
    RuntimeException lastException = null;
    for (DateTimeFormatter parser : parsers) {
      try {
        return new Instant(parser.parseMillis(value));
      } catch (IllegalArgumentException e) {
        lastException = e;
      }
    }

    throw lastException;
  }

  @Override
  public Instant parse(Long value) {
    throw new IllegalArgumentException("Expected a string value, but got a long (" + value + "). Cannot parse date.");
  }

  @Override
  public Instant parse(Double value) {
    throw new IllegalArgumentException("Expected a string value, but got a double (" + value + "). Cannot parse date.");
  }

  @Override
  public Instant parse(BigDecimal value) {
    throw new IllegalArgumentException("Expected a string value, but got a bigdecimal (" + value + "). Cannot parse date.");
  }

  @Override
  public Instant parseWithTimeZone(String value, String timezone) {
    DateTimeZone tz = DateTimeZone.forID(timezone);
    RuntimeException lastException = null;
    for (DateTimeFormatter parser : parsers) {
      try {
        DateTimeFormatter parserWithZone = parser.withZone(tz);
        return new Instant(parserWithZone.parseMillis(value));
      } catch (IllegalArgumentException e) {
        lastException = e;
      }
    }

    throw lastException;
  }
}
