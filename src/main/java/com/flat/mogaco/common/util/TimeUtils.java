package com.flat.mogacko.common.util;

import java.time.Duration;
import java.time.LocalTime;

public class TimeUtils {

    public static LocalTime minusLocalTime(LocalTime localTime, LocalTime target) {
        return localTime.minusHours(target.getHour())
                .minusMinutes(target.getMinute())
                .minusSeconds(target.getSecond());
    }

    public static LocalTime plusLocalTime(LocalTime localTime, LocalTime target) {
        return localTime.minusHours(target.getHour())
                .plusMinutes(target.getMinute())
                .plusSeconds(target.getSecond());
    }

}