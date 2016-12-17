package pl.ct8.rasztabiga;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ct8.rasztabiga.models.Dyzurni;
import pl.ct8.rasztabiga.models.Exam;
import pl.ct8.rasztabiga.models.LuckyNumbers;
import pl.ct8.rasztabiga.models.Student;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    @RequestMapping("/dyzurni")
    public Dyzurni whoIs() {
        //App.setDuzyrni();
        return App.dyzurni;
    }

    @RequestMapping("/setdyzurni")
    public Dyzurni setDyzurni() {
        App.setDuzyrni();
        return App.dyzurni;
    }

    @RequestMapping("/setversion")
    public int setVersionCode(@RequestParam("ver") int versionCode) {
        App.writeActualVersionCode(versionCode);
        return App.readActualVersionCode();
    }

    @RequestMapping("/getversion")
    public int getVersionCode() {
        return App.readActualVersionCode();
    }

    @RequestMapping("/getluckynumbers")
    public LuckyNumbers getLuckyNumbers() {
        return App.luckyNumbers;
    }

    @RequestMapping("/setluckynumbers")
    public LuckyNumbers setLuckyNumbers(@RequestParam("a") int monday, @RequestParam("b") int tuesday, @RequestParam("c") int wednesday, @RequestParam("d") int thursday, @RequestParam("e") int friday) {
        ArrayList<Integer> list = new ArrayList<>(5);
        list.add(monday);
        list.add(tuesday);
        list.add(wednesday);
        list.add(thursday);
        list.add(friday);
        App.setLuckyNumbers(list);
        return App.luckyNumbers;
    }

    @RequestMapping("/setluckynumbersfromdisk")
    public LuckyNumbers setLuckyNumbersFromDisk() {
        App.readLuckyNumbers();
        return App.luckyNumbers;
    }

    @RequestMapping("/createstudentstable")
    public void createStudentsTable() {
        DatabaseController.createStudentsTable();
    }

    @RequestMapping("/addstudents")
    public void addStudents() {
        DatabaseController.addStudents(App.LIST);
    }

    @RequestMapping("/getstudentsfromdb")
    public List<Student> getStudentsFromDB() {
        return DatabaseController.getStudents();
    }

    @RequestMapping("/createexamstable")
    public void createExamsTable() {
        DatabaseController.createExamsTable();
    }

    @RequestMapping("/addexam")
    public void addExam(@RequestParam Map<String, String> list) {
        Exam exam = new Exam(list.get("subject"), list.get("desc"), Integer.valueOf(list.get("year")),
                Integer.valueOf(list.get("month")), Integer.valueOf(list.get("day")));

        DatabaseController.addExam(exam);
    }

    @RequestMapping("/createsettingstable")
    public void createSettingsTable() {
        DatabaseController.createSettingsTable();
        DatabaseController.initializeSettingsTable();
    }

    @RequestMapping("/createNeededTables")
    public void createAllTables() {
        DatabaseController.createStudentsTable();
        DatabaseController.createExamsTable();
        DatabaseController.createSettingsTable();
    }

    @RequestMapping("/getexams")
    public List<Exam> getExams() {
        return DatabaseController.getExams();
    }

}
