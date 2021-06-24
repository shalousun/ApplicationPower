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
import org.logstash.filters.parser.CasualISO8601Parser;
import org.logstash.filters.parser.JodaParser;
import org.logstash.filters.parser.TimestampParser;
import org.logstash.filters.parser.TimestampParserFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DateFilter {

    private static Logger logger = LoggerFactory.getLogger(DateFilter.class);
    private final String[] tagOnFailure;
    private final List<ParserExecutor> executors = new ArrayList<>();
    private final ResultSetter setter;


    public DateFilter(List<String> tagOnFailure) {
        this.tagOnFailure = tagOnFailure.toArray(new String[0]);
        this.setter = new TimestampSetter();

    }

    public void acceptFilterConfig(String format, String locale, String timezone) {
        TimestampParser parser = TimestampParserFactory.makeParser(format, locale, timezone);
        logger.debug("Date filter with format={}, locale={}, timezone={} built as {}", format, locale, timezone, parser.getClass().getName());
        if (parser instanceof JodaParser || parser instanceof CasualISO8601Parser) {
            executors.add(new TextParserExecutor(parser, timezone));
        } else {
            executors.add(new NumericParserExecutor(parser));
        }
    }

    public Instant executeParsers(Object input) {
        if (input == null) {
            return null;
        }
        for (ParserExecutor executor : executors) {
            try {
                Instant instant = executor.execute(input);
                return instant;
            } catch (IllegalArgumentException | IOException e) {
                // do nothing, try next ParserExecutor
            }
        }
        return null;
    }
}
