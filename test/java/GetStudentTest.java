import com.pitzzahh.database.DatabaseConnection;

import com.pitzzahh.view.Main;
import org.junit.jupiter.api.Test;
import com.pitzzahh.entity.Student;

import java.time.LocalDate;
import java.util.Date;

import static com.pitzzahh.entity.SearchingType.SEARCH_BY_STUDENT_NUMBER_AND_STUDENT_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetStudentTest {
    private final DatabaseConnection UNDER_TEST = new DatabaseConnection();

    @Test
    void shouldGetStudentInfoUsingStudentNumberAndStudentName() {
        String studentNumber = "0488369088";
        String studentName = "Taryn Lent";
        String age = "30";
        String address = "247 Kingsford Crossing";
        LocalDate dateOfBirth = LocalDate.of(2000, 1, 1);
        String email = "tarynlent@gmail.com";
        String course = "Bachelor of Science in Information Technology";

        Student student = new Student(studentNumber, studentName, age, address, dateOfBirth ,email, course);

        UNDER_TEST.search(UNDER_TEST, student, SEARCH_BY_STUDENT_NUMBER_AND_STUDENT_NAME);

        System.out.println(student);
        System.out.println(Main.students.get(0).toString());

        assertEquals(student.toString(), Main.students.get(0).toString());
    }
}
