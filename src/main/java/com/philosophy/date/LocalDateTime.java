package com.philosophy.date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class LocalDateTime {
    /**
     * <code>LocalDate</code>转换成为<code>Date</code>
     *
     * @param date <code>LocalDate</code>对象
     * @return <code>Date</code>对象
     */
    public Date to(LocalDate date) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = date.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    /**
     * <code>Date</code>转换成为<code>LocalDate</code>
     * @param date <code>Date</code>对象
     * @return <code>LocalDate</code>对象
     */
    public LocalDate to(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    /**
     * 经过的日期
     * @param start 开始日期
     * @param end 结束日期
     * @return 包含开始和结束日期经历的天数
     */
    public List<LocalDate> passedDays(LocalDate start, LocalDate end){
        List<LocalDate> day = new LinkedList<>();
        day.add(start);
        LocalDate temp = start;
        while(!temp.equals(end)){
            temp = temp.plusDays(1);
            day.add(temp);
        }
        return day;
    }

    /**
     * 经过的月份
     * @param start 开始日期
     * @param end 结束日期
     * @return 包含开始和结束日期经历的月数
     */
    public List<LocalDate> passedMonth(LocalDate start, LocalDate end){
        List<LocalDate> month = new LinkedList<>();
        month.add(start);
        LocalDate temp = start;
        while(temp.getMonthValue() != end.getMonthValue()){
            temp = temp.plusMonths(1);
            month.add(temp);
        }
        return month;
    }
    /**
     * 经过的年份
     * @param start 开始日期
     * @param end 结束日期
     * @return 包含开始和结束日期经历的年份
     */
    public List<LocalDate> passedYear(LocalDate start, LocalDate end){
        List<LocalDate> year = new LinkedList<>();
        year.add(start);
        LocalDate temp = start;
        while(temp.getYear() != end.getYear() ){
            temp = temp.plusYears(1);
            year.add(temp);
        }
        return year;
    }
}
