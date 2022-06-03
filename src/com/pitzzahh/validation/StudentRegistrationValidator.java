package com.pitzzahh.validation;

import java.util.regex.Pattern;
import java.util.function.Function;
import com.pitzzahh.entity.Student;
import com.pitzzahh.database.DatabaseConnection;
import static com.pitzzahh.database.Process.getStudentNumber;
import static com.pitzzahh.validation.StudentRegistrationValidator.*;
import static com.pitzzahh.validation.StudentRegistrationValidator.ValidationResult.*;

public interface StudentRegistrationValidator extends Function<Student, ValidationResult> {

    /**
     * Function that validates if the studentNumber is valid.
     * @return a {@code ValidationResult} whether it is a {@code SUCCESS} or {@code STUDENT_NUMBER_NOT_VALID}.
     */
    static StudentRegistrationValidator isStudentNumberValid() {
        return student -> Pattern.compile("^\\d{10}$")
                .matcher(String.valueOf(student.getStudentNumber()))
                .matches() ? SUCCESS : STUDENT_NUMBER_NOT_VALID;
    }

    /**
     * Function that validates if the student already exists.
     * @return a {@code ValidationResult} whether it is a {@code SUCCESS} or {@code STUDENT_ALREADY_EXIST}.
     */
    static StudentRegistrationValidator isStudentAlreadyExists(DatabaseConnection databaseConnection) {
        return student -> getStudentNumber(databaseConnection, String.valueOf(student.getStudentNumber()))
                .equals(String.valueOf(student.getStudentNumber())) ? SUCCESS : STUDENT_ALREADY_EXIST;
    }

    /**
     * Method that chains Function together.
     * @return a {@code ValidationResult} whether it is a {@code SUCCESS} or whatever the result of the chained Function.
     */
    default StudentRegistrationValidator and(StudentRegistrationValidator otherValidation) {
        return student -> this.apply(student).equals(SUCCESS) ? otherValidation.apply(student) : this.apply(student);
    }

    enum ValidationResult {
        SUCCESS,
        STUDENT_NUMBER_NOT_VALID,
        STUDENT_ALREADY_EXIST,
    }
}
