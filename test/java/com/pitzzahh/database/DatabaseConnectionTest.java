//package com.pitzzahh.database;
//
//import com.pitzzahh.entity.Courses;
//import com.pitzzahh.entity.Student;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//class DatabaseConnectionTest {
//
//    private final Student UNDER_TEST = new Student(
//            "1234567890",
//            "Peter John Arao",
//            "19",
//            "Buyoan, Legazpi City Albay",
//            Courses.BSIT.getDescription()
//
//    );
//
//    @Test
//    void shouldPassIfStudentIsFoundUsingStudentNumber() {
//        // given
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        // when
//        DatabaseConnection.search(databaseConnection, UNDER_TEST, "SEARCH_BY_STUDENT_NUMBER");
//
//        assert result != null;
//        Student studentResult = new Student(
//                Integer.parseInt(String.valueOf(result[0])),
//                String.valueOf(result[1]),
//                Byte.parseByte(String.valueOf(result[2])),
//                String.valueOf(result[3]),
//                String.valueOf(result[4])
//        );
//
//        // then
//        assertEquals(UNDER_TEST.toString(), studentResult.toString());
//    }
//
//    @Test
//    void shouldPassIfStudentIsFoundUsingStudentName() {
//        // given
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        // when
//        Object[] result = DatabaseConnection.search(databaseConnection, UNDER_TEST, "SEARCH_BY_STUDENT_NAME");
//
//        assert result != null;
//        Student studentResult = new Student(
//                Integer.parseInt(String.valueOf(result[0])),
//                String.valueOf(result[1]),
//                Byte.parseByte(String.valueOf(result[2])),
//                String.valueOf(result[3]),
//                String.valueOf(result[4])
//        );
//
//        // then
//        assertEquals(UNDER_TEST.toString(), studentResult.toString());
//    }
//    @Test
//    void shouldPassIfStudentIsFoundUsingStudentAge() {
//        // given
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        // when
//        Object[] result = DatabaseConnection.search(databaseConnection, UNDER_TEST, "SEARCH_BY_STUDENT_AGE");
//
//        assert result != null;
//        Student studentResult = new Student(
//                Integer.parseInt(String.valueOf(result[0])),
//                String.valueOf(result[1]),
//                Byte.parseByte(String.valueOf(result[2])),
//                String.valueOf(result[3]),
//                String.valueOf(result[4])
//        );
//
//        // then
//        assertEquals(UNDER_TEST.toString(), studentResult.toString());
//    }
//
//    @Test
//    void shouldPassIfStudentIsFoundUsingStudentAddress() {
//        // given
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        // when
//        Object[] result = DatabaseConnection.search(databaseConnection, UNDER_TEST, "SEARCH_BY_STUDENT_ADDRESS");
//
//        assert result != null;
//        Student studentResult = new Student(
//                Integer.parseInt(String.valueOf(result[0])),
//                String.valueOf(result[1]),
//                Byte.parseByte(String.valueOf(result[2])),
//                String.valueOf(result[3]),
//                String.valueOf(result[4])
//        );
//
//        // then
//        assertEquals(UNDER_TEST.toString(), studentResult.toString());
//    }
//    @Test
//    void shouldPassIfStudentIsFoundUsingStudentCourse() {
//        // given
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        // when
//        Object[] result = DatabaseConnection.search(databaseConnection, UNDER_TEST, "SEARCH_BY_STUDENT_COURSE");
//
//        assert result != null;
//        Student studentResult = new Student(
//                Integer.parseInt(String.valueOf(result[0])),
//                String.valueOf(result[1]),
//                Byte.parseByte(String.valueOf(result[2])),
//                String.valueOf(result[3]),
//                String.valueOf(result[4])
//        );
//
//        // then
//        assertEquals(UNDER_TEST.toString(), studentResult.toString());
//    }
//}