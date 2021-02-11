package test;

import com.willyborja.WorkingCalculator.BusinessLogic.PaymentBL;
import com.willyborja.WorkingCalculator.Classes.DayTimePeriod;
import com.willyborja.WorkingCalculator.Classes.Employee;
import com.willyborja.WorkingCalculator.Classes.TimePeriod;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PaymentBLTest extends Object {

    PaymentBL paymentBL;

    @Before
    public void init(){
        paymentBL = new PaymentBL();
    }

    @Test
    public void splitWorkingRecord() {
        try {
            String testString = "RENE=MO10:00-12:00,TU10:00-12:00,TH01:00-03:00,SA14:00-18:00,SU20:00-21:00";
            List<DayTimePeriod> workingRecord = new ArrayList<>();
            workingRecord.add(new DayTimePeriod("MO", new SimpleDateFormat("HH:mm").parse("10:00"), new SimpleDateFormat("HH:mm").parse("12:00")));
            workingRecord.add(new DayTimePeriod("TU", new SimpleDateFormat("HH:mm").parse("10:00"), new SimpleDateFormat("HH:mm").parse("12:00")));
            workingRecord.add(new DayTimePeriod("TH", new SimpleDateFormat("HH:mm").parse("01:00"), new SimpleDateFormat("HH:mm").parse("03:00")));
            workingRecord.add(new DayTimePeriod("MO", new SimpleDateFormat("HH:mm").parse("14:00"), new SimpleDateFormat("HH:mm").parse("18:00")));
            workingRecord.add(new DayTimePeriod("MO", new SimpleDateFormat("HH:mm").parse("20:00"), new SimpleDateFormat("HH:mm").parse("21:00")));

            Employee employee = new Employee("RENE", workingRecord , 215);
            Employee employeeTested = paymentBL.splitWorkingRecord(testString);
            assertEquals(employeeTested.getPayment(), employee.getPayment(), 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            String testString = "EDUARDO=TU13:00-14:00,WE10:00-11:00,FR20:00-23:00,SA14:00-16:00";
            List<DayTimePeriod> workingRecord = new ArrayList<>();
            workingRecord.add(new DayTimePeriod("TU", new SimpleDateFormat("HH:mm").parse("13:00"), new SimpleDateFormat("HH:mm").parse("14:00")));
            workingRecord.add(new DayTimePeriod("WE", new SimpleDateFormat("HH:mm").parse("10:00"), new SimpleDateFormat("HH:mm").parse("11:00")));
            workingRecord.add(new DayTimePeriod("FR", new SimpleDateFormat("HH:mm").parse("20:00"), new SimpleDateFormat("HH:mm").parse("23:00")));
            workingRecord.add(new DayTimePeriod("SA", new SimpleDateFormat("HH:mm").parse("14:00"), new SimpleDateFormat("HH:mm").parse("16:00")));

            Employee employee = new Employee("EDUARDO", workingRecord , 215);
            Employee employeeTested = paymentBL.splitWorkingRecord(testString);
            assertEquals(employeeTested.getPayment(), employee.getPayment(), 1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}