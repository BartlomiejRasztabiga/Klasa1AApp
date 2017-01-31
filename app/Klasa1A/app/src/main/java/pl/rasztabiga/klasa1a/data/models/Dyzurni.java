package pl.rasztabiga.klasa1a.data.models;


import pl.rasztabiga.klasa1a.data.source.onDuties.models.Student;

public final class Dyzurni {
    private final Student dyzurny1;
    private final Student dyzurny2;

    public Dyzurni(Student dyzurny1, Student dyzurny2) {
        this.dyzurny1 = dyzurny1;
        this.dyzurny2 = dyzurny2;
    }

    public Student getDyzurny1() {
        return dyzurny1;
    }

    public Student getDyzurny2() {
        return dyzurny2;
    }
}
