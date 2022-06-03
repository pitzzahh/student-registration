package com.pitzzahh.exception;

/**
 * class used for exception handling if the table is empty
 * This class extends the {@code RuntimeException}
 */
public class EmptyTableException extends RuntimeException {

    public EmptyTableException() {
        super("TABLE IS EMPTY");
    }

    public EmptyTableException(String message) {
        super(message);
    }
}
