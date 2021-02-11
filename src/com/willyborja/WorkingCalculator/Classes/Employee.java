package com.willyborja.WorkingCalculator.Classes;

import java.util.List;

public class Employee {

    private String name;
    private List<DayTimePeriod> workingRecord;
    private double payment;

    public Employee(String name, List<DayTimePeriod> workingRecord, double payment) {
        this.name = name;
        this.workingRecord = workingRecord;
        this.payment = payment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DayTimePeriod> getWorkingRecord() {
        return workingRecord;
    }

    public void setWorkingRecord(List<DayTimePeriod> workingRecord) {
        this.workingRecord = workingRecord;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }
}
