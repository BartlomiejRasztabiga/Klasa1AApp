package pl.rasztabiga.klasa1a.models;


public class Exam {
    private String subject;
    private String desc;

    public Exam(String subject, String desc) {
        this.subject = subject;
        this.desc = desc;
    }

    public String getSubject() {
        return subject;
    }

    public String getDesc() {
        return desc;
    }
}
