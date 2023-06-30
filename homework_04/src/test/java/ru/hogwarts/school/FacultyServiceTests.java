package ru.hogwarts.school;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FacultyServiceTests {

    @Mock
    private FacultyRepository repositoryMock;

    @InjectMocks
    FacultyService out;

    @Test
    void addFacultyTest() {
        Faculty f = new Faculty(4,"DDD", "green");
        when(repositoryMock.save(f)).thenReturn(f);
        assertEquals(f, out.addFaculty(f));
    }

    @Test
    void getFacultyPositiveTest() {
        Faculty f = new Faculty(4,"DDD", "green");
        when(repositoryMock.findById(4L)).thenReturn(Optional.of(f));
        assertEquals(f, out.getFaculty(4));
    }

    @Test
    void getFacultyNegativeTest() {
        when(repositoryMock.findById(4L)).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, () -> out.getFaculty(4));
    }

    @Test
    void editFacultyTest() {
        Faculty f = new Faculty(3, "CCC", "black");
        when(repositoryMock.save(f)).thenReturn(f);
        assertEquals(f, out.editFaculty(f));
    }

    // Что-то не придумал как тестировать метод без возвращаемого значения
//    @Test
//    void removeFacultyPositiveTest() {
//        Faculty f = new Faculty(3, "CCC", "green");
//        int size = out.getAll().size();
//        assertEquals(f, out.removeFaculty(3));
//        assertEquals(size - 1, out.getAll().size());
//    }
//
//    @Test
//    void removeFacultyNegativeTest() {
//        int size = out.getAll().size();
//        assertNull(out.removeFaculty(4));
//        assertEquals(size, out.getAll().size());
//    }

    @Test
    void getFacultiesByColorPositiveTest() {
        Faculty f = new Faculty(4,"DDD", "green");
        when(repositoryMock.findByColorLike("green")).thenReturn(List.of(f));
        assertIterableEquals(List.of(f), out.getFacultiesByColor("green"));
    }

    @Test
    void getFacultiesByColorNegativeTest() {
        List<Faculty> test = Collections.emptyList();
        when(repositoryMock.findByColorLike("black")).thenReturn(test);
        assertIterableEquals(test, out.getFacultiesByColor("black"));
    }

}
