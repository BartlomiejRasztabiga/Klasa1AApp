package pl.rasztabiga.klasa1a.models;


import android.graphics.Color;
import android.graphics.Interpolator;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Calendar;
import java.util.Date;

public class Exam {
    private String subject;
    private String desc;
    private int year;
    private int month;
    private int day;

    public Exam(String subject, String desc, int year, int month, int day) {
        this.subject = subject;
        this.desc = desc;
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public String getSubject() {
        return subject;
    }

    public String getDesc() {
        return desc;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public Event createEvent() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();
        return new Event(Color.GREEN, date.getTime(), this);
    }
}
