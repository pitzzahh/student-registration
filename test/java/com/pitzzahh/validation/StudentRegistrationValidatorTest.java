package com.pitzzahh.validation;

import org.junit.jupiter.api.Test;
import com.pitzzahh.entity.Student;
import com.pitzzahh.database.DatabaseConnection;
import static org.junit.jupiter.api.Assertions.*;

class StudentRegistrationValidatorTest {

    private final Student underTest = new Student(
            2000263444,
            "Peter John Arao",
            (byte)19,
            "Buyoan, Legazpi City Albay",
            "Bachelor of Science in Information Technology"
    );

    @Test
    void shouldPassIfStudentAlreadyExist() {
        // given
        String studentNumberToFind = "2000263444";
        // when
        Object[] result = DatabaseConnection.search(new DatabaseConnection(), studentNumberToFind);
        // then
        assert result != null;
        assertEquals(underTest.toString(), new Student(
                Integer.parseInt(result[0].toString()),
                result[1].toString(),
                Byte.parseByte(result[2].toString()),
                result[3].toString(),
                result[4].toString())
                .toString()
        );
    }
}