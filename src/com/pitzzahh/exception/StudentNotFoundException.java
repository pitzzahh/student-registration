package com.pitzzahh.exception;

/**
 * class used for exception handling if the student is not found.
 * This class extends the {@code RuntimeException}
 */
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException() {
        super("STUDENT DOES NOT EXIST");
    }
}