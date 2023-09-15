package org.search_engine.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * UTILITY FOR DATEs
 * */
public class DateUtils {

    public static String formatDate(Date date){
        String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(date);
    }
}
