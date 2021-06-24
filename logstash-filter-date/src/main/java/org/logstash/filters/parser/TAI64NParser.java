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

import java.math.BigDecimal;
import org.joda.time.Instant;

public class TAI64NParser implements TimestampParser {
  @Override
  public Instant parse(String value) {
    int offset = 0;
    if (value.startsWith("@")) {
      offset = 1;
    }

    // https://cr.yp.to/libtai/tai64.html
    // First 8 bytes (16 hex chars) of TAI64N are seconds. TAI64's unix epoch is at 2^62
    long secondsSinceEpoch = Long.parseLong(value.substring(offset, 16 + offset), 16) & ((1L << 62) - 1);
    // last 4 bytes (8 hex chars) of TAI64N are subsecond value in nanoseconds.
    int nanoseconds = Integer.parseInt(value.substring(16 + offset, 24 + offset), 16);

    // Compensate for leap seconds to convert TAI to UTC.
    // XXX: Leap seconds aren't this simple. We need to find out what times each leap second was introduced.
    secondsSinceEpoch -= 10;

    return new Instant(secondsSinceEpoch * 1000 + (nanoseconds / 1_000_000));
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
    return parse(value);
  }
}
