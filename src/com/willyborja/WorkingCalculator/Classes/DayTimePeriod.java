package com.willyborja.WorkingCalculator.Classes;

import java.util.Date;

public class DayTimePeriod extends TimePeriod{

    private String day;

    public DayTimePeriod(String day, Date startDate, Date endDate) {
        super(startDate, endDate);
        this.day = day;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
