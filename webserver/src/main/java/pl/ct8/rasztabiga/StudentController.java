package pl.ct8.rasztabiga;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.ct8.rasztabiga.models.Dyzurni;

@RestController
public class StudentController {

    private static final String template = "Dużurnymi są: %s %s!";

    @RequestMapping("/dyzurni")
    public Dyzurni whoIs()
    {
        return App.dyzurni;
    }

}
