package ru.hogwarts.school;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collections;
import java.util.List;

class StudentServiceTests {

	StudentService out;

	@BeforeEach
	void setupStudents() {
		out = new StudentService();
		out.addStudent(new Student("AAA", 10));
		out.addStudent(new Student("BBB", 20));
		out.addStudent(new Student("CCC", 30));
	}

	@Test
	void addStudentTest() {
		int size = out.getAll().size();
		Student s = new Student(4,"DDD", 40);
		assertEquals(s, out.addStudent(s));
		assertEquals(size + 1, out.getAll().size());
	}

	@Test
	void getStudentPositiveTest() {
		assertEquals(new Student(3, "CCC", 30), out.getStudent(3));
	}

	@Test
	void getStudentNegativeTest() {
		assertNull(out.getStudent(4));
	}

	@Test
	void editStudentPositiveTest() {
		Student s = new Student(3, "CCC", 35);
		int size = out.getAll().size();
		assertEquals(s, out.editStudent(s));
		assertEquals(size, out.getAll().size());
	}

	@Test
	void editStudentNegativeTest() {
		Student s = new Student(4, "CCC", 35);
		int size = out.getAll().size();
		assertNull(out.editStudent(s));
		assertEquals(size, out.getAll().size());
	}

	@Test
	void removeStudentPositiveTest() {
		Student s = new Student(3, "CCC", 30);
		int size = out.getAll().size();
		assertEquals(s, out.removeStudent(3));
		assertEquals(size - 1, out.getAll().size());
	}

	@Test
	void removeStudentNegativeTest() {
		int size = out.getAll().size();
		assertNull(out.removeStudent(4));
		assertEquals(size, out.getAll().size());
	}

	@Test
	void getStudentsByAgePositiveTest() {
		Student s = new Student(4,"DDD", 30);
		out.addStudent(s);
		List<Student> test = List.of(new Student(3, "CCC", 30), s);
		assertIterableEquals(test, out.getStudentsByAge(30));
	}

	@Test
	void getStudentsByAgeNegativeTest() {
		List<Student> test = Collections.emptyList();
		assertIterableEquals(test, out.getStudentsByAge(40));
	}

 }
