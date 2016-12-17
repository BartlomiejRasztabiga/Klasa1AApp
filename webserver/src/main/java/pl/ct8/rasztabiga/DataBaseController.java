package pl.ct8.rasztabiga;

import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import pl.ct8.rasztabiga.models.Exam;
import pl.ct8.rasztabiga.models.Student;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController {

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

    public static void createStudentsTable() {
        Connection connection = getConnection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
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
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createExamsTable() {
        Connection connection = getConnection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
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
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createSettingsTable() {
        Connection connection = getConnection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();

            String sql = "CREATE TABLE SETTINGS"
                    + " (KEY TEXT,"
                    + " VALUE TEXT)";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Couldn't create table");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void initializeSettingsTable() {
        Connection connection = getConnection();
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO SETTINGS (KEY, VALUE) " +
                    "VALUES (?, ?)";

            stmt = connection.prepareStatement(sql);

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
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addExam(Exam exam) {
        Connection connection = getConnection();
        PreparedStatement stmt = null;
        try {
            String sql = "INSERT INTO EXAMS (YEAR, MONTH, DAY, SUBJECT, DESCRIPTION, VISIBLE) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, exam.getYear());
            stmt.setInt(2, exam.getMonth());
            stmt.setInt(3, exam.getDay());
            stmt.setString(4, exam.getSubject());
            stmt.setString(5, exam.getDesc());
            stmt.setInt(6, 1);

            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addStudents(List<Student> studentList) {
        // connection
        Connection connection = getConnection();
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            // inserting students to database
            for (Student student : studentList) {
                int number = student.getNumber();
                String name = student.getName();
                String surname = student.getSurname();
                String sql = "INSERT INTO STUDENTS (NUMBER, NAME, SURNAME) " +
                        "VALUES (" + number + "," + "'" + name + "'" + "," + "'" + surname + "'" + ")";
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static List<Student> getStudents() {
        Connection connection = getConnection();
        Statement stmt = null;
        List<Student> studentList = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            //searching command
            String searchSQL = "SELECT * FROM STUDENTS";
            ResultSet rs = stmt.executeQuery(searchSQL);
            // reading students
            while (rs.next()) {
                Student student = new Student(rs.getString("name"), rs.getString("surname"), rs.getInt("number"));
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return studentList;
    }

    public static List<Exam> getExams() {
        Connection connection = getConnection();
        Statement stmt = null;
        List<Exam> examsList = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            String sql = "SELECT * FROM EXAMS WHERE VISIBLE = 1";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Exam exam = new Exam(rs.getString("SUBJECT"), rs.getString("DESCRIPTION"),
                        rs.getInt("YEAR"), rs.getInt("MONTH"), rs.getInt("DAY"));

                examsList.add(exam);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return examsList;
    }

}

