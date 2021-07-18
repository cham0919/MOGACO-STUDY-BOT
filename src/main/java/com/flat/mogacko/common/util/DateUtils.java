package com.flat.mogacko.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final Logger log = LoggerFactory.getLogger(DateUtils.class);


    public static Date now(){
        return new Date();
    }

    public static String now(String format){
        return format(now(), format);
    }

    public static String format(Date theDate, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(theDate);
    }


}
