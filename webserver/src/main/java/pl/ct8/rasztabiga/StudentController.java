package pl.ct8.rasztabiga;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ct8.rasztabiga.models.*;
import pl.ct8.rasztabiga.utils.ApiCodeGenerator;
import pl.ct8.rasztabiga.utils.EmailUtils;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    @ModelAttribute()
    public void initialize() {
        System.out.println("ok xD");
    }

@RequestMapping(value = "/dyzurni", method = RequestMethod.GET)
    public ResponseEntity<?> getDyzurniOld() {
        try {
            return new ResponseEntity<>(DatabaseController.getDyzurni(), HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getdyzurni", method = RequestMethod.GET)
    public ResponseEntity<?> getDyzurni() {
        try {
            return new ResponseEntity<>(DatabaseController.getDyzurni(), HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/setdyzurni", method = RequestMethod.GET)
    public ResponseEntity<?> setDyzurni(@RequestParam("first") int first, @RequestParam("second") int second) {
        try {
            DatabaseController.setDyzurni(first, second);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/nextdyzurni", method = RequestMethod.GET)
    public  ResponseEntity<?> nextDyzurni() {
        try {
            App.nextDuzyrni();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getluckynumbers", method = RequestMethod.GET)
    public ResponseEntity<?> getLuckyNumbers() {
        try {
            return new ResponseEntity<>(DatabaseController.getLuckyNumbers(), HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/setluckynumbers", method = RequestMethod.GET)
    public ResponseEntity<?> setLuckyNumbers(@RequestParam Map<String, String> numbers) {
        ArrayList<Integer> list = new ArrayList<>(5);
        list.add(Integer.valueOf(numbers.get("a")));
        list.add(Integer.valueOf(numbers.get("b")));
        list.add(Integer.valueOf(numbers.get("c")));
        list.add(Integer.valueOf(numbers.get("d")));
        list.add(Integer.valueOf(numbers.get("e")));
        try {
            DatabaseController.setLuckyNumbers(list);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getexams", method = RequestMethod.GET)
    public ResponseEntity<?> getExams() {
        try {
            return new ResponseEntity<>(DatabaseController.getExams(), HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/addexam", method = RequestMethod.GET)
    public ResponseEntity<?> addExam(@RequestParam Map<String, String> list) {
        Exam exam = new Exam(list.get("subject"), list.get("desc"), Integer.valueOf(list.get("year")),
                Integer.valueOf(list.get("month")), Integer.valueOf(list.get("day")));

        try {
            DatabaseController.addExam(exam);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getversion", method = RequestMethod.GET)
    public ResponseEntity<?> getVersionCode() {
        try {
            return new ResponseEntity<>(DatabaseController.getActualVersionCode(), HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/setversion", method = RequestMethod.GET)
    public ResponseEntity<?> setVersionCode(@RequestParam("ver") int versionCode) {
        try {
            DatabaseController.setActualVersionCode(versionCode);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/sendApiKeys", method = RequestMethod.GET)
    public ResponseEntity<?> sendApiKeys() {
        try {
            List<String> addressList = new ArrayList<>(DatabaseController.getEmailsWithoutApiCodeList());
            List<String> apiKeysList = new ArrayList<>(DatabaseController.getApiCodesList());
            if (!addressList.isEmpty()) {
                //System.out.println(DatabaseController.getEmailsWithoutApiCodeList().toString());
                //System.out.println(DatabaseController.getApiCodesList());
                for (String address : addressList) {
                    String apiKey;
                    while (true) {
                        apiKey = ApiCodeGenerator.nextApiCode();
                        if(!apiKeysList.contains(apiKey) || apiKeysList.isEmpty()) break;
                    }
                    apiKeysList.add(apiKey);
                    DatabaseController.setApiCode(apiKey, address);
                    EmailUtils.sendEmail(address, apiKey);
                    System.out.println("Succesfully sent " + apiKey + " to " + address);
                }
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Na gorze sa nowe metody

    // Na dole metody testowe do tworzenia tabel


    @RequestMapping(value = "/createstudentstable", method = RequestMethod.GET)
    public void createStudentsTable() {
        DatabaseController.createStudentsTable();
    }

    @RequestMapping(value = "/addstudentstotable", method = RequestMethod.GET)
    public void addStudents() {
        DatabaseController.addStudents(App.LIST);
    }

    @RequestMapping(value = "/getstudentsfromdb", method = RequestMethod.GET)
    public List<Student> getStudentsFromDB() {
        return DatabaseController.getStudents();
    }

    @RequestMapping(value = "/createexamstable", method = RequestMethod.GET)
    public void createExamsTable() {
        DatabaseController.createExamsTable();
    }

    @RequestMapping(value = "/createsettingstable", method = RequestMethod.GET)
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
