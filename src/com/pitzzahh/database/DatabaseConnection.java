/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pitzzahh.database;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import com.pitzzahh.view.Main;
import java.sql.ResultSetMetaData;
import java.util.function.Consumer;
import java.util.function.Function;
import com.pitzzahh.entity.Student;
import com.pitzzahh.entity.SearchingType;
import com.pitzzahh.exception.StudentNotFoundException;
import com.pitzzahh.validation.StudentRegistrationValidator;
import com.pitzzahh.exception.InvalidStudentNumberException;
import static com.pitzzahh.validation.StudentRegistrationValidator.ValidationResult.*;

/**
 *
 * @author peter
 */
public class DatabaseConnection {
    private static String TABLE_NAME;
    private static final String CREATE_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS students (" +
                                                          "student_number VARCHAR(10) NOT NULL PRIMARY KEY," +
                                                          "name VARCHAR(50) NULL, " +
                                                          "age INT NOT NULL," +
                                                          "address VARCHAR(150) NOT NULL," +
                                                          "course VARCHAR(100) NOT NULL);";

    /**
     * public Constructor.
     * This creates a connection to the database.
     */
    public DatabaseConnection() {
        try {
            Consumer<Connection> createTable = connection -> {
                try {
                    TABLE_NAME = getTableName();
                    connection.prepareStatement(CREATE_TABLE_STATEMENT).executeUpdate();
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            };
            createTable.accept(connect());
            System.out.println("Connected to the PostgreSQL Server Successfully.");
        } catch (RuntimeException runtimeException) {
            System.out.println(runtimeException.getMessage());
        }
    }

    /**
     * Method that connects to the PostgreSQL database.
     * @return a Connection object.
     */
    public final Connection connect() {
        Connection connection = null;
        try {
            final String DATABASE = "peter";
            final String USERNAME = "peter";
            final String PASSWORD = "!Password123";
            final String URL = "jdbc:postgresql://localhost:5432/" + DATABASE;
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException sqlException) {
            Main.PROMPT.show.accept(sqlException.getMessage(), true);
        }
        return connection;
    }

    /**
     * Function that returns a query based on the given parameter table.
     */
    private static final Function<String, String> GET_ALL_DATA_QUERY = tableName -> "SELECT * FROM " + tableName;
    
    /**
     * Function that returns a query based on the given parameter table.
     */
    public static final Function<String, String> GET_ROW_QUERY = studentNumber -> "SELECT * FROM students WHERE student_number = " + "'" + studentNumber + "'";

    /**
     * Method that gets the table name from the {@code CREATE_TABLE_STATEMENT}
     *
     * @return the name of the table in the database.
     */
    private static String getTableName() {
        int[] subStrings = {DatabaseConnection.CREATE_TABLE_STATEMENT.indexOf("EXISTS") + 6, DatabaseConnection.CREATE_TABLE_STATEMENT.indexOf("(") - 1};
        return DatabaseConnection.CREATE_TABLE_STATEMENT.substring(subStrings[0], subStrings[1]).trim();
    }

    /**
     * Method that gets all the columns in the table.
     * @return {@code String[]} containing all the columns of the table.
     */
    public String[] getAllTableColumnName() {
        try {
            System.out.println("List of column names in the current table: " + TABLE_NAME);
            ResultSetMetaData resultSetMetaData = connect().createStatement().executeQuery(GET_ALL_DATA_QUERY.apply(TABLE_NAME)).getMetaData();
            int count = resultSetMetaData.getColumnCount();
            String[] columns = new String[count];
            for(int i = 1; i <= count; i++) {
                columns[i - 1] = resultSetMetaData.getColumnName(i);
            }
            return columns;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Method that gets all the data from the table.
     */
    public void getAllData() {
        try {
            ResultSet resultSet = connect().createStatement().executeQuery(GET_ALL_DATA_QUERY.apply(TABLE_NAME));
            while (resultSet.next()) {
                Main.students.add(
                        new Student(
                                resultSet.getString("student_number"),
                                resultSet.getString("name"),
                                resultSet.getString("age"),
                                resultSet.getString("address"),
                                resultSet.getString("course"))
                );
            }
        } catch (SQLException ignored) {
            throw new StudentNotFoundException();
        }
    }

    /**
     * Method that gets a student from the table.
     * @param studentNumber the student number needed to search in the table.
     * @return Student object.
     */
    public Student getStudent(String studentNumber) {
        try {
            ResultSet resultSet = connect().createStatement().executeQuery(GET_ROW_QUERY.apply(studentNumber));
            if (resultSet.next()) {
                return new Student(
                        resultSet.getString("student_number"),
                        resultSet.getString("name"),
                        resultSet.getString("age"),
                        resultSet.getString("address"),
                        resultSet.getString("course")
                );
            }
        } catch (SQLException sqlException) {
            throw new StudentNotFoundException();
        }
        return null;
    }
    
    /**
     * Method that search the table from the database and 
     * @param databaseConnection database connection object needed to connect to the database.
     * @param student the student needed to look up for student row to be fetched.
     */
    public static void search(DatabaseConnection databaseConnection, Student student, SearchingType searchingType) {
        try {
            ResultSet resultSet = null;
            switch (searchingType) {
                case SEARCH_BY_STUDENT_NUMBER :
                    if(StudentRegistrationValidator.isStudentNumberValid().apply(student) != SUCCESS) throw new InvalidStudentNumberException(String.valueOf(student.getStudentNumber()));
                    resultSet = databaseConnection.connect().createStatement().executeQuery(Process.GET_STUDENT_QUERY_BY_STUDENT_NUMBER.apply(String.valueOf(student.getStudentNumber())));
                    break;
                case SEARCH_BY_STUDENT_NAME :
                    resultSet = databaseConnection.connect().createStatement().executeQuery(Process.GET_STUDENT_QUERY_BY_STUDENT_NAME.apply(String.valueOf(student.getName())));
                    break;
                case SEARCH_BY_STUDENT_AGE :
                    resultSet = databaseConnection.connect().createStatement().executeQuery(Process.GET_STUDENT_QUERY_BY_STUDENT_AGE.apply(String.valueOf(student.getAge())));
                    break;
                case SEARCH_BY_STUDENT_ADDRESS :
                    resultSet = databaseConnection.connect().createStatement().executeQuery(Process.GET_STUDENT_QUERY_BY_STUDENT_ADDRESS.apply(String.valueOf(student.getAddress())));
                    break;
                case SEARCH_BY_STUDENT_COURSE :
                    resultSet = databaseConnection.connect().createStatement().executeQuery(Process.GET_STUDENT_QUERY_BY_STUDENT_COURSE.apply(String.valueOf(student.getCourse())));
                    break;
            }
            assert resultSet != null;
            while (resultSet.next()) {
                Main.students.add(
                        new Student(
                                resultSet.getString("student_number"),
                                resultSet.getString("name"),
                                resultSet.getString("age"),
                                resultSet.getString("address"),
                                resultSet.getString("course")
                ));
            }
            if (Main.students.isEmpty()) throw new StudentNotFoundException();

        } catch (SQLException sqlException) {
            if(StudentRegistrationValidator.isStudentAlreadyExists(databaseConnection).apply(student) == STUDENT_DOES_NOT_EXIST) throw new StudentNotFoundException();
            Main.PROMPT.show.accept(sqlException.getMessage(), true);
        }
    }
}
