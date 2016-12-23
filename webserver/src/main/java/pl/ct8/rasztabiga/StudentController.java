package pl.ct8.rasztabiga;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ct8.rasztabiga.models.*;
import pl.ct8.rasztabiga.utils.ApiCodeGenerator;
import pl.ct8.rasztabiga.utils.EmailUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    @ModelAttribute()
    public void initialize() {
        System.out.println("ok xD");
    }

    @RequestMapping("/dyzurni")
    public Dyzurni getDyzurniOld() {
        return DatabaseController.getDyzurni();
    }

    @RequestMapping("/getdyzurni")
    public Dyzurni getDyzurni() {
        return DatabaseController.getDyzurni();
    }

    @RequestMapping("/setdyzurni")
    public void setDyzurni(@RequestParam("first") int first, @RequestParam("second") int second) {
        DatabaseController.setDyzurni(first, second);
    }

    @RequestMapping("/nextdyzurni")
    public void nextDyzurni() {
        App.nextDuzyrni();
    }

    @RequestMapping("/getluckynumbers")
    public LuckyNumbers getLuckyNumbers() {
        return DatabaseController.getLuckyNumbers();
    }

    @RequestMapping("/setluckynumbers")
    public void setLuckyNumbers(@RequestParam Map<String, String> numbers) {
        ArrayList<Integer> list = new ArrayList<>(5);
        list.add(Integer.valueOf(numbers.get("a")));
        list.add(Integer.valueOf(numbers.get("b")));
        list.add(Integer.valueOf(numbers.get("c")));
        list.add(Integer.valueOf(numbers.get("d")));
        list.add(Integer.valueOf(numbers.get("e")));
        DatabaseController.setLuckyNumbers(list);
    }

    @RequestMapping("/getexams")
    public List<Exam> getExams() {
        return DatabaseController.getExams();
    }

    @RequestMapping("/addexam")
    public void addExam(@RequestParam Map<String, String> list) {
        Exam exam = new Exam(list.get("subject"), list.get("desc"), Integer.valueOf(list.get("year")),
                Integer.valueOf(list.get("month")), Integer.valueOf(list.get("day")));

        DatabaseController.addExam(exam);
    }

    @RequestMapping("/getversion")
    public int getVersionCode() {
        return DatabaseController.getActualVersionCode();
    }

    @RequestMapping("/setversion")
    public void setVersionCode(@RequestParam("ver") int versionCode) {
        DatabaseController.setActualVersionCode(versionCode);
    }

    @RequestMapping("/sendApiKeys")
    public void sendApiKeys() {
        List<String> addressList = new ArrayList<>(DatabaseController.getEmailsWithoutApiCodeList());
        List<String> apiKeysList = new ArrayList<>(DatabaseController.getApiCodesList());
        if (!addressList.isEmpty()) {
            //System.out.println(DatabaseController.getEmailsWithoutApiCodeList().toString());
            //System.out.println(DatabaseController.getApiCodesList());
            for (String address : addressList) {
                String apiKey;
                while (true) {
                    apiKey = ApiCodeGenerator.nextApiCode();
                    if(!apiKeysList.contains(apiKey) && apiKeysList.isEmpty()) break;
                }
                apiKeysList.add(apiKey);
                DatabaseController.setApiCode(apiKey, address);
                EmailUtils.sendEmail(address, apiKey);
                System.out.println("Succesfully sent " + apiKey + " to " + address);
            }
        }
    }

    //Na gorze sa nowe metody

    // Na dole metody testowe do tworzenia tabel


    @RequestMapping("/createstudentstable")
    public void createStudentsTable() {
        DatabaseController.createStudentsTable();
    }

    @RequestMapping("/addstudentstotable")
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


}
