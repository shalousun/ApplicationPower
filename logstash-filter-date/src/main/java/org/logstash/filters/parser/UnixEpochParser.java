/* * Licensed to Elasticsearch under one or more contributor
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

import org.joda.time.Instant;
import java.math.BigDecimal;

public class UnixEpochParser implements TimestampParser {
  private static long MAX_EPOCH_SECONDS = (long)Integer.MAX_VALUE;

  @Override
  public Instant parse(String value) {
    if (value.contains(".")) {
      int dot = value.indexOf(".");
      long seconds = Long.parseLong(value.substring(0, dot));
      long millis = coerceToMillis(seconds);
      // Milliseconds today, so we take at most 3 digits after the dot.
      int subdigits = Math.min(3, (value.length() - dot - 1));
      assert subdigits >= 0 && subdigits <= 3;

      long subseconds = Long.parseLong(value.substring(dot+1, dot+1+subdigits));
      switch (subdigits) {
        case 0:
          return new Instant(millis);
        case 1:
          return new Instant(millis + subseconds * 100);
        case 2:
          return new Instant(millis + subseconds * 10);
        case 3:
        default:
          return new Instant(millis + subseconds);
      }
    } else {
      return new Instant(coerceToMillis(Long.parseLong(value)));
    }
  }

  @Override
  public Instant parseWithTimeZone(String value, String timezone) {
    return parse(value);
  }

  @Override
  public Instant parse(Long value) {
    return new Instant(coerceToMillis(value));
  }

  @Override
  public Instant parse(Double value) {
    if (value.longValue() > MAX_EPOCH_SECONDS) {
      throw new IllegalArgumentException("Cannot parse date for value larger than UNIX epoch maximum seconds");
    }
    return new Instant((long)(value * 1000));
  }

  private long coerceToMillis(long value) {
    if (value > MAX_EPOCH_SECONDS) {
      throw new IllegalArgumentException("Cannot parse date for value larger than UNIX epoch maximum seconds");
    }
    return value * 1000;
  }

  @Override
  public Instant parse(BigDecimal value) {
    if (value.longValue() > MAX_EPOCH_SECONDS) {
      throw new IllegalArgumentException("Cannot parse date for value larger than UNIX epoch maximum seconds");
    }
    return new Instant(value.scaleByPowerOfTen(3).longValue());
  }
}
