package com.pitzzahh.validation;

import com.pitzzahh.view.Main;
import org.junit.jupiter.api.Test;
import com.pitzzahh.entity.Student;
import com.pitzzahh.entity.Courses;
import com.pitzzahh.database.DatabaseConnection;
import static org.junit.jupiter.api.Assertions.*;
import static com.pitzzahh.entity.SearchingType.SEARCH_BY_STUDENT_NUMBER;

class StudentRegistrationValidatorTest {

    @Test
    void shouldPassIfStudentAlreadyExist() {
        // given
        Student student = new Student(
                "2000263444",
                "Peter John Arao",
                "19",
                "Legazpi City",
                Courses.BSIT.getDescription()
        );
        // when
        DatabaseConnection.search(new DatabaseConnection(), student, SEARCH_BY_STUDENT_NUMBER);
        // then

        assertEquals(student.toString(), Main.students.get(0).toString());
    }
}