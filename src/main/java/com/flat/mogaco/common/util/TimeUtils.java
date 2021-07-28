package com.flat.mogaco.common.util;

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

    public static String LocalTimeToString(LocalTime localTime){
        return localTime.getHour() + "시간 " + localTime.getMinute() + "분 " + localTime.getSecond() + "초";
    }

}
