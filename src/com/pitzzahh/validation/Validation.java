/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pitzzahh.validation;

import com.pitzzahh.exception.StudentNotFoundException;
import com.pitzzahh.database.DatabaseConnection;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import com.pitzzahh.database.Process;
import java.util.regex.Pattern;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author peter
 */
public class Validation {
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
     * Function that validates if the student exists on the table from the database.
     */
    public static final BiPredicate<String, DatabaseConnection> IS_STUDENT_NUMBER_EXISTS = (studentNumber, databaseConnection) -> getStudentNumber(databaseConnection, studentNumber).equals(studentNumber);
    
    /**
     * Function that validates if the student number that is tested is valid.
     * Valid student number should be 10 characters long with no spaces
     */
    public static final Predicate<String> IS_STUDENT_NUMBER_VALID = student_number -> Pattern.compile("^\\d{10}$").matcher(student_number).matches();
    
}
