package com.flat.mogaco.common.util;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtils {

    public static LocalTime minusLocalTime(LocalTime localTime, LocalTime target) {
        return localTime.minusHours(target.getHour())
                .minusMinutes(target.getMinute())
                .minusSeconds(target.getSecond());
    }

    public static LocalTime plusLocalTime(LocalTime localTime, LocalTime target) {
        return localTime.plusHours(target.getHour())
                .plusMinutes(target.getMinute())
                .plusSeconds(target.getSecond());
    }

    public static Duration plusTime(Duration duration, LocalTime target) {
        return duration.plusHours(target.getHour())
                .plusMinutes(target.getMinute())
                .plusSeconds(target.getSecond());
    }

    public static String localTimeToString(LocalTime localTime){
        return localTime.getHour() + "시간 " + localTime.getMinute() + "분 " + localTime.getSecond() + "초";
    }

    public static String durationToString(Duration duration){
        return DurationFormatUtils.formatDuration(duration.toMillis(), "H시간 mm분 ss초", true);
    }

}
