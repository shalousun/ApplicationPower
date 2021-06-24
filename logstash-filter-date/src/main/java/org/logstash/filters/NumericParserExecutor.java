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

package org.logstash.filters;

import org.joda.time.Instant;

import org.logstash.filters.parser.TimestampParser;

import java.io.IOException;
import java.math.BigDecimal;

class NumericParserExecutor implements ParserExecutor {
  private TimestampParser parser;
  public NumericParserExecutor(TimestampParser parser) {
    this.parser = parser;
  }

  public Instant execute(Object input) throws IOException {
    if (input instanceof String) {
      return parser.parse((String) input);
    } else if (input instanceof Long) {
      return parser.parse((Long)input);
    } else if (input instanceof Integer) {
      return parser.parse(((Integer) input).longValue());
    } else if (input instanceof Double) {
      return parser.parse((Double) input);
    } else if (input instanceof BigDecimal) {
      return parser.parse((BigDecimal) input);
    } else {
      throw new IllegalArgumentException("Cannot parse date for value of type " + input.getClass().getName());
    }
  }
}
