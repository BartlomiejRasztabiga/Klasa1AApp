package pl.ct8.rasztabiga.models;

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

    public Exam(String subject, String desc, int year, int month, int day) {
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

}
