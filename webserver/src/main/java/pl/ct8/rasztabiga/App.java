package pl.ct8.rasztabiga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.ct8.rasztabiga.models.Dyzurni;
import pl.ct8.rasztabiga.utils.LoggerUtils;
import pl.ct8.rasztabiga.utils.SecurityUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
public class App {

    //static final List<Student> LIST = new ArrayList<>(DatabaseController.getStudents());
    private static final String CRON_EXPRESSION = "0 0 1 * * 1";
    private static final String RESET_LUCKY_NUMBERS_CRON_EXPRESSION = "0 0 1 * * 6";
    private static Logger logger = LoggerUtils.getLogger();

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);


    }

    /*private static void fillListWithRealNames() {
        LIST.add(new Student("Jakub", "Bębacz", 1));
        LIST.add(new Student("Karol", "Brożyna", 2));
        LIST.add(new Student("Przemysław", "Cedro", 3));
        LIST.add(new Student("Mateusz", "Chodurski", 4));
        LIST.add(new Student("Roksana", "Cieśla", 5));
        LIST.add(new Student("Tomasz", "Domagała", 6));
        LIST.add(new Student("Izabela", "Działak", 7));
        LIST.add(new Student("Weronika", "Dziwirek", 8));
        LIST.add(new Student("Marcin", "Grzegorzewski", 9));
        LIST.add(new Student("Radosław", "Grzesik", 10));
        LIST.add(new Student("Karolina", "Homa", 11));
        LIST.add(new Student("Adam", "Kaleta", 12));
        LIST.add(new Student("Bartłomiej", "Kopyść", 13));
        LIST.add(new Student("Jakub", "Kozieł", 14));
        LIST.add(new Student("Mateusz", "Krzysiek", 15));
        LIST.add(new Student("Jan", "Kuc", 16));
        LIST.add(new Student("Jessica", "Łukawska", 17));
        LIST.add(new Student("Sylwia", "Malarczyk", 18));
        LIST.add(new Student("Aleksandra", "Mazur", 19));
        LIST.add(new Student("Izabela", "Mojecka", 20));
        LIST.add(new Student("Kinga", "Nowakowska", 21));
        LIST.add(new Student("Agnieszka", "Pajdała", 22));
        LIST.add(new Student("Kamil", "Petrus", 23));
        LIST.add(new Student("Karol", "Polit", 24));
        LIST.add(new Student("Bartłomiej", "Rasztabiga", 25));
        LIST.add(new Student("Jędrzej", "Sarna", 26));
        LIST.add(new Student("Miłosz", "Słoń", 27));
        LIST.add(new Student("Mateusz", "Sobierajski", 28));
        LIST.add(new Student("Mikołaj", "Stefański", 29));
        LIST.add(new Student("Dawid", "Wąsala", 30));
        LIST.add(new Student("Wiktor", "Wdowin", 31));
        LIST.add(new Student("Barbara", "Winkler", 32));
        LIST.add(new Student("Karol", "Wyrębkiewicz", 33));
    }*/

    static void nextDuzyrni() throws SQLException {

        int number1, number2;
        Dyzurni dyzurni = DatabaseController.getDyzurni();

        number1 = dyzurni.getDyzurny1().getNumber();
        number2 = dyzurni.getDyzurny2().getNumber();

        number1 += 2;
        number2 += 2;
        if (number1 > 33) {
            number1 = number1 - 33;
        }
        if (number2 > 33) {
            number2 = number2 - 33;
        }

        DatabaseController.setDyzurni(number1, number2);
    }

    @Scheduled(cron = CRON_EXPRESSION)
    private static void scheduleSetDyzurni() {
        try {
            nextDuzyrni();
        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
        System.out.println("Set dyzurni: at " + new Date());
    }

    @Scheduled(cron = RESET_LUCKY_NUMBERS_CRON_EXPRESSION)
    private static void resetLuckyNumbers() {
        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0));
        try {
            DatabaseController.setLuckyNumbers(list);
        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
    }
}
