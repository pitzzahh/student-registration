package com.pitzzahh.exception;

/**
 * class used for exception handling on adding student info.
 * This class extends the {@code RuntimeException}
 */
public class InvalidStudentNumberException extends RuntimeException {
    public InvalidStudentNumberException(String message) {
        super(String.format("INVALID STUDENT NUMBER: %s SHOULD BE 10 CHARACTERS LONG", message));
    }
}