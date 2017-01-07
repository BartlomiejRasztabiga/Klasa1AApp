package pl.ct8.rasztabiga.models;

import java.util.List;

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
        this.id = id;
    }

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

    public int getId() {
        return id;
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
