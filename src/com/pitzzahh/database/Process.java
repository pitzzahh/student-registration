/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pitzzahh.database;

import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.io.IOException;
import java.sql.SQLException;
import java.io.BufferedWriter;
import com.pitzzahh.entity.Student;
import java.sql.PreparedStatement;
import java.util.function.Function;
import static com.pitzzahh.view.Main.PROMPT;
import com.pitzzahh.exception.StudentNotFoundException;
import com.pitzzahh.exception.StudentAlreadyExistsException;
import com.pitzzahh.exception.InvalidStudentNumberException;
import com.pitzzahh.validation.StudentRegistrationValidator;
import static com.pitzzahh.validation.StudentRegistrationValidator.ValidationResult.SUCCESS;
import static com.pitzzahh.validation.StudentRegistrationValidator.ValidationResult.STUDENT_DOES_NOT_EXIST;

/**
 *
 * @author peter
 */
public class Process {
    
    /**
     * Method that writes to a text file.
     * <p>It accepts a File object, and a string to be written in the text file</p>
     * @param file the File object needed to perform file operations.
     * @param whatToWrite the String representation that will be written into the text file.
     * @throws IOException if something goes wrong.
     */
    public static void writeToATextFile(File file, String whatToWrite) throws IOException {
        BufferedWriter bufferedWriter;
        bufferedWriter = new BufferedWriter(new FileWriter(file, true));
        bufferedWriter.write(whatToWrite);
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    /**
     * Method that returns the studentNumber of the student.
     * @param databaseConnection database connection needed to connect to the database.
     * @param studentNumber the studentNumber needed to get the studentNumber of the user. (checks if the studentNumber exists in the table)
     * @return {@code studentNumber} of the student from the table.
     */
    public static String getStudentNumber(DatabaseConnection databaseConnection, String studentNumber) {
        try {
            ResultSet resultSet = databaseConnection.connect().createStatement().executeQuery(Process.GET_STUDENT_NUMBER_QUERY.apply(studentNumber));
            if (resultSet.next()) return resultSet.getString("student_number");
        } catch (SQLException ignored) {
            throw new StudentNotFoundException();
        }
        return "0";
    }

     /**
     * Method that inserts data from the table.
     *
     * @param databaseConnection database connection needed to connect to the database.
     * @param studentNumber the username needed to insert in the table, user_name column in the table.
     * @param name the password needed to insert in the table, password column in the table.
     * @param age the age needed to insert in the table, age column in the table.
     * @param address the address needed to insert in the table, address column in the table.
     * @param course the course needed to insert in the table, course column in the table.
     * @throws StudentAlreadyExistsException if the student number already exists in the table, column student_number. (student number is the primary key, it means it cannot be duplicated).
     */
    public static void insertData (
        DatabaseConnection databaseConnection, 
        String studentNumber, 
        String name,
        String age,
        String address,
        String course) {
        final String INSERT_STATEMENT = "INSERT INTO students (student_number, name, age, address, course) VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement preparedStatement = databaseConnection.connect().prepareStatement(INSERT_STATEMENT);
            preparedStatement.setString(1, studentNumber);
            preparedStatement.setString(2, name);
            preparedStatement.setInt(3, Integer.parseInt(age.trim()));
            preparedStatement.setString(4, address);
            preparedStatement.setString(5, course);  
            
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            if (getStudentNumber(databaseConnection, studentNumber).equals(studentNumber)) throw new StudentAlreadyExistsException(studentNumber);
            PROMPT.show.accept(sqlException.getMessage(), true);
        }
    }
    
    /**
     * Method that removes a student from the table in the database.
     * @param databaseConnection database connection object needed to connect to the database.
     * @param studentNumber the student number needed to look up for student row removal.
     */
    public static void removeStudent(DatabaseConnection databaseConnection, String studentNumber) {
        final String DELETE_ROW_STATEMENT = "DELETE FROM students WHERE student_number = " + "'" + studentNumber + "'";
        try {
            PreparedStatement preparedStatement = databaseConnection.connect().prepareStatement(DELETE_ROW_STATEMENT);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            PROMPT.show.accept(sqlException.getMessage(), true);
        }
    }
    
    /**
     * Method that updates the student info from the table in the database.
     * Reads the selected row where the user right-clicked. Selects the selected row from the table and outputs
     * in the text fields ready to be modified.
     * @param databaseConnection database connection object needed to connect to the database.
     * @param newStudentUpdate the new {@code Student} to be updated into the table.
     * @param oldStudent the old {@code Student} to be checked if exists from the table.
     */
    public static void updateStudent(DatabaseConnection databaseConnection, Student newStudentUpdate, Student oldStudent) {
        try {
            final String UPDATE_STATEMENT = "UPDATE students " +
                                            "SET student_number = ?, " +
                                            "name = ?, " +
                                            "age = ?, " +
                                            "address = ?, " +
                                            "course = ? " +
                                            "WHERE student_number = " + "'" + oldStudent.getStudentNumber() + "'";

            if(StudentRegistrationValidator.isStudentNumberValid().apply(newStudentUpdate) != SUCCESS) throw new InvalidStudentNumberException(String.valueOf(newStudentUpdate.getStudentNumber()).trim());
            if(StudentRegistrationValidator.isStudentAlreadyExists(databaseConnection).apply(oldStudent) == STUDENT_DOES_NOT_EXIST) throw new StudentNotFoundException();

            PreparedStatement preparedStatement = databaseConnection.connect().prepareStatement(UPDATE_STATEMENT);
            preparedStatement.setString(1, String.valueOf(newStudentUpdate.getStudentNumber()).trim());
            preparedStatement.setString(2, newStudentUpdate.getName().trim());
            preparedStatement.setInt(3, Integer.parseInt(newStudentUpdate.getAge()));
            preparedStatement.setString(4, newStudentUpdate.getAddress().trim());
            preparedStatement.setString(5, newStudentUpdate.getCourse().trim());
            preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            PROMPT.show.accept(sqlException.getMessage(), true);
        }
    }

    
    /**
    * Function that returns a String query in getting the student number of a student from the table in the database.
    */
    public static final Function<String, String> GET_STUDENT_NUMBER_QUERY = studentNumber -> "SELECT student_number FROM students WHERE student_number = " + "'" + studentNumber + "'";
    
    /**
     * Function that returns a String query in getting the all the student info from the table in the database.
     */
    public static final Function<String, String> GET_STUDENT_QUERY_BY_STUDENT_NUMBER = studentNumber -> "SELECT * FROM students WHERE student_number = " + "'" + studentNumber + "'";

    /**
     * Function that returns a String query on getting student from the table using name.
     */
    public static Function<String, String>  GET_STUDENT_QUERY_BY_STUDENT_NAME = studentName -> "SELECT * FROM students WHERE name = " + "'" + studentName + "'";

    /**
     * Function that returns a String query on getting student from the table using age.
     */
    public static Function<String, String>  GET_STUDENT_QUERY_BY_STUDENT_AGE = studentAge -> "SELECT * FROM students WHERE age = " + "'" + studentAge + "'";

    /**
     * Function that returns a String query on getting student from the table using address.
     */
    public static Function<String, String>  GET_STUDENT_QUERY_BY_STUDENT_ADDRESS = studentAddress -> "SELECT * FROM students WHERE address = " + "'" + studentAddress + "'";

    /**
     * Function that returns a String query on getting student from the table using course.
     */
    public static Function<String, String>  GET_STUDENT_QUERY_BY_STUDENT_COURSE = studentCourse -> "SELECT * FROM students WHERE course = " + "'" + studentCourse + "'";

}
