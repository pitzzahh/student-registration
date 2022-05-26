package com.pitzzahh.exception;

/**
 * class used for exception handling on blank response.
 * This class extends the {@code RuntimeException}
 */
public class BlankTextFieldsException extends RuntimeException {
    public BlankTextFieldsException() {
        super("PLEASE DON'T LEAVE ANYTHING BLANK");
    }
}