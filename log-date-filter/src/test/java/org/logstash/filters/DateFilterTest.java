package org.logstash.filters;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Instant;
import org.junit.Assert;
import org.junit.Test;
import org.logstash.filters.parser.JodaParser;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateFilterTest {

    public static final String STANDARD_DATE_FORMAT_UTC = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private List<String> failtagList = Collections.singletonList("_date_parse_fail");
    private String tz = "UTC";
    private String loc = "Asia/Shanghai";

    @Test
    public void testIsoStrings() throws Exception {
        Map<String, String> testElements = new HashMap<String, String>() {{
            put("2010-05-03T08:18:18+00:00", "2010-05-03T08:18:18.000Z");
            put("2001-09-05T16:36:36+0000", "2001-09-05T16:36:36.000Z");
            put("2001-12-07T23:54:54Z", "2001-12-07T23:54:54.000Z");
            put("2001-12-07T23:54:54.000Z", "2001-12-07T23:54:54.000Z");
        }};
        DateFilter subject = new DateFilter(failtagList);
        subject.acceptFilterConfig("ISO8601", loc, tz);
        for (Map.Entry<String, String> entry : testElements.entrySet()) {
            applyString(subject, entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void testPatternStringsInterpolateTzNoYear() throws Exception {
        Map<String, String> testElements = new HashMap<String, String>() {{
            put("Mar 27 01:59:59.999", "2016-03-27T00:59:59.999Z");
//            put("Mar 27 02:00:01.000", "2016-03-27T01:00:01.000Z"); // this should and does fail, the time does not exist
            put("Mar 27 03:00:01.000", "2016-03-27T01:00:01.000Z"); // after CET to CEST change at 02:00
        }};
        TestClock clk = new TestClock(new DateTime(2016, 03, 29, 23, 59, 50, DateTimeZone.UTC));
        JodaParser.setDefaultClock(clk);
        DateFilter subject = new DateFilter(failtagList);
        subject.acceptFilterConfig("MMM dd hh:mm:ss.SSS", loc, "%{mytz}");
        for (Map.Entry<String, String> entry : testElements.entrySet()) {
            applyStringTz(subject, entry.getKey(), entry.getValue(), "CET");
        }
    }

    @Test
    public void testIsoStringsInterpolateTz() throws Exception {
        Map<String, String> testElements = new HashMap<String, String>() {{
            put("2001-01-01T00:00:00", "2001-01-01T04:00:00.000Z");
            put("1974-03-02T04:09:09", "1974-03-02T08:09:09.000Z");
            put("2006-01-01T00:00:00", "2006-01-01T04:00:00.000Z");
            // Venezuela changed from -4:00 to -4:30 in late 2007
            put("2008-01-01T00:00:00", "2008-01-01T04:30:00.000Z");
            // Venezuela changed from -4:30 to -4:00 on Sunday, 1 May 2016
            put("2016-05-01T08:18:18.123", "2016-05-01T12:18:18.123Z");
        }};
        DateFilter subject = new DateFilter(failtagList);
        subject.acceptFilterConfig("ISO8601", loc, "%{mytz}");
        for (Map.Entry<String, String> entry : testElements.entrySet()) {
            applyStringTz(subject, entry.getKey(), entry.getValue(), "America/Caracas");
        }
    }

    @Test
    public void testTai64Strings() throws Exception {
        Map<String, String> testElements = new HashMap<String, String>() {{
            put("4000000050d506482dbdf024", "2012-12-22T01:00:46.767Z");
            put("@4000000050d506482dbdf024", "2012-12-22T01:00:46.767Z");

        }};
        DateFilter subject = new DateFilter(failtagList);
        subject.acceptFilterConfig("TAI64N", loc, tz);
        for (Map.Entry<String, String> entry : testElements.entrySet()) {
            applyString(subject, entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void testUnixStrings() throws Exception {
        Map<String, String> testElements = new HashMap<String, String>() {{
            put("0", "1970-01-01T00:00:00.000Z");
            put("1000000000", "2001-09-09T01:46:40.000Z");
            put("1478207457", "2016-11-03T21:10:57.000Z");
        }};
        DateFilter subject = new DateFilter(failtagList);
        subject.acceptFilterConfig("UNIX", loc, tz);
        for (Map.Entry<String, String> entry : testElements.entrySet()) {
            applyString(subject, entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void testUnixInts() throws Exception {
        Map<Integer, String> testElements = new HashMap<Integer, String>() {{
            put(0, "1970-01-01T00:00:00.000Z");
            put(1000000000, "2001-09-09T01:46:40.000Z");
            put(1478207457, "2016-11-03T21:10:57.000Z");
            put(456, "1970-01-01T00:07:36.000Z");
        }};
        DateFilter subject = new DateFilter(failtagList);
        subject.acceptFilterConfig("UNIX", loc, tz);
        for (Map.Entry<Integer, String> entry : testElements.entrySet()) {
            applyInt(subject, entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void testUnixLongs() throws Exception {
        Map<Long, String> testElements = new HashMap<Long, String>() {{
            put(0L, "1970-01-01T00:00:00.000Z");
            put(1000000000L, "2001-09-09T01:46:40.000Z");
            put(1478207457L, "2016-11-03T21:10:57.000Z");
            put(456L, "1970-01-01T00:07:36.000Z");
        }};
        DateFilter subject = new DateFilter(failtagList);
        subject.acceptFilterConfig("UNIX", loc, tz);
        for (Map.Entry<Long, String> entry : testElements.entrySet()) {
            applyLong(subject, entry.getKey(), entry.getValue());
        }
    }

    @Test
    public void testUnixMillisLong() throws Exception {
        DateFilter subject = new DateFilter(failtagList);
        subject.acceptFilterConfig("UNIX", loc, tz);
        subject.acceptFilterConfig("UNIX_MS", loc, tz);
        applyLong(subject, 1000000000123L, "2001-09-09T01:46:40.123Z");
    }

    @Test
    public void testUnixDouble() throws Exception {
        DateFilter subject = new DateFilter(failtagList);
        subject.acceptFilterConfig("UNIX", loc, tz);
        applyDouble(subject, 1478207457.456D, "2016-11-03T21:10:57.456Z");
    }

    private void applyString(DateFilter subject, String supplied, String expected) {
        Instant instant = subject.executeParsers(supplied);

        DateTime dateTime = new DateTime(instant);
        System.out.println(dateTime.toString(STANDARD_DATE_FORMAT_UTC));
        System.out.println("=======================================");
        commonAssertions(dateTime.toString(STANDARD_DATE_FORMAT_UTC), expected);
    }

    private void applyStringTz(DateFilter subject, String supplied, String expected, String tz) {
        Instant instant = subject.executeParsers(supplied);
        DateTime dateTime = new DateTime(instant);

        System.out.println(dateTime.toString(STANDARD_DATE_FORMAT_UTC));
        commonAssertions(dateTime.toString(STANDARD_DATE_FORMAT_UTC), expected);
    }

    private void applyInt(DateFilter subject, Integer supplied, String expected) {
        Instant instant = subject.executeParsers(supplied);
        DateTime dateTime = new DateTime(instant);

        commonAssertions(dateTime.toString(STANDARD_DATE_FORMAT_UTC), expected);
    }

    private void applyLong(DateFilter subject, Long supplied, String expected) {
        Instant instant = subject.executeParsers(supplied);
        DateTime dateTime = new DateTime(instant);

        commonAssertions(dateTime.toString(STANDARD_DATE_FORMAT_UTC), expected);
    }

    private void applyDouble(DateFilter subject, Double supplied, String expected) {
        Instant instant = subject.executeParsers(supplied);
        DateTime dateTime = new DateTime(instant);
        commonAssertions(dateTime.toString(STANDARD_DATE_FORMAT_UTC), expected);
    }

    private void commonAssertions(String supplied, String expected) {
        Assert.assertEquals(supplied, expected);
        Assert.assertTrue(String.format("Unequal - expected: %s, actual: %s", expected, supplied), expected.equals(supplied));
    }

    class TestClock implements JodaParser.Clock {
        private DateTime datetime;

        public TestClock(DateTime datetime) {
            this.datetime = datetime;
        }

        @Override
        public DateTime read() {
            return datetime;
        }
    }
}
