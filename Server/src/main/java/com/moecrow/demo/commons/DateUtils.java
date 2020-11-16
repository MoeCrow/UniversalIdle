package com.moecrow.demo.commons;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author willz
 * @date 2020.11.16
 */
public class DateUtils {
    public static int getHoursLeftOfTime(Date date) {
        Date nextDay = getDaysAgoByTime(date, -1);
        nextDay = getStartTime(nextDay);

        long diff = nextDay.getTime() - date.getTime();

        return (int) TimeUnit.MILLISECONDS.toHours(diff);
    }

    /**
     * 获取数天前的开始时间
     * @param day
     * @return
     */
    public static Date getDaysAgo(int day) {
        return getStartTime(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(day)));
    }

    public static Date getDaysAgoByTime(Date date, int day) {
        return new Date(date.getTime() - TimeUnit.DAYS.toMillis(day));
    }

    /**
     * 获取数个月前
     *
     * @param month
     * @return
     */
    public static Date getMonthsAgo(int month) {
        Calendar calender = Calendar.getInstance();
        Date date = new Date();
        calender.setTime(date);
        calender.add(Calendar.MONTH, -6);
        return calender.getTime();
    }

    /**
     * 获取今天开始时间
     */
    public static Date getStartTime(Date date) {
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取今天结束时间
     * */
    public static Date getEndTime(Date date) {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(date);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 获取毫秒时间差
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getMillsDuration(Date startTime, Date endTime) {
        return endTime.getTime() - startTime.getTime();
    }

    /**
     * 获取四舍五入的秒时间差
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getSecondsDuration(Date startTime, Date endTime) {
        return Math.round(0.001f * getMillsDuration(startTime, endTime));
    }

    /**
     * 获取时间差
     * @param startTime
     * @param endTime
     * @return
     */
    public static String getDuration(Date startTime, Date endTime){
        String duration=null;
        try {
            long diff = endTime.getTime() - startTime.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
            long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
            long second = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60)-minutes*(1000*60))/(1000);
            if(days>0){
                duration = ""+days+"天"+hours+"小时"+minutes+"分"+second+"秒";
            }
            if(days<=0){
                duration = ""+hours+"小时"+minutes+"分"+second+"秒";
            }
            if(days<=0&&hours<=0){
                duration = ""+minutes+"分"+second+"秒";
            }
            if(days<=0&&hours<=0&&minutes<=0){
                if (second <= 10) {
                    duration = ""+diff+"毫秒";
                } else {
                    duration = ""+second+"秒";
                }
            }
            return duration;
        }catch (Exception e) {
            return duration;
        }

    }


    public static Date getYmd(Date date) {
        Calendar Ymd = Calendar.getInstance();
        Ymd.setTime(date);
        Ymd.set(Calendar.HOUR_OF_DAY, 0);
        Ymd.set(Calendar.MINUTE, 0);
        Ymd.set(Calendar.SECOND, 0);
        Ymd.set(Calendar.MILLISECOND, 0);
        return Ymd.getTime();
    }

    public static int getDayDiffer(Date startDate, Date endDate) {
        //判断是否跨年
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        String startYear = yearFormat.format(startDate);
        String endYear = yearFormat.format(endDate);
        if (startYear.equals(endYear)) {
            /*  使用Calendar跨年的情况会出现问题    */
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            int startDay = calendar.get(Calendar.DAY_OF_YEAR);
            calendar.setTime(endDate);
            int endDay = calendar.get(Calendar.DAY_OF_YEAR);
            return endDay - startDay;
        } else {
            /*  跨年不会出现问题，需要注意不满24小时情况（2016-03-18 11:59:59 和 2016-03-19 00:00:01的话差值为 0）  */
            //  只格式化日期，消除不满24小时影响
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            long startDateTime = 0;
            try {
                startDateTime = dateFormat.parse(dateFormat.format(startDate)).getTime();
                long endDateTime = dateFormat.parse(dateFormat.format(endDate)).getTime();
                return (int) ((endDateTime - startDateTime) / (1000 * 3600 * 24));
            } catch (ParseException e) {
                e.printStackTrace();
                return 0;
            }
        }
    }

}