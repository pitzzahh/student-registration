package com.pitzzahh.exception;

/**
 * class used for exception handling if the student already exists.
 * This class extends the {@code RuntimeException}
 */
public class StudentAlreadyExistsException extends RuntimeException {
    public StudentAlreadyExistsException(String studentNumber) {
        super(String.format("STUDENT WITH STUDENT NUMBER: %s ALREADY EXISTS", studentNumber));
    }
}