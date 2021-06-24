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
import org.joda.time.Instant;
import org.junit.Test;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.math.BigDecimal;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class UnixEpochParserTest {
  static Random random = new Random();

  // XXX: Make these be theories and rules instead.
  @Parameters
  public static Collection<Object[]> samples() {
    int count = random.nextInt(1000) + 1;

    // Create a random number of values to test
    return LongStream.range(1, count)
            .map(i -> new Long(Math.abs(random.nextLong() % Integer.MAX_VALUE)))
            .mapToObj(i -> new Object[] { new Instant(i), String.format("%d.%03d", i / 1000, i % 1000) })
            .collect(Collectors.toList());
  }

  private Instant expected;
  private String input;

  public UnixEpochParserTest(Instant expected, String input) {
    this.expected = expected;
    this.input = input;
  }

  @Test
  public void parsesEpoch0() {
    Instant actual = new UnixEpochParser().parse(input);
    assertEquals(expected, actual);
  }

  @Test
  public void parsesEpochBigDecimal() {
    Instant actual = new UnixEpochParser().parse(new BigDecimal(input));
    assertEquals(expected, actual);
  }
}

