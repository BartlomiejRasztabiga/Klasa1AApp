package pl.ct8.rasztabiga;

import pl.ct8.rasztabiga.models.*;
import pl.ct8.rasztabiga.utils.LoggerUtils;
import pl.ct8.rasztabiga.utils.SecurityUtils;
import sun.rmi.runtime.Log;

import javax.swing.text.Style;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseController {

    private static Logger logger = LoggerUtils.getLogger();

    //TODO Add transaction where needed

    private static Connection getConnectionToAnalyticsDB() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:analytics.db");
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return c;
    }

    private static Connection getConnection() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:main.db");

        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
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
            logger.warning(e.getMessage());
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
            logger.warning(e.getMessage());
        }
    }

    static void createSettingsTable() {
        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {
            String sql = "CREATE TABLE SETTINGS (KEY TEXT, VALUE TEXT)";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Couldn't create table");
            logger.warning(e.getMessage());
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

            //Test change

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

            /** FEATURE */

            stmt.setString(1, "changingRoomStatus");
            stmt.setString(2, "0");
            stmt.execute();

            stmt.setString(1, "doorStatus");
            stmt.setString(2, "0");
            stmt.execute();

        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
    }

    static void addExam(Exam exam) throws SQLException {
        String sql = "INSERT INTO EXAMS (YEAR, MONTH, DAY, SUBJECT, DESCRIPTION, VISIBLE) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, exam.getYear());
            stmt.setInt(2, exam.getMonth());
            stmt.setInt(3, exam.getDay());
            stmt.setString(4, exam.getSubject());
            stmt.setString(5, exam.getDesc());
            stmt.setInt(6, 1);

            stmt.execute();
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
            logger.warning(e.getMessage());
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
            logger.warning(e.getMessage());
            return null;
        }

    }

    static List<Exam> getExams() throws SQLException {
        List<Exam> examsList = new ArrayList<>();
        String sql = "SELECT * FROM EXAMS WHERE VISIBLE = 1";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Exam exam = new Exam(rs.getInt("ID"), rs.getString("SUBJECT"), rs.getString("DESCRIPTION"),
                        rs.getInt("YEAR"), rs.getInt("MONTH"), rs.getInt("DAY"));
                examsList.add(exam);
            }
            return examsList;
        }
    }

    static ArrayList<String> getAssociatedImagesList(int examId) throws SQLException {
        ArrayList<String> associatedImagesList = new ArrayList<>();
        String sql = "SELECT * FROM EXAMS_PHOTOS WHERE exam_id = ?";
        try(Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, examId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                associatedImagesList.add(rs.getString("name"));
            }
            return associatedImagesList;
        }
    }

    private static Student getStudent(int number) {
        String sql = "SELECT * FROM STUDENTS WHERE NUMBER = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, number);

            ResultSet rs = stmt.executeQuery();
            rs.next();

            return new Student(rs.getString("NAME"), rs.getString("SURNAME"), rs.getInt("NUMBER"));
        } catch (SQLException e) {
            logger.warning(e.getMessage());
            return null;
        }

    }

    /** FEATURE */

    static String getChangingRoomStatus() throws SQLException {
        String changingRoomStatus;
        String sql = "SELECT * FROM SETTINGS WHERE KEY = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, "changingRoomStatus");

            ResultSet rs = stmt.executeQuery();
            rs.next();

            changingRoomStatus = rs.getString("VALUE");
        }
        return changingRoomStatus;
    }
    static void setChangingRoomStatus(int changingRoomStatus) throws SQLException {
        String sql = "UPDATE SETTINGS SET VALUE = ? WHERE KEY = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, String.valueOf(changingRoomStatus));
            stmt.setString(2, "changingRoomStatus");

            stmt.executeUpdate();

        }
    }
    static String getDoorStatus() throws SQLException {
        String doorStatus;
        String sql = "SELECT * FROM SETTINGS WHERE KEY = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, "doorStatus");

            ResultSet rs = stmt.executeQuery();
            rs.next();

            doorStatus = rs.getString("VALUE");
        }
        return doorStatus;
    }
    static void setDoorStatus(int doorStatus) throws SQLException {
        String sql = "UPDATE SETTINGS SET VALUE = ? WHERE KEY = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, String.valueOf(doorStatus));
            stmt.setString(2, "doorStatus");

            stmt.executeUpdate();

        }
    }

    static Dyzurni getDyzurni() throws SQLException {
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

        }
    }

    static void setDyzurni(int first, int second) throws SQLException {
        String sql = "UPDATE SETTINGS SET VALUE = ? WHERE KEY = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, String.valueOf(first));
            stmt.setString(2, "dyzurni.first");

            stmt.executeUpdate();

            stmt.setString(1, String.valueOf(second));
            stmt.setString(2, "dyzurni.second");

            stmt.executeUpdate();

        }
    }

    static LuckyNumbers getLuckyNumbers() throws SQLException {
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
        }
    }

    static void setLuckyNumbers(ArrayList<Integer> list) throws SQLException {
        String sql = "UPDATE SETTINGS SET VALUE = ? WHERE KEY = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

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
        }
    }

    static int getActualVersionCode() throws SQLException {
        String sql = "SELECT * FROM SETTINGS WHERE KEY = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "actualVersionNumber");
            ResultSet rs = stmt.executeQuery();
            rs.next();

            return Integer.valueOf(rs.getString("VALUE"));
        }
    }

    static void setActualVersionCode(int versionCode) throws SQLException {
        String sql = "UPDATE SETTINGS SET VALUE = ? WHERE KEY = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, String.valueOf(versionCode));
            stmt.setString(2, "actualVersionNumber");

            stmt.executeUpdate();
        }
    }

    static List<String> getEmailsWithoutApiCodeList() throws SQLException {
        List<String> addressList = new ArrayList<>();
        String sql = "SELECT * FROM API_KEYS WHERE api_key ISNULL";
        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                addressList.add(rs.getString("email"));
            }

            return addressList;

        }
    }

    static List<String> getApiCodesList() throws SQLException {
        List<String> apiCodesList = new ArrayList<>();
        String sql = "SELECT * FROM API_KEYS WHERE api_key NOT NULL";
        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                apiCodesList.add(rs.getString("api_key"));
            }

            return apiCodesList;
        }
    }

    static void setApiCode(String apiCode, String email) {

        String sql = "UPDATE API_KEYS SET api_key = ? WHERE email = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, apiCode);
            stmt.setString(2, email);

            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.warning(e.getMessage());
        }
    }

    public static User getUser(String apiKey) throws SQLException {
        String sql = "SELECT * FROM API_KEYS WHERE api_key = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, apiKey);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String roles = rs.getString("roles");
                List<String> rolesStringList = Arrays.asList(roles.split(","));
                List<SecurityUtils.Role> rolesList = new ArrayList<>();
                for (String s : rolesStringList) {
                    rolesList.add(SecurityUtils.resolveRole(s));
                }
                User user = new User(rs.getInt("id"), rs.getString("email"), rs.getString("api_key"),
                        rs.getString("name"), rs.getString("surname"), rolesList);

                return user;
            } else {
                return null;
            }
        }
    }

    public static void bumpUserAnalitycsField(User user) throws SQLException {
        String sql = "UPDATE USERS_ANALYTICS SET requestsAmount = requestsAmount + 1 WHERE api_key = ?";
        try (Connection connection = getConnectionToAnalyticsDB(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getApiKey());
            stmt.executeUpdate();
        }
    }
}

