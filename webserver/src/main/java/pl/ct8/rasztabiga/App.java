package pl.ct8.rasztabiga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import pl.ct8.rasztabiga.models.Dyzurni;
import pl.ct8.rasztabiga.models.LuckyNumbers;
import pl.ct8.rasztabiga.models.Student;

import java.io.*;
import java.util.*;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
@EnableScheduling
public class App {

    private static final String CRON_EXPRESSION = "0 0 1 * * 1";
    private static final String RESRT_LUCKY_NUMBERS_CRON_EXPRESSION = "0 0 1 * * 6";
    private static final List<Student> LIST = new ArrayList<>(33);
    private static final Properties PROP = new Properties();
    static Dyzurni dyzurni;
    static LuckyNumbers luckyNumbers;
    private static int number1, number2;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

        fillListWithRealNames();

    }

    public static void simulate(int weeks) {
        for (int i = 1; i < weeks; i++) {
            setDuzyrni();
            System.out.println("Tydzien " + i);
            System.out.println(dyzurni.getDyzurny1() + " " + dyzurni.getDyzurny2());
        }

    }

    private static void fillListWithRealNames() {
        LIST.add(new Student("Jakub", "Bębacz", 1));
        LIST.add(new Student("Karol", "Brożyna", 2));
        LIST.add(new Student("Przemysław", "Cedro", 3));
        LIST.add(new Student("Mateusz", "Chodurski", 4));
        LIST.add(new Student("Roskana", "Cieśla", 5));
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
    }

    private static void readDyzurni() {
        InputStream input = null;

        try {

            input = new FileInputStream(System.getProperty("user.dir") + File.separator + "config.properties");

            // load a Properties file
            PROP.load(input);

            // get the Property value and print it out
            number1 = Integer.valueOf(PROP.getProperty("first"));
            number2 = Integer.valueOf(PROP.getProperty("second"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void writeDyzurni() {
        OutputStream output = null;

        try {

            output = new FileOutputStream(System.getProperty("user.dir") + File.separator + "config.properties");

            // set the Properties value
            PROP.setProperty("first", String.valueOf(number1));
            PROP.setProperty("second", String.valueOf(number2));

            // save Properties to project root folder
            PROP.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void setDuzyrni() {

        readDyzurni();

        dyzurni = new Dyzurni(LIST.get(number1 - 1), LIST.get(number2 - 1));
        number1 += 2;
        number2 += 2;
        if (number1 > 33) {
            number1 = number1 - 33;
        }
        if (number2 > 33) {
            number2 = number2 - 33;
        }

        writeDyzurni();
    }

    public static int readActualVersionCode() {
        InputStream input = null;

        try {

            input = new FileInputStream(System.getProperty("user.dir") + File.separator + "config.properties");

            // load a Properties file
            PROP.load(input);

            // get the Property value and print it out
            return Integer.valueOf(PROP.getProperty("actualVersionCode"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    public static void writeActualVersionCode(int versionCode) {
        OutputStream output = null;

        try {

            output = new FileOutputStream(System.getProperty("user.dir") + File.separator + "config.properties");

            // set the Properties value
            PROP.setProperty("actualVersionCode", String.valueOf(versionCode));

            // save Properties to project root folder
            PROP.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setLuckyNumbers(ArrayList<Integer> list) {
        luckyNumbers = new LuckyNumbers(list);
        writeLuckyNumbers();
    }

    public static void readLuckyNumbers() {
        InputStream input = null;

        try {

            input = new FileInputStream(System.getProperty("user.dir") + File.separator + "config.properties");

            // load a Properties file
            PROP.load(input);

            // get the Property value and print it out
            Integer monday = Integer.valueOf(PROP.getProperty("ln.monday"));
            Integer tuesday = Integer.valueOf(PROP.getProperty("ln.tuesday"));
            Integer wednesday = Integer.valueOf(PROP.getProperty("ln.wednesday"));
            Integer thursday = Integer.valueOf(PROP.getProperty("ln.thursday"));
            Integer friday = Integer.valueOf(PROP.getProperty("ln.friday"));

            ArrayList<Integer> list = new ArrayList<>(5);
            list.add(monday);
            list.add(tuesday);
            list.add(wednesday);
            list.add(thursday);
            list.add(friday);
            list.trimToSize();

            luckyNumbers = new LuckyNumbers(list);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void writeLuckyNumbers() {
        OutputStream output = null;

        try {

            output = new FileOutputStream(System.getProperty("user.dir") + File.separator + "config.properties");

            // set the Properties value
            ArrayList<Integer> list = luckyNumbers.getNumbersList();
            PROP.setProperty("ln.monday", String.valueOf(list.get(0)));
            PROP.setProperty("ln.tuesday", String.valueOf(list.get(1)));
            PROP.setProperty("ln.wednesday", String.valueOf(list.get(2)));
            PROP.setProperty("ln.thursday", String.valueOf(list.get(3)));
            PROP.setProperty("ln.friday", String.valueOf(list.get(4)));

            // save Properties to project root folder
            PROP.store(output, null);

        } catch (IOException io) {
            io.printStackTrace();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Scheduled(cron = CRON_EXPRESSION)
    private static void scheduleSetDyzurni() {
        App.setDuzyrni();
        System.out.println("Set dyzurni: at " + new Date());
    }

    @Scheduled(cron = RESRT_LUCKY_NUMBERS_CRON_EXPRESSION)
    private static void resetLuckyNumbers() {
        ArrayList<Integer> list = new ArrayList<>(5);
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(0);
        luckyNumbers = new LuckyNumbers(list);
        writeLuckyNumbers();
    }
}
