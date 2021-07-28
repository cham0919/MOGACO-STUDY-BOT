package com.flat.mogaco.env;

import java.util.Properties;

public class Config {

    private static Properties props = new Properties();


    public static String getProperty(String key) {
        return props.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public static void setProperty(String key, String value) {
        props.setProperty(key, value);
    }
}
