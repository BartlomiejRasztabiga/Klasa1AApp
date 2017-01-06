package pl.ct8.rasztabiga;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ct8.rasztabiga.models.Exam;
import pl.ct8.rasztabiga.utils.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
public class StudentController {

    private static Logger logger = LoggerUtils.getLogger();

    @RequestMapping(value = "/dyzurni", method = RequestMethod.GET)
    public ResponseEntity<?> getDyzurniOld(@RequestParam("apiKey") String apiKey) {
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.USER);
            return new ResponseEntity<>(DatabaseController.getDyzurni(), HttpStatus.OK);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/getdyzurni", method = RequestMethod.GET)
    public ResponseEntity<?> getDyzurni(@RequestParam("apiKey") String apiKey) {
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.USER);
            return new ResponseEntity<>(DatabaseController.getDyzurni(), HttpStatus.OK);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/setdyzurni", method = RequestMethod.GET)
    public ResponseEntity<?> setDyzurni(@RequestParam("first") int first, @RequestParam("second") int second, @RequestParam("apiKey") String apiKey) {
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.ADMIN);
            DatabaseController.setDyzurni(first, second);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/nextdyzurni", method = RequestMethod.GET)
    public ResponseEntity<?> nextDyzurni(@RequestParam("apiKey") String apiKey) {
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.ADMIN);
            App.nextDuzyrni();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/getluckynumbers", method = RequestMethod.GET)
    public ResponseEntity<?> getLuckyNumbers(@RequestParam("apiKey") String apiKey) {
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.USER);
            return new ResponseEntity<>(DatabaseController.getLuckyNumbers(), HttpStatus.OK);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/setluckynumbers", method = RequestMethod.GET)
    public ResponseEntity<?> setLuckyNumbers(@RequestParam Map<String, String> numbers, @RequestParam("apiKey") String apiKey) {
        ArrayList<Integer> list = new ArrayList<>(5);
        list.add(Integer.valueOf(numbers.get("a")));
        list.add(Integer.valueOf(numbers.get("b")));
        list.add(Integer.valueOf(numbers.get("c")));
        list.add(Integer.valueOf(numbers.get("d")));
        list.add(Integer.valueOf(numbers.get("e")));
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.ADMIN);
            DatabaseController.setLuckyNumbers(list);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/getexams", method = RequestMethod.GET)
    public ResponseEntity<?> getExams(@RequestParam("apiKey") String apiKey) {
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.USER);
            return new ResponseEntity<>(DatabaseController.getExams(), HttpStatus.OK);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/addexam", method = RequestMethod.GET)
    public ResponseEntity<?> addExam(@RequestParam Map<String, String> list, @RequestParam("apiKey") String apiKey) {
        Exam exam = new Exam(list.get("subject"), list.get("desc"), Integer.valueOf(list.get("year")),
                Integer.valueOf(list.get("month")), Integer.valueOf(list.get("day")));
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.ADMIN);
            DatabaseController.addExam(exam);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }


    //TODO ADD APIKEY LATER, WHEN EVERYONE HAS THE APP
    /*@RequestMapping(value = "/getversion", method = RequestMethod.GET)
    public ResponseEntity<?> getVersionCode(@RequestParam("apiKey") String apiKey) {
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.USER);
            return new ResponseEntity<>(DatabaseController.getActualVersionCode(), HttpStatus.OK);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }*/

    @RequestMapping(value = "/getversion", method = RequestMethod.GET)
    public ResponseEntity<?> getVersionCode() {
        try {
            return new ResponseEntity<>(DatabaseController.getActualVersionCode(), HttpStatus.OK);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/setversion", method = RequestMethod.GET)
    public ResponseEntity<?> setVersionCode(@RequestParam("ver") int versionCode, @RequestParam("apiKey") String apiKey) {
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.ADMIN);
            DatabaseController.setActualVersionCode(versionCode);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/sendApiKeys", method = RequestMethod.GET)
    public ResponseEntity<?> sendApiKeys(@RequestParam("apiKey") String apiKey) {
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.ADMIN);
            List<String> addressList = new ArrayList<>(DatabaseController.getEmailsWithoutApiCodeList());
            List<String> apiKeysList = new ArrayList<>(DatabaseController.getApiCodesList());
            if (!addressList.isEmpty()) {
                for (String address : addressList) {
                    String apiKeyString;
                    while (true) {
                        apiKeyString = ApiCodeGenerator.nextApiCode();
                        if (!apiKeysList.contains(apiKeyString) || apiKeysList.isEmpty()) break;
                    }
                    apiKeysList.add(apiKeyString);
                    DatabaseController.setApiCode(apiKeyString, address);

                    String message = "Twój klucz dostępu do apki: ";
                    new Thread(new EmailSender(address, message + apiKeyString)).start();
                    //TODO Move it to new thread

                    System.out.println("Successfully sent " + apiKeyString + " to " + address);
                }
            }
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/checkApiKey", method = RequestMethod.GET)
    public ResponseEntity<?> checkApiKey(@RequestParam("apiKey") String apiKey) {
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.USER);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }


    /** FEATURE */


    @RequestMapping(value = "/getchangingroomstatus", method = RequestMethod.GET)
    public ResponseEntity<?> getChangingRoomStatus(@RequestParam("apiKey") String apiKey){
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.USER);
            return new ResponseEntity<>(DatabaseController.getChangingRoomStatus(), HttpStatus.OK);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    @RequestMapping(value = "/setchangingroomstatus", method = RequestMethod.GET)
    public ResponseEntity<?> setChangingRoomStatus(@RequestParam("apiKey") String apiKey, int changingRoomStatus){
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.USER);
            DatabaseController.setChangingRoomStatus(changingRoomStatus);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }


    //Na gorze sa nowe metody

    // Na dole metody testowe do tworzenia tabel


    /*@RequestMapping(value = "/createstudentstable", method = RequestMethod.GET)
    public void createStudentsTable(@RequestParam("apiKey") String apiKey) {
        try {
            SecurityUtils.authenticate(apiKey, SecurityUtils.Role.ADMIN);
            DatabaseController.createStudentsTable();
        } catch (SQLException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ApiKeyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (NoPermissionsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }

    }

    *//*@RequestMapping(value = "/addstudentstotable", method = RequestMethod.GET)
    public void addStudents() {
        DatabaseController.addStudents(App.LIST);
    }*//*

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

    @RequestMapping(value = "/createNeededTables", method = RequestMethod.GET)
    public void createAllTables() {
        DatabaseController.createStudentsTable();
        DatabaseController.createExamsTable();
        DatabaseController.createSettingsTable();
    }
*/

}
