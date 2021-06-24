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

public class UnixMillisEpochParser implements TimestampParser {
  private static long MAX_EPOCH_MILLISECONDS = (long)Integer.MAX_VALUE * 1000;

  @Override
  public Instant parse(String value) {
    return parse(Long.parseLong(value));
  }

  @Override
  public Instant parse(Long value) {
    return new Instant(value);
  }

  @Override
  public Instant parse(Double value) {
    // XXX: Should we accept a double?
    return parse(value.longValue());
  }

  @Override
  public Instant parseWithTimeZone(String value, String timezone) {
    return parse(value);
  }

  @Override
  public Instant parse(BigDecimal value) {
    long lv = value.longValue();
    if (lv > MAX_EPOCH_MILLISECONDS) {
      throw new IllegalArgumentException("Cannot parse date for value larger than UNIX epoch maximum seconds");
    }
    return new Instant(lv);
  }
}
