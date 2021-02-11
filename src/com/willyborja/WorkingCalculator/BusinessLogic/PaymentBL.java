package com.willyborja.WorkingCalculator.BusinessLogic;

import com.willyborja.WorkingCalculator.Classes.DayTimePeriod;
import com.willyborja.WorkingCalculator.Classes.Employee;
import com.willyborja.WorkingCalculator.Classes.TimePeriod;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentBL {

    private FileBL fileBL;
    private List<TimePeriod> schedulesList;
    List<Employee> employeeList;

    public PaymentBL(){
        fileBL = new FileBL();
        employeeList = new ArrayList<>();

        loadSchedules();
        List<String> workingRecordList = fileBL.readCommand();
        fileBL.closeFile();
        workingRecordList.forEach(workingRecord ->{
            employeeList.add(splitWorkingRecord(workingRecord));
        });
        printWorkingRecords(employeeList);
    }

    public void loadSchedules() {
        try {
            schedulesList = new ArrayList<>();
            schedulesList.add(new TimePeriod(new SimpleDateFormat("dd/mm/yyyy HH:mm").parse("01/01/1970 00:01"), new SimpleDateFormat("dd/mm/yyyy HH:mm").parse("01/01/1970 09:00")));
            schedulesList.add(new TimePeriod(new SimpleDateFormat("dd/mm/yyyy HH:mm").parse("01/01/1970 09:01"), new SimpleDateFormat("dd/mm/yyyy HH:mm").parse("01/01/1970 18:00")));
            schedulesList.add(new TimePeriod(new SimpleDateFormat("dd/mm/yyyy HH:mm").parse("01/01/1970 18:01"), new SimpleDateFormat("dd/mm/yyyy HH:mm").parse("02/01/1970 00:00")));
        } catch (Exception e) {
            System.err.println("Can't load working schedules: " + e.getMessage());
        }
    }

    public Employee splitWorkingRecord(String workingInfoRecord){
        String [] workingInfoArray= workingInfoRecord.split("=");

        String workerName = workingInfoArray[0];
        String workingRecord = workingInfoArray[1];
        List<DayTimePeriod> listWorkingRecord = new ArrayList<>();

        String pattern = "(^|)(([a-zA-Z]{2})(\\d{2}:\\d{2})-(\\d{2}:\\d{2})+)(,|$)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(workingRecord);

        while (m.find()){
            String day = m.group(3);
            String strStartWorkingPeriod = m.group(4);
            String strEndWorkingPeriod = m.group(5);
            try {
                listWorkingRecord.add(new DayTimePeriod(day, new SimpleDateFormat("HH:mm").parse(strStartWorkingPeriod), new SimpleDateFormat("HH:mm").parse(strEndWorkingPeriod)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return new Employee(workerName, listWorkingRecord, readWorkingRecord(listWorkingRecord));
    }

    public double readWorkingRecord(List<DayTimePeriod> listWorkingRecord) {
        double payment = 0;
        for(DayTimePeriod dayTimePeriod: listWorkingRecord){
            payment = payment + calculatePayment(dayTimePeriod.getDay(), dayTimePeriod.getStartDate(), dayTimePeriod.getEndDate());
        }
        return payment;
    }

    public Long calculatePayment(String day, Date startDate, Date endDate){
        Long workedHours = TimeUnit.MILLISECONDS.toHours(endDate.getTime() - startDate.getTime());
        switch (day){
            case "MO":
            case "TU":
            case "WE":
            case "TH":
            case "FR":
                if((startDate.after(schedulesList.get(0).getStartDate()) && endDate.before(schedulesList.get(0).getEndDate()))
                        || ((startDate.equals(schedulesList.get(0).getStartDate())) && (endDate.equals(schedulesList.get(0).getEndDate())))
                        || ((startDate.after(schedulesList.get(0).getStartDate())) && (endDate.equals(schedulesList.get(0).getEndDate())))
                        || ((startDate.equals(schedulesList.get(0).getStartDate())) && (endDate.before(schedulesList.get(0).getEndDate())))){
                    return workedHours * 25;
                }
                if((startDate.after(schedulesList.get(1).getStartDate()) && endDate.before(schedulesList.get(1).getEndDate()))
                        || ((startDate.equals(schedulesList.get(1).getStartDate())) && (endDate.equals(schedulesList.get(1).getEndDate())))
                        || ((startDate.after(schedulesList.get(1).getStartDate())) && (endDate.equals(schedulesList.get(1).getEndDate())))
                        || ((startDate.equals(schedulesList.get(1).getStartDate())) && (endDate.before(schedulesList.get(1).getEndDate())))){
                    return workedHours * 15;
                }
                if((startDate.after(schedulesList.get(2).getStartDate()) && endDate.before(schedulesList.get(2).getEndDate()))
                        || ((startDate.equals(schedulesList.get(2).getStartDate())) && (endDate.equals(schedulesList.get(2).getEndDate())))
                        || ((startDate.after(schedulesList.get(2).getStartDate())) && (endDate.equals(schedulesList.get(2).getEndDate())))
                        || ((startDate.equals(schedulesList.get(2).getStartDate())) && (endDate.before(schedulesList.get(2).getEndDate())))){
                    return workedHours * 20;
                }
                System.out.println("COMPARISON: "+startDate.equals(schedulesList.get(2).getStartDate()));
                break;

            case "SA":
            case "SU":
                if((startDate.after(schedulesList.get(0).getStartDate()) && endDate.before(schedulesList.get(0).getEndDate()))
                        || ((startDate.equals(schedulesList.get(0).getStartDate())) && (endDate.equals(schedulesList.get(0).getEndDate())))
                        || ((startDate.after(schedulesList.get(0).getStartDate())) && (endDate.equals(schedulesList.get(0).getEndDate())))
                        || ((startDate.equals(schedulesList.get(0).getStartDate())) && (endDate.before(schedulesList.get(0).getEndDate())))){
                    return workedHours * 30;
                }
                if((startDate.after(schedulesList.get(1).getStartDate()) && endDate.before(schedulesList.get(1).getEndDate()))
                        || ((startDate.equals(schedulesList.get(1).getStartDate())) && (endDate.equals(schedulesList.get(1).getEndDate())))
                        || ((startDate.after(schedulesList.get(1).getStartDate())) && (endDate.equals(schedulesList.get(1).getEndDate())))
                        || ((startDate.equals(schedulesList.get(1).getStartDate())) && (endDate.before(schedulesList.get(1).getEndDate())))){
                    return workedHours * 20;
                }
                if((startDate.after(schedulesList.get(2).getStartDate()) && endDate.before(schedulesList.get(2).getEndDate()))
                        || ((startDate.equals(schedulesList.get(2).getStartDate())) && (endDate.equals(schedulesList.get(2).getEndDate())))
                        || ((startDate.after(schedulesList.get(2).getStartDate())) && (endDate.equals(schedulesList.get(2).getEndDate())))
                        || ((startDate.equals(schedulesList.get(2).getStartDate())) && (endDate.before(schedulesList.get(2).getEndDate())))){
                    return workedHours * 25;
                }
                break;

            default:
                return Long.valueOf(0);
        }
        return Long.valueOf(0);
    }

    public void printWorkingRecords(List<Employee> employeeList){
        employeeList.forEach(employee -> {
            System.out.println("The amount to pay to " + employee.getName() + " is " + employee.getPayment() + " USD.");
        });
    }
}
