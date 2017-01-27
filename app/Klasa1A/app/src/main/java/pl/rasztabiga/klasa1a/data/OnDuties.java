package pl.rasztabiga.klasa1a.data;


import java.util.ArrayList;
import java.util.List;

import pl.rasztabiga.klasa1a.models.*;

public class OnDuties {
    private List<Student> studentList;

    public OnDuties(List<Student> studentsList) {
        this.studentList = new ArrayList<>(studentsList);
    }
}
