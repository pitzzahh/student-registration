/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pitzzahh.database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.io.BufferedWriter;
import com.pitzzahh.view.Main;
import java.sql.PreparedStatement;
import java.util.function.Function;
import com.pitzzahh.validation.Validation;
import static com.pitzzahh.view.Main.PROMPT;
import com.pitzzahh.exception.StudentNotFoundException;
import com.pitzzahh.exception.StudentAlreadyExistsException;
import com.pitzzahh.exception.InvalidStudentNumberException;
import static com.pitzzahh.validation.Validation.getStudentNumber;

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
            preparedStatement.setInt(3, Integer.valueOf(age.trim()));
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
     * @param databaseConnection database connection object needed to connect to the database.
     * @param studentNumber the student number needed to look up for student row to be updated.
     * @param name the new name of the student.
     * @param age the new age of the student.
     * @param address the new address of the student.
     * @param course the new course of the student.
     */
    public static void updateStudent(
        DatabaseConnection databaseConnection, 
        String studentNumber, 
        String name,
        String age,
        String address,
        String course) {

        try {
            String studentNumberFromSelectedRow = String.valueOf(Main.table.getModel().getValueAt(Main.table.getSelectedRow(), 0));
            final String UPDATE_STATEMENT = "UPDATE students " + 
                                            "SET student_number = ?, name = ?, age = ?, address = ?, course = ? " +
                                            "WHERE student_number = " + "'" + studentNumberFromSelectedRow + "'";
            if(Validation.IS_STUDENT_NUMBER_VALID.negate().test(studentNumber.trim())) throw new InvalidStudentNumberException(studentNumber.trim());
            if(Validation.IS_STUDENT_NUMBER_EXISTS.negate().test(studentNumberFromSelectedRow, databaseConnection)) throw new StudentNotFoundException();
            PreparedStatement preparedStatement = databaseConnection.connect().prepareStatement(UPDATE_STATEMENT);
            preparedStatement.setString(1, studentNumber.trim());
            preparedStatement.setString(2, name.trim());
            preparedStatement.setInt(3, Integer.valueOf(age.trim()));
            preparedStatement.setString(4, address.trim());
            preparedStatement.setString(5, course.trim()); 
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
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
    public static final Function<String, String> GET_STUDENT_QUERY = studentNumber -> "SELECT * FROM students WHERE student_number = " + "'" + studentNumber + "'";
    
    public static void main(String[] args) {
        Object[] student = DatabaseConnection.search(Main.DATABASE_CONNECTION, "2000263444");
        System.out.println(student.toString());
    }
}
