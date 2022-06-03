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

import static com.pitzzahh.database.Process.getStudentNumber;

/**
 *
 * @author peter
 */
public class Validation {


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
