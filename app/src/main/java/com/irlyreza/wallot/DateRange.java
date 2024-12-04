package com.irlyreza.wallot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateRange {

    public DateRange(){

    }

    public static void main(String[] args) {

    }

    public String Today(){
        Calendar end = Calendar.getInstance();
        int year = end.get(Calendar.YEAR);
        int month = end.get(Calendar.MONTH) + 1;
        int day = end.get(Calendar.DAY_OF_MONTH);
        String today = end.toString();
        return today;
    }


    public static List<String> getDateRange(Date startDate, Date endDate) {

        List<String> dateRange = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);

        while (!calendar.getTime().after(endDate)) {
            Date currentDate = calendar.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateRange.add(dateFormat.format(currentDate));
            calendar.add(Calendar.DATE, 1);
        }

        return dateRange;
    }
    public static String getMonday(int year,int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            if (Calendar.DAY_OF_MONTH == 1){
                calendar.add(Calendar.MONTH,-1);
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    public static String getFirstDay(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        while (calendar.get(Calendar.DAY_OF_MONTH) != 1) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());
    }

    public static String getFirstDayMonth(int year,int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        while (calendar.get(Calendar.MONTH) != Calendar.JANUARY || calendar.get(Calendar.DAY_OF_MONTH) != 1 ) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            if (Calendar.DAY_OF_MONTH == 1){
                calendar.add(Calendar.MONTH,-1);
            }
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(calendar.getTime());

    }

}
