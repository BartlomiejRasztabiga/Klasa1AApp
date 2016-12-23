package pl.ct8.rasztabiga;

import pl.ct8.rasztabiga.models.Dyzurni;
import pl.ct8.rasztabiga.models.Exam;
import pl.ct8.rasztabiga.models.LuckyNumbers;
import pl.ct8.rasztabiga.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {

    //TODO Add transaction where needed

    private static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:main.db");

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Connected to SQLite DB");
        return c;
    }

    static void createStudentsTable() {
        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {
            // creating table
            String sql = "CREATE TABLE STUDENTS"
                    + " (NUMBER INTEGER PRIMARY KEY NOT NULL,"
                    + " NAME    TEXT    NOT NULL, "
                    + " SURNAME TEXT    NOT NULL)";
            // executing
            stmt.executeUpdate(sql);
            // closing connection
        } catch (SQLException e) {
            System.out.println("Couldn't create table");
            e.printStackTrace();
        }
    }

    static void createExamsTable() {
        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {
            // creating table
            String sql = "CREATE TABLE EXAMS"
                    + " (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                    + " YEAR INTEGER, "
                    + " MONTH INTEGER, "
                    + " DAY INTEGER, "
                    + " SUBJECT TEXT,"
                    + " DESCRIPTION TEXT,"
                    + " VISIBLE INTEGER)";
            // executing
            stmt.executeUpdate(sql);
            // closing connection
        } catch (SQLException e) {
            System.out.println("Couldn't create table");
            e.printStackTrace();
        }
    }

    static void createSettingsTable() {
        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE SETTINGS (KEY TEXT, VALUE TEXT)";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Couldn't create table");
            e.printStackTrace();
        }
    }

    static void initializeSettingsTable() {
        String sql = "INSERT INTO SETTINGS (KEY, VALUE) VALUES (?, ?)";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "dyzurni.first");
            stmt.setString(2, "0");
            stmt.execute();

            stmt.setString(1, "dyzurni.second");
            stmt.setString(2, "0");
            stmt.execute();

            stmt.setString(1, "ln.monday");
            stmt.setString(2, "0");
            stmt.execute();

            stmt.setString(1, "ln.tuesday");
            stmt.setString(2, "0");
            stmt.execute();

            stmt.setString(1, "ln.wednesday");
            stmt.setString(2, "0");
            stmt.execute();

            stmt.setString(1, "ln.thursday");
            stmt.setString(2, "0");
            stmt.execute();

            stmt.setString(1, "ln.friday");
            stmt.setString(2, "0");
            stmt.execute();

            stmt.setString(1, "actualVersionNumber");
            stmt.setString(2, "0");
            stmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addExam(Exam exam) {
        String sql = "INSERT INTO EXAMS (YEAR, MONTH, DAY, SUBJECT, DESCRIPTION, VISIBLE) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, exam.getYear());
            stmt.setInt(2, exam.getMonth());
            stmt.setInt(3, exam.getDay());
            stmt.setString(4, exam.getSubject());
            stmt.setString(5, exam.getDesc());
            stmt.setInt(6, 1);

            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void addStudents(List<Student> studentList) {
        String sql = "INSERT INTO STUDENTS (NUMBER, NAME, SURNAME) VALUES (?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            for (Student student : studentList) {
                stmt.setInt(1, student.getNumber());
                stmt.setString(2, student.getName());
                stmt.setString(3, student.getSurname());
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static List<Student> getStudents() {
        List<Student> studentList = new ArrayList<>();
        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {

            String searchSQL = "SELECT * FROM STUDENTS";
            ResultSet rs = stmt.executeQuery(searchSQL);

            while (rs.next()) {
                Student student = new Student(rs.getString("name"), rs.getString("surname"), rs.getInt("number"));
                studentList.add(student);
            }

            return studentList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    static List<Exam> getExams() {
        List<Exam> examsList = new ArrayList<>();
        String sql = "SELECT * FROM EXAMS WHERE VISIBLE = 1";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Exam exam = new Exam(rs.getString("SUBJECT"), rs.getString("DESCRIPTION"),
                        rs.getInt("YEAR"), rs.getInt("MONTH"), rs.getInt("DAY"));

                examsList.add(exam);
            }

            return examsList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static Student getStudent(int number) {
        String sql = "SELECT * FROM STUDENTS WHERE NUMBER = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, number);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            return new Student(rs.getString("NAME"), rs.getString("SURNAME"), rs.getInt("NUMBER"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    static Dyzurni getDyzurni() {
        int first, second;
        String sql = "SELECT * FROM SETTINGS WHERE KEY = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "dyzurni.first");

            ResultSet rs = stmt.executeQuery();
            rs.next();

            first = Integer.valueOf(rs.getString("VALUE"));

            stmt.setString(1, "dyzurni.second");

            rs = stmt.executeQuery();
            rs.next();

            second = Integer.valueOf(rs.getString("VALUE"));

            return new Dyzurni(getStudent(first), getStudent(second));

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void setDyzurni(int first, int second) {
        String sql = "UPDATE SETTINGS SET VALUE = ? WHERE KEY = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, String.valueOf(first));
            stmt.setString(2, "dyzurni.first");

            stmt.executeUpdate();

            stmt.setString(1, String.valueOf(second));
            stmt.setString(2, "dyzurni.second");

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static LuckyNumbers getLuckyNumbers() {
        ArrayList<Integer> list = new ArrayList<>(5);
        String sql = "SELECT * FROM SETTINGS WHERE KEY = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, "ln.monday");
            ResultSet rs = stmt.executeQuery();
            rs.next();
            list.add(Integer.valueOf(rs.getString("VALUE")));

            stmt.setString(1, "ln.tuesday");
            rs = stmt.executeQuery();
            rs.next();
            list.add(Integer.valueOf(rs.getString("VALUE")));

            stmt.setString(1, "ln.wednesday");
            rs = stmt.executeQuery();
            rs.next();
            list.add(Integer.valueOf(rs.getString("VALUE")));

            stmt.setString(1, "ln.thursday");
            rs = stmt.executeQuery();
            rs.next();
            list.add(Integer.valueOf(rs.getString("VALUE")));

            stmt.setString(1, "ln.friday");
            rs = stmt.executeQuery();
            rs.next();
            list.add(Integer.valueOf(rs.getString("VALUE")));

            return new LuckyNumbers(list);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void setLuckyNumbers(ArrayList<Integer> list) {
        String sql = "UPDATE SETTINGS SET VALUE = ? WHERE KEY = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){

            stmt.setString(1, String.valueOf(list.get(0)));
            stmt.setString(2, "ln.monday");

            stmt.executeUpdate();

            stmt.setString(1, String.valueOf(list.get(1)));
            stmt.setString(2, "ln.tuesday");

            stmt.executeUpdate();

            stmt.setString(1, String.valueOf(list.get(2)));
            stmt.setString(2, "ln.wednesday");

            stmt.executeUpdate();

            stmt.setString(1, String.valueOf(list.get(3)));
            stmt.setString(2, "ln.thursday");

            stmt.executeUpdate();

            stmt.setString(1, String.valueOf(list.get(4)));
            stmt.setString(2, "ln.friday");

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static int getActualVersionCode() {
        String sql = "SELECT * FROM SETTINGS WHERE KEY = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "actualVersionNumber");
            ResultSet rs = stmt.executeQuery();
            rs.next();

            return Integer.valueOf(rs.getString("VALUE"));
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    static void setActualVersionCode(int versionCode) {
        String sql = "UPDATE SETTINGS SET VALUE = ? WHERE KEY = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, String.valueOf(versionCode));
            stmt.setString(2, "actualVersionNumber");

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static List<String> getEmailsWithoutApiCodeList() {
        List<String> addressList = new ArrayList<>();
        String sql = "SELECT * FROM API_KEYS WHERE api_key ISNULL";
        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                addressList.add(rs.getString("email"));
            }

            return addressList;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    static List<String> getApiCodesList() {
        List<String> apiCodesList = new ArrayList<>();
        String sql = "SELECT * FROM API_KEYS WHERE api_key NOT NULL";
        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                apiCodesList.add(rs.getString("api_key"));
            }

            return apiCodesList;

        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    static void setApiCode(String apiCode, String email) {

        String sql = "UPDATE API_KEYS SET api_key = ? WHERE email = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, apiCode);
            stmt.setString(2, email);

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

