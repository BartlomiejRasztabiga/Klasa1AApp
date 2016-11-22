package pl.ct8.rasztabiga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.ct8.rasztabiga.models.Dyzurni;
import pl.ct8.rasztabiga.models.Student;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class App {

    private static List<Student> list = new ArrayList<>(33);
    public static Dyzurni dyzurni;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        setDuzyrni();
        fillListWithRealNames();
    }

    private static void fillListWithRealNames() {
        list.add(new Student("Jan", "Kowalski", 1));
        list.add(new Student("Janina", "Kowalska", 2));
        list.add(new Student("aaaa", "bbbb", 3));
        list.add(new Student("vvvv", "dddd", 4));
        list.add(new Student("gfdg", "dfsdf", 5));
    }

    private static void setDuzyrni() {
        dyzurni = new Dyzurni(new Student("Bartłomiej", "Rasztabiga", 25), new Student("Izabela", "Działak", 7));
    }

    public static void startSchedule() {

    }
}
