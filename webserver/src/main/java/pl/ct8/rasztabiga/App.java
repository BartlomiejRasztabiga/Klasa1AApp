package pl.ct8.rasztabiga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.ct8.rasztabiga.models.Dyzurni;
import pl.ct8.rasztabiga.models.Student;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class App {

    private static List<Student> list = new ArrayList<>(33);
    public static Dyzurni dyzurni;
    private static int number1, number2;
    private static Properties prop = new Properties();

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
/*
        //writeDyzurni();
        readDyzurni();

        System.out.println(number1);
        System.out.println(number2);
*/
        fillListWithRealNames();
        simulate(30);
        //setDuzyrni();
        //System.out.println(dyzurni.getDyzurny1() + " " + dyzurni.getDyzurny2());

        //for(Student a : list) {
        //    System.out.println(a);
        //}
    }

    public static void simulate(int weeks) {
        for(int i = 1; i < weeks; i++) {
            setDuzyrni();
            System.out.println("Tydzien " + i);
            System.out.println(dyzurni.getDyzurny1() + " " + dyzurni.getDyzurny2());
        }

    }

    private static void fillListWithRealNames() {
        list.add(new Student("Jakub", "Bębacz", 1));
        list.add(new Student("Karol", "Brożyna", 2));
        list.add(new Student("Przemysław", "Cedro", 3));
        list.add(new Student("Mateusz", "Chodurski", 4));
        list.add(new Student("Roskana", "Cieśla", 5));
        list.add(new Student("Tomasz", "Domagała", 6));
        list.add(new Student("Izabela", "Działak", 7));
        list.add(new Student("Weronika", "Dziwirek", 8));
        list.add(new Student("Marcin", "Grzegorzewski", 9));
        list.add(new Student("Radosław", "Grzesik", 10));
        list.add(new Student("Karolina", "Homa", 11));
        list.add(new Student("Adam", "Kaleta", 12));
        list.add(new Student("Bartłomiej", "Kopyść", 13));
        list.add(new Student("Jakub", "Kozieł", 14));
        list.add(new Student("Mateusz", "Krzysiek", 15));
        list.add(new Student("Jan", "Kuc", 16));
        list.add(new Student("Jessica", "Łukawska", 17));
        list.add(new Student("Sylwia", "Malarczyk", 18));
        list.add(new Student("Aleksandra", "Mazur", 19));
        list.add(new Student("Izabela", "Mojecka", 20));
        list.add(new Student("Kinga", "Nowakowska", 21));
        list.add(new Student("Agnieszka", "Pajdała", 22));
        list.add(new Student("Kamil", "Petrus", 23));
        list.add(new Student("Karol", "Polit", 24));
        list.add(new Student("Bartłomiej", "Rasztabiga", 25));
        list.add(new Student("Jędrzej", "Sarna", 26));
        list.add(new Student("Miłosz", "Słoń", 27));
        list.add(new Student("Mateusz", "Sobierajski", 28));
        list.add(new Student("Mikołaj", "Stefański", 29));
        list.add(new Student("Dawid", "Wąsala", 30));
        list.add(new Student("Wiktor", "Wdowin", 31));
        list.add(new Student("Barbara", "Winkler", 32));
        list.add(new Student("Karol", "Wyrębkiewicz", 33));
    }

    private static void readDyzurni() {
        InputStream input = null;

        try {

            input = new FileInputStream("src/main/java/pl/ct8/rasztabiga/config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            number1 = Integer.valueOf(prop.getProperty("first"));
            number2 = Integer.valueOf(prop.getProperty("second"));

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

            output = new FileOutputStream("src/main/java/pl/ct8/rasztabiga/config.properties");

            // set the properties value
            prop.setProperty("first", String.valueOf(number1));
            prop.setProperty("second", String.valueOf(number2));

            // save properties to project root folder
            prop.store(output, null);

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

    private static void setDuzyrni() {

        readDyzurni();

        //System.out.println(number1);
        //System.out.println(number2);

        dyzurni = new Dyzurni(list.get(number1 - 1), list.get(number2 - 1));
        number1 += 2;
        number2 += 2;
        if(number1 > 33) {
            number1 = number1 - 33;
        }
        if(number2 > 33) {
            number2 = number2 - 33;
        }
        writeDyzurni();
    }

    public static void startSchedule() {

    }
}
