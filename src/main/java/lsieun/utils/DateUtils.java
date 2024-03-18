package lsieun.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String fromUnixTimestamp(int timestamp) {
        Date time = new java.util.Date((long) timestamp * 1000);
        return df.format(time);
    }
}
