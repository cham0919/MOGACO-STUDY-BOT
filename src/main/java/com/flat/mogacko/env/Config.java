package com.flat.mogacko.env;

import java.util.Properties;

public class Config {

    public static Properties getAllProperties() {
        return getEnvironment().getAllProperties();
    }

    public static Environment getEnvironment() {
        return PropertiesEnvironment.getInstance();
    }

    public static String getProperty(String key) {
        return getAllProperties().getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return getAllProperties().getProperty(key, defaultValue);
    }

    public static int getInt(String key) {
        String value = getAllProperties().getProperty(key);
        Integer intValue = Integer.valueOf(value);
        return intValue;
    }

    public static int getInt(String key, int defaultValue) {
        try {
            return getInt(key);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Boolean getBoolean(String key) { return getBoolean(key, false); }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = getAllProperties().getProperty(key);
        return value == null ? defaultValue : Boolean.valueOf(value);
    }

    public static byte getByte(String key) {
        String value = getAllProperties().getProperty(key);
        byte byteValue = Byte.valueOf(value);
        return byteValue;
    }

    public static byte getByte(String key, byte defaultValue) {
        try {
            return getByte(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static short getShort(String key) { return Short.valueOf(getAllProperties().getProperty(key)); }

    public static short getShort(String key, short defaultValue) {
        try {
            return getShort(key);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static long getLong(String key) {
        String value = getAllProperties().getProperty(key);
        long longValue = Long.valueOf(value);
        return longValue;
    }

    public static long getLong(String key, long defaultValue) {
        try {
            return getLong(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static float getFloat(String key) {
        String value = getAllProperties().getProperty(key);
        float floatValue = Float.valueOf(value);
        return floatValue;
    }

    public static float getFloat(String key, float defaultValue) {
        try {
            return getFloat(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static double getDouble(String key) {
        String value = getAllProperties().getProperty(key);
        double doubleValue = Double.valueOf(value);
        return doubleValue;
    }

    public static double getDouble(String key, double defaultValue) {
        try {
            return getDouble(key);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static void setProperty(String key, String value) {
        getAllProperties().setProperty(key, value);
    }
}
