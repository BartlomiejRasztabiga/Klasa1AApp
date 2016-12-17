package pl.ct8.rasztabiga;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ct8.rasztabiga.models.Dyzurni;
import pl.ct8.rasztabiga.models.LuckyNumbers;
import pl.ct8.rasztabiga.models.Student;

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
    public LuckyNumbers setLuckyNumbers(@RequestParam Map<String, String> numbers) {
        ArrayList<Integer> list = new ArrayList<>(5);
        list.add(Integer.valueOf(numbers.get("a")));
        list.add(Integer.valueOf(numbers.get("b")));
        list.add(Integer.valueOf(numbers.get("c")));
        list.add(Integer.valueOf(numbers.get("d")));
        list.add(Integer.valueOf(numbers.get("e")));
        App.setLuckyNumbers(list);
        return App.luckyNumbers;
    }

    @RequestMapping("/setluckynumbersfromdisk")
    public LuckyNumbers setLuckyNumbersFromDisk() {
        App.readLuckyNumbers();
        return App.luckyNumbers;
    }
    @RequestMapping("/createstudentstable")
    public void createTable(){
        DataBaseController.createStudentsTable();
    }
    @RequestMapping("/addstudents")
    public void addStudents(){
        DataBaseController.addStudents(App.LIST);
    }
    @RequestMapping("/getstudentsfromdb")
    public List<Student> getStudentsFromDB(){
        return DataBaseController.getStudents();

    }



}
