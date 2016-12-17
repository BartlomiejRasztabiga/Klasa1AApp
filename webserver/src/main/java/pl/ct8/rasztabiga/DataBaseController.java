package pl.ct8.rasztabiga;

import pl.ct8.rasztabiga.models.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DataBaseController {

    private static final String dataBase = "heh";

    private static Connection connect() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:test.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Connected");
        return c;
    }

    public static void createStudentsTable() {
        Connection connection = connect();
        Statement stmt;
        try {
            stmt = connection.createStatement();
            // creating table
            String tabelaSQL = "CREATE TABLE STUDENTS"
                    + " (NUMBER INTEGER PRIMARY KEY NOT NULL,"
                    + " NAME    TEXT    NOT NULL, "
                    + " SURNAME TEXT    NOT NULL)";
            // executing
            stmt.executeUpdate(tabelaSQL);
            // closing connection
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println("Nie mogę stworzyć tabeli" + e.getMessage());
        }
    }

    public static void addStudents(List<Student> studentList) {
        // connection
        Connection connection = connect();
        Statement stmt;
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
            stmt.close();
            connection.close();
            System.out.println("Chyba sie udalo xD");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public static void getStudents(List<Student> studentList) {
        Connection connection = connect();
        Statement stmt;
        try{
            stmt = connection.createStatement();
            // reading students
            while(true){
                
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

