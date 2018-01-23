package pl.rasztabiga.klasa1a.data.source.onDuties.models;

public class Student {

    private String name;
    private String surname;
    private Integer number;

    public Student(String name, String surname, int number) {
        this.name = name;
        this.surname = surname;
        this.number = number;
    }

    public Student() {
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", number=" + number +
                '}';
    }
}
