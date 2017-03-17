package com.site.utils;

/**
 * Created by wang0 on 2016/9/13.
 */
public class DateUtil {

    public static long toHour(long mill) {
        return mill / (60 * 60 * 1000);
    }

    public static long toSecond(long mill) {
        return mill / 1000;
    }

    public static long tominute(long mill) {
        return mill / (60 * 1000);
    }

    public static long toDay(long mill) {
        return mill / (24 * 60 * 60 * 1000);
    }

    public static long hour(long hour) {
        return (60 * 60 * 1000) * hour;
    }

    public static long second(long second) {
        return 1000 * second;
    }

    public static long minutes(long minutes) {
        return 60 * 1000 * minutes;
    }

    public static long day(long day) {
        return 24 * 60 * 60 * 1000;
    }

    public static StringBuilder formatdate(long mill) {
        StringBuilder string = new StringBuilder("");
        long hour = toHour(mill);
        if (hour > 0) {
            string.append(hour).append("时");
            mill -= hour(hour);
            long minutes = tominute(mill);
            if (minutes > 0) {
                string.append(minutes).append("分");
                mill -= minutes(minutes);
                long seconds = toSecond(mill);
                if (seconds > 0) {
                    string.append(seconds).append("秒");
                }
            } else {
                long seconds = toSecond(mill);
                if (seconds > 0) {
                    string.append(seconds).append("秒");
                }
            }


        } else {
            long minutes = tominute(mill);
            if (minutes > 0) {
                string.append(minutes).append("分");
                mill -= minutes(minutes);
                long seconds = toSecond(mill);
                if (seconds > 0) {
                    string.append(seconds).append("秒");
                }
            } else {
                long seconds = toSecond(mill);
                if (seconds > 0) {
                    string.append(seconds).append("秒");
                }
            }
        }
        return string;
    }


}
