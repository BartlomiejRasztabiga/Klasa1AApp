package pl.ct8.rasztabiga;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.ct8.rasztabiga.models.Dyzurni;

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

}
