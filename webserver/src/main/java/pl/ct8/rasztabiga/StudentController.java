package pl.ct8.rasztabiga;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ct8.rasztabiga.models.Dyzurni;
import pl.ct8.rasztabiga.models.Exam;
import pl.ct8.rasztabiga.models.LuckyNumbers;
import pl.ct8.rasztabiga.models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    @RequestMapping(value = "/dyzurni", method = RequestMethod.GET)
    public Dyzurni getDyzurniOld() {
        return DatabaseController.getDyzurni();
    }

    @RequestMapping(value = "/getdyzurni", method = RequestMethod.GET)
    public Dyzurni getDyzurni() {
        return DatabaseController.getDyzurni();
    }

    @RequestMapping(value = "/setdyzurni", method = RequestMethod.POST)
    public void setDyzurni(@RequestParam("first") int first, @RequestParam("second") int second) {
        DatabaseController.setDyzurni(first, second);
    }

    @RequestMapping(value = "/nextdyzurni", method = RequestMethod.GET)
    public void nextDyzurni() {
        App.nextDuzyrni();
    }

    @RequestMapping(value = "/getluckynumbers", method = RequestMethod.GET)
    public LuckyNumbers getLuckyNumbers() {
        return DatabaseController.getLuckyNumbers();
    }

    @RequestMapping(value = "/setluckynumbers", method = RequestMethod.POST)
    public void setLuckyNumbers(@RequestParam Map<String, String> numbers) {
        ArrayList<Integer> list = new ArrayList<>(5);
        list.add(Integer.valueOf(numbers.get("a")));
        list.add(Integer.valueOf(numbers.get("b")));
        list.add(Integer.valueOf(numbers.get("c")));
        list.add(Integer.valueOf(numbers.get("d")));
        list.add(Integer.valueOf(numbers.get("e")));
        DatabaseController.setLuckyNumbers(list);
    }

    @RequestMapping(value = "/getexams", method = RequestMethod.GET)
    public List<Exam> getExams() {
        return DatabaseController.getExams();
    }

    @RequestMapping(value = "/addexam", method = RequestMethod.POST)
    public void addExam(@RequestParam Map<String, String> list) {
        Exam exam = new Exam(list.get("subject"), list.get("desc"), Integer.valueOf(list.get("year")),
                Integer.valueOf(list.get("month")), Integer.valueOf(list.get("day")));

        DatabaseController.addExam(exam);
    }

    @RequestMapping(value = "/getversion", method = RequestMethod.GET)
    public int getVersionCode() {
        return DatabaseController.getActualVersionCode();
    }

    @RequestMapping(value = "/setversion", method = RequestMethod.POST)
    public void setVersionCode(@RequestParam("ver") int versionCode) {
        DatabaseController.setActualVersionCode(versionCode);
    }

    //Na gorze sa nowe metody

    // Na dole metody testowe do tworzenia tabel


    @RequestMapping(value = "/createstudentstable", method = RequestMethod.POST)
    public void createStudentsTable() {
        DatabaseController.createStudentsTable();
    }

    @RequestMapping(value = "/addstudentstotable", method = RequestMethod.POST)
    public void addStudents() {
        DatabaseController.addStudents(App.LIST);
    }

    @RequestMapping(value = "/getstudentsfromdb", method = RequestMethod.GET)
    public List<Student> getStudentsFromDB() {
        return DatabaseController.getStudents();
    }

    @RequestMapping(value = "/createexamstable", method = RequestMethod.POST)
    public void createExamsTable() {
        DatabaseController.createExamsTable();
    }

    @RequestMapping(value = "/createsettingstable", method = RequestMethod.POST)
    public void createSettingsTable() {
        DatabaseController.createSettingsTable();
        DatabaseController.initializeSettingsTable();
    }

    @RequestMapping(value = "/createNeededTables", method = RequestMethod.POST)
    public void createAllTables() {
        DatabaseController.createStudentsTable();
        DatabaseController.createExamsTable();
        DatabaseController.createSettingsTable();
    }


}
