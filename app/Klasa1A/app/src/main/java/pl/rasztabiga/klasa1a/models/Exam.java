package pl.rasztabiga.klasa1a.models;


import android.graphics.Color;

import com.github.sundeepk.compactcalendarview.domain.Event;

import java.util.Calendar;
import java.util.Date;

public class Exam {
    private int id;
    private String subject;
    private String desc;
    private int year;
    private int month;
    private int day;

    public Exam(int id, String subject, String desc, int year, int month, int day) {
        this.subject = subject;
        this.desc = desc;
        this.year = year;
        this.month = month;
        this.day = day;
        this.day = id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDesc() {
        return desc;
    }

    public int getId() {
        return id;
    }

    public Event createEvent() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();
        return new Event(Color.RED, date.getTime(), this);
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", subject='" + subject + '\'' +
                ", desc='" + desc + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }
}
