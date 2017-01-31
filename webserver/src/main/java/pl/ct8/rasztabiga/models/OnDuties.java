package pl.ct8.rasztabiga.models;

import java.util.List;

public class OnDuties {
    private Student[] studentsArray = new Student[2];

    public OnDuties(List<Student> studentsList) {
        studentsArray[0] = studentsList.get(0);
        studentsArray[1] = studentsList.get(1);
    }

    public OnDuties(Student first, Student second) {
    }

    public Student[] getStudentsArray() {
        return studentsArray;
    }
}
