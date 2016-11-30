package pl.rasztabiga.klasa1a.models;

public class Student {

    private String name;
    private String surname;
    private Integer number;

    public Student(String name, String surname, int number) {
        this.name = name;
        this.surname = surname;
        this.number = number;
    }

    public String getName() {
        return name;
    }

// --Commented out by Inspection START (29.11.2016 20:03):
//    public void setName(String name) {
//        this.name = name;
//    }
// --Commented out by Inspection STOP (29.11.2016 20:03)

    public String getSurname() {
        return surname;
    }

// --Commented out by Inspection START (29.11.2016 20:03):
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }
// --Commented out by Inspection STOP (29.11.2016 20:03)

// --Commented out by Inspection START (29.11.2016 20:03):
//    public int getNumber() {
//        return number;
//    }
// --Commented out by Inspection STOP (29.11.2016 20:03)

// --Commented out by Inspection START (29.11.2016 20:03):
//    public void setNumber(int number) {
//        this.number = number;
//    }
// --Commented out by Inspection STOP (29.11.2016 20:03)

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", number=" + number +
                '}';
    }
}
