# DateTimeUtil方法介绍
DateTimeUtil是对时间转换的操作统一封装。所属的包为com.power.common.util。

DateTimeUtil也定义一些常用的时间格式表达式，如下：

```
private final static String[] WEEK_ARR = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

public static final String DATE_FORMAT_MINITE = "yyyy-MM-dd HH:mm";

public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";

public static final String DATE_FORMAT_SECOND = "yyyy-MM-dd HH:mm:ss";

public static final String DATE_FORMAT_CHINESE = "yyyy年MM月dd日";

public static final String DATE_FORMAT_CHINESE_SECONDE = "yyyy年MM月dd日 HH:mm:ss";

public static final String DATE_FORMAT_CHINESE_WEEK_SECONDE =  "yyyy年MM月dd日 E HH:mm:ss";
```

## 1. dateToStr(Date date, String format)方法
该方法用于将Date转化成String格式的字符串时间

Usage:

```
DateTimeUtil.dateToStr(new Date(),"yyyy-MM-dd");//return 2017-07-22

```
## 2. strToDate(String sDate, String format)方法
该方法用于将字符串时间转化成Date

Usage:

```
DateTimeUtil.strToDate("2017-07-22","yyyy-MM-dd");
```
## 3. nowTimeStamp()方法
该方法用于获取当前时间戳,返回Timestamp

Usage:

```
DateTimeUtil.nowTimeStamp();
```

## 4. dateToStr(Date date)方法
该方法用于将Date转化成字符串。

Usage:

```
DateTimeUtil.dateToStr(new Date());//return 2017-07-22
```

## 5. strToStamp(String date)方法
该方法用于将Timestamp转化成字符串,该方法格式使用的默认的格式为：yyyy-MM-dd

Usage:

```
DateTimeUtil.strToStamp("2017-07-22");//return 2017-07-22 00:00:00.0
```
## 6. timestampToString(Timestamp time, String fmt)方法
该方法用于将Timestamp转化成String格式的时间

Usage:

```
DateTimeUtil.timestampToString(new Timestamp(System.currentTimeMillis()),"yyyy-MM-dd");//return 2017-07-22
```
## 7. setTimeToNextDay0H0M0S(Timestamp time)方法
该方法用于将传入时间戳重置到它的下一天的0时0分0秒，使用场景为需要精确根据时间范围来查找数据，在高并发昼夜高频使用的系统，如果将时间重置到23:59:59来查询并不是可行方法，每一毫秒都可能有数据写入。该方法重置时间返回long型的时间戳

Usage:

```
DateTimeUtil.setTimeToNextDay0H0M0S(new Timestamp(System.currentTimeMillis()));//1500652800000
```
## 8. setTimeToNextDay0H0M0S(long millis)方法
该方法用于将传入long时间戳重置到它的下一天的0时0分0秒,该方法重置时间后返回long型的时间戳

Usage:

```
DateTimeUtil.setTimeToNextDay0H0M0S(1500652800000L);
```
## 9. setToNextDay0H0M0SExceptToday(long millis)方法
该方法用于将传入long时间戳重置到它的下一天的0时0分0秒,当传入的时间今天则不进行重置。该方法重置时间后返回long型的时间戳

Usage:

```
DateTimeUtil.setToNextDay0H0M0SExceptToday(1500652800000L);
```
## 10. setToNextDay0H0M0SExceptToday(Timestamp stamp)方法
该方法用于将传入时间戳重置到它的下一天的0时0分0秒,当传入的时间今天则不进行重置。该方法重置时间后返回long型的时间戳

Usage:

```
DateTimeUtil.setToNextDay0H0M0SExceptToday(new Timestamp(1500652800000L));
```
## 11. setTimeTo0H0M0S(Timestamp time)方法
该方法将时间戳重置到0时0分0秒，该方法重置时间后返回long型的时间戳

Usage:
```
DateTimeUtil.setTimeTo0H0M0S(new Timestamp(1500652800000L));
```
## 12. setTimeTo0H0M0S(long millis)方法
该方法将long型的时间戳重置到0时0分0秒，该方法重置时间后返回long型的时间戳

Usage:
```
DateTimeUtil.setTimeTo0H0M0S(1500652800000L);
```
## 13. setTimeToLastDay0H0M0S(Timestamp time)方法
该方法用于将时间戳重置到它的上一天0时0分0秒，该方法重置时间后返回long型的时间戳

Usage:
```
DateTimeUtil.setTimeToLastDay0H0M0S(new Timestamp(1500652800000L));
```

## 14. isDifferentDay(Calendar calendar1, Calendar calendar2)方法
该方法用于判断传入的两个Calendar是否是同一天，返回true或者false
Usage:

```
DateTimeUtil.isDifferentDay(Calendar.getInstance(),Calendar.getInstance()));
```
## 15. isDifferentDay(Timestamp timestamp0, Timestamp timestamp1)方法
该方法用于判断传入的两个时间戳是否是同一天，返回true或者false

Usage:

```
DateTimeUtil.isDifferentDay(new Timestamp(1500652800000L),new Timestamp(1500652800000L));
```
## 16. isDifferentDay(long millis0, long millis1)方法
该方法用于判断两个传入的long时间戳是否相同

Usage:
```
DateTimeUtil.isDifferentDay(1500652800000L,1500652800000L);
```
## 17. long2Str(Long millSec)方法
该方法是将long型时间戳转化成字符串时间，默认使用格式化yyyy年MM月dd日
Usage:

```
DateTimeUtil.long2Str(1500652800000L);//return 2017年07月22日
```
## 18. long2Str(long millSec, String format)方法
该方法用于将long型时间格式化成制定的字符串时间格式

Usage:
```
DateTimeUtil.long2Str(1500652800000L,"yyyy-MM-dd");//return 2017-07-22
```
## 19. strToLong(String dateFormat, String strDate)方法
该方法用于字符串格式的时间转化成long型的时间戳

Usage:

```
DateTimeUtil.strToLong("yyyy-MM-dd","2017-07-22");//return 1500652800000
```

## 20. getCurrentMonthDays()方法
该方法用于获取本月的天数,返回int类型

Usage:

```
DateTimeUtil.getCurrentMonthDays();//return 31
```
## 21. getCurrentMonthDays(Timestamp stamp)方法
该方法用于获取传入时间所在月份的天数，返回int类型

Usage:

```
DateTimeUtil.getCurrentMonthDays(new Timestamp(1500652800000L));//return 31
```
## 22. getFirstDayOfCurrentWeek(Timestamp stamp)方法
根据传入时间获取所属周的第一天(0HOMOS) 根据中国习惯将星期一当做第一天,
返回long型时间戳

Usage:

```
DateTimeUtil.getFirstDayOfCurrentWeek(new Timestamp(1500652800000L)); //return 1500220800000
```
## 23. getFirstDayOfCurrentWeek(long ms)方法
根据传入时间获取所属周的第一天(0HOMOS) 根据中国习惯将星期一当做第一天,
返回long型时间戳。

Usage:

```
DateTimeUtil.getFirstDayOfCurrentWeek(1500652800000L);//return 1500220800000
```

## 24. setToFirstDayOfCurrentYear(long millis)方法
将时间设置为当年第一天，并且将时分秒全部置0

Usage:

```
DateTimeUtil.setToFirstDayOfCurrentYear(1500652800000L);//return 1483200000000
```
## 25. setToFirstDayOfNextYear(long millis)方法
将时间设置为下一年的第一天，返回long型时间戳

Usage:

```
DateTimeUtil.setToFirstDayOfNextYear(1500652800000L);//return 1514736000000
```
## 26. setToFirstDayOfLastMonth(long ms)方法
将时间重置到上月的第一天，返回long型时间戳

Usage:

```
DateTimeUtil.setToFirstDayOfLastMonth(1500652800000L);
```
## 27. setToLastMonthCommonDay(long ms)方法

根据long型时间戳上月同期的时间戳,返回long型时间戳,如果传入的时间是月末，处理后仍然是月末，
例如：5月31号，上月同期是4月30号。

Usage:

```
DateTimeUtil.setToLastMonthCommonDay(1500652800000L);
```
## 28. setToFirstDayOfCurrentMonth(long millis)方法
将时间重置为传入时间当前月的第一天，并且将时分秒全置0,返回long型时间戳

Usage:

```
DateTimeUtil.setToFirstDayOfCurrentMonth(1500652800000L);
```
## 29. setToFirstDayOfNextMonth(long millis)方法
将时间重置为传入时间下月的第一天，并将时分秒全置0,返回long型时间戳

Usage:
```
DateTimeUtil.setToFirstDayOfNextMonth(1500652800000L)
```
## 30. setToNextYearCommonDay(long millis)方法
根据时间获取下一年的同一天,返回long型时间戳

Usage:

```
DateTimeUtil.setToNextYearCommonDay(1500652800000L);
```
## 31. setToLastYearCommonDay(long millis)方法
根据传入的时间戳获取去年同期的时间戳,返回long型时间戳

Usage:
```
DateTimeUtil.setToLastYearCommonDay(1500652800000L);
```

## 32. getLastDayOfCurrentWeek(Timestamp stamp)方法
根据时间获取所属周的最后一天,星期天(中国习惯)，返回long型时间戳

Usage:

```
DateTimeUtil.getLastDayOfCurrentWeek(new Timestamp(1500652800000L));
```
## 33. getFirstDayOfCurrentQuarter(long ms)方法
根据时间的得到所对应季度的第一天(0H0M0S)，返回long型时间戳

Usage:

```
DateTimeUtil.getFirstDayOfCurrentQuarter(1500652800000L);
```
## 34. getFirstDayOfNextQuarter(long ms)方法
根据时间获取下一个季度的第一天(0H0M0S)，返回long型时间戳

Usage:

```
DateTimeUtil.getFirstDayOfNextQuarter(1500652800000L);
```
## 35. getDayOfWeek(long ms)方法
根据时间获取是周几(中国化)

Usage:

```
DateTimeUtil.getDayOfWeek(1500652800000L);//return 6
```
## 36. isToday(long ms)方法
判断是否是今天

Usage:

```
DateTimeUtil.isToday(1500652800000L);
```
## 37. friendlyTime(long ms)方法
社交化友好时间显示

Usage:

```
DateTimeUtil.friendlyTime(1500652800000L));//return 昨天
```
## 38. getAge(long ms)方法
根据时间戳计算出年龄
Usage:

```
DateTimeUtil.getAge(1500652800000L));
```
## 39. getLastYearCommonDay(String strDate,String format)方法
去年同期

Usage:

```
DateTimeUtil.getLastYearCommonDay("2017-07-22","yyyy-MM-dd");
```
## 40. getLastMonthCommonDay(String strDate,String format)方法
上月同期，如果传入时间是所属月的最后一天，则上月同期也是最后一天，例如9月30日，则上月同期是8月31日

Usage:
```
DateTimeUtil.getLastMonthCommonDay("2017-07-22","yyyy-MM-dd");
```
## 41. getBirthdayFormIdCard(String idCard)方法
该方法用于从身份证中获取生日，改方法返回时间戳Timestamp

Usage:
```
DateTimeUtil.getBirthdayFormIdCard("420621198312147749");
```

## 42. isCurrentYear(long ms)方法
该方法用于判断输入的时间是否是今年，是则反回true，否则返回false

Usage:
```
DateTimeUtil.isCurrentYear(1483200000000L);//return true
```
## 43. getNowTime()方法
该方法返回当前long类型的毫秒时间戳

Usage:

```
DateTimeUtil.getNowTime();//return 1528535247089
```