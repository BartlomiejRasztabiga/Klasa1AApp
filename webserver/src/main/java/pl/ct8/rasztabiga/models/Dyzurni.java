package pl.ct8.rasztabiga.models;


public class Dyzurni {
    private Student dyzurny1;
    private Student dyzurny2;

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

    public String writeDyzurni() {
        return "Dyżurnymi są: " + dyzurny1 + " i " + dyzurny2;
    }
}
