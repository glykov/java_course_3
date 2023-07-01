package ru.hogwarts.school;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class StudentServiceTests {

    @Mock
    private StudentRepository repositoryMock;

	@InjectMocks
    StudentService out;

	@Test
	void addStudentTest() {
        Student s = new Student(1, "AAAA", 17, null);
		when(repositoryMock.save(s)).thenReturn(s);
		assertEquals(s, out.addStudent(s));
	}

	@Test
	void getStudentPositiveTest() {
        Student s = new Student(1L, "AAAA", 17, null);
        when(repositoryMock.findById(1L)).thenReturn(Optional.of(s));
        assertEquals(s, out.getStudent(1));
	}

	@Test
	void getStudentNegativeTest() {
        when(repositoryMock.findById(4L)).thenReturn(Optional.empty());
		assertThrows(NoSuchElementException.class, () -> out.getStudent(4));
	}

	@Test
	void editStudentTest() {
		Student s = new Student(1, "CCC", 35, null);
        when(repositoryMock.save(s)).thenReturn(s);
		assertEquals(s, out.editStudent(s));
	}

// Что-то не придумал как тестировать метод без возвращаемого значения
//	@Test
//	void removeStudentPositiveTest() {
//		Student s = new Student(3, "CCC", 30);
//		int size = out.getAll().size();
//		assertEquals(s, out.removeStudent(3));
//		assertEquals(size - 1, out.getAll().size());
//	}
//
//	@Test
//	void removeStudentNegativeTest() {
//		int size = out.getAll().size();
//		assertNull(out.removeStudent(4));
//		assertEquals(size, out.getAll().size());
//	}

	@Test
	void getStudentsByAgePositiveTest() {
		Student s = new Student(4,"DDD", 30, null);
        when(repositoryMock.findByAgeLessThan(31)).thenReturn(List.of(s));
		assertIterableEquals(List.of(s), out.getStudentsByAge(31));
	}

	@Test
	void getStudentsByAgeNegativeTest() {
		List<Student> test = Collections.emptyList();
        when(repositoryMock.findByAgeLessThan(31)).thenReturn(test);
		assertIterableEquals(test, out.getStudentsByAge(31));
	}

 }
