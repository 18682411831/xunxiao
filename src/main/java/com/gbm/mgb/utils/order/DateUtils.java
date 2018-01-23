package com.gbm.mgb.utils.order;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Waylon on 2017/3/9.
 */
public class DateUtils {

    private static final String ALLDATE_1 = "yyyyMMddHHmmssSSS";
    private static final String ALLDATE_2 = "yyyy-MM-dd HH:mm:ss";
    private static final String ALLDATE_3 = "yyyy-MM-dd";
    public static final String ALL_DATE_4 = "yyyyMMddHHmmssSS";

    public static String getTimeStamp(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(ALLDATE_1);
        if (date != null) {
            return sdf.format(date);
        }
        return null;
    }
 
    public static Date addOrMinusYear(long ti, int i) throws Exception {
        Date rtn = null;
        GregorianCalendar cal = new GregorianCalendar();
        Date date = new Date(ti);
        cal.setTime(date);
        cal.add(13, i);
        rtn = cal.getTime();
        return rtn;
    }

    public static Date strToDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat(ALLDATE_2);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(ALLDATE_2);
        return sdf.format(date);
    }

    public static String date2Str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(ALLDATE_3);
        return sdf.format(date);
    }

    /**
     * 将日期格式是2017-04-06 10:00:00.0 转为10
     */
    public static String date2Num(String date) {

        if (date.length() == 21 ){
            date = date.substring(11,16);
            return date;
        }
        return date;
    }

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }

    /**
	 * 获取格式化后的字符日期
	 *
	 * @param format
	 * @return
	 */
	public static String getCurrDate(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

    /**
	 * 将字符串转换成一个日期(yyyyMMddHHmmssSS)
	 *
	 * @param date
	 * @return 返回格式化后的日期
	 */
	public static String strToDate4(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(ALL_DATE_4);
		return sdf.format(date);
	}

}
