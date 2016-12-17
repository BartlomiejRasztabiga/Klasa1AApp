package pl.ct8.rasztabiga;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ct8.rasztabiga.models.Dyzurni;
import pl.ct8.rasztabiga.models.LuckyNumbers;

import java.util.ArrayList;

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
    @RequestMapping("/createtable")
    public void createTable(){
        DataBaseController.createTable();
    }
    @RequestMapping("/addstudents")
    public void addStudents(){
        DataBaseController.addStudents(App.LIST);
    }




}
