package com.pitzzahh;

import com.pitzzahh.view.Main;
import com.pitzzahh.entity.Courses;
import com.pitzzahh.entity.Student;
import com.pitzzahh.database.DatabaseConnection;

import static com.pitzzahh.entity.SearchingType.SEARCH_BY_STUDENT_NAME;

public class Test {
    public static void main(String[] args) {
        final Student UNDER_TEST = new Student(
                "2000263444",
                "Peter John Arao",
                "19",
                "Legazpi City",
                Courses.BSIT.getDescription()
        );

        DatabaseConnection.search(new DatabaseConnection(), UNDER_TEST, SEARCH_BY_STUDENT_NAME);
        Main.students.forEach(System.out::println);
    }
}
