package common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     * 时间字符串转为Date类型
     * @param dateString
     * @param format
     * @return
     * @throws ParseException
     */
    public static Date parse(String dateString,String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = sdf.parse(dateString);
        return date;
    }

    /**
     * Date类型转为字符串
     * @param date
     * @param format
     * @return
     */
    public static String format(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String datestr = sdf.format(date);
        return datestr;
    }
}
