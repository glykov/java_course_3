package ru.hogwarts.school;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collections;
import java.util.List;

public class FacultyServiceTests {
    FacultyService out;

    @BeforeEach
    void setupFaculties() {
        out = new FacultyService();
        out.addFaculty(new Faculty("AAA", "red"));
        out.addFaculty(new Faculty("BBB", "yellow"));
        out.addFaculty(new Faculty("CCC", "green"));
    }

    @Test
    void addFacultyTest() {
        int size = out.getAll().size();
        Faculty f = new Faculty(4,"DDD", "green");
        assertEquals(f, out.addFaculty(f));
        assertEquals(size + 1, out.getAll().size());
    }

    @Test
    void getFacultyPositiveTest() {
        assertEquals(new Faculty(3, "CCC", "green"), out.getFaculty(3));
    }

    @Test
    void getFacultyNegativeTest() {
        assertNull(out.getFaculty(4));
    }

    @Test
    void editFacultyPositiveTest() {
        Faculty f = new Faculty(3, "CCC", "black");
        int size = out.getAll().size();
        assertEquals(f, out.editFaculty(f));
        assertEquals(size, out.getAll().size());
    }

    @Test
    void editFacultyNegativeTest() {
        Faculty f = new Faculty(4, "CCC", "black");
        int size = out.getAll().size();
        assertNull(out.editFaculty(f));
        assertEquals(size, out.getAll().size());
    }

    @Test
    void removeFacultyPositiveTest() {
        Faculty f = new Faculty(3, "CCC", "green");
        int size = out.getAll().size();
        assertEquals(f, out.removeFaculty(3));
        assertEquals(size - 1, out.getAll().size());
    }

    @Test
    void removeFacultyNegativeTest() {
        int size = out.getAll().size();
        assertNull(out.removeFaculty(4));
        assertEquals(size, out.getAll().size());
    }

    @Test
    void getFacultiesByColorPositiveTest() {
        Faculty f = new Faculty(4,"DDD", "green");
        out.addFaculty(f);
        List<Faculty> test = List.of(new Faculty(3, "CCC", "green"), f);
        assertIterableEquals(test, out.getFacultiesByColor("green"));
    }

    @Test
    void getFacultiesByColorNegativeTest() {
        List<Faculty> test = Collections.emptyList();
        assertIterableEquals(test, out.getFacultiesByColor("black"));
    }

}
