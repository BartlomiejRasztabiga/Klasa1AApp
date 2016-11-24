package pl.ct8.rasztabiga;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ct8.rasztabiga.models.Dyzurni;

@RestController
public class StudentController {

    @RequestMapping("/dyzurni")
    public Dyzurni whoIs()
    {
        //App.setDuzyrni();
        return App.dyzurni;
    }

}
