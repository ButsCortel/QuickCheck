package com.buts.research;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Test {
    public static void main(String[] args) {
        DateTimeFormatter format = DateTimeFormatter
                .ofLocalizedTime(FormatStyle.SHORT);
        LocalTime time1 = LocalTime.parse("12:00 AM", format);
        LocalTime time2 = LocalTime.parse("11:59 PM", format);
        //Duration duration = Duration.between(time1, time2);
        if (time1.compareTo(time2) > 0) {
        	System.out.println("late");       	
        }
        else {
        	System.out.println("on time");
        }
    }
}