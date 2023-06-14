package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();

    private long counter = 0L;

    public Student addStudent(Student student) {
        student.setId(++counter);
        students.put(counter, student);
        return student;
    }

    public Student getStudent(long id) {
        return students.get(id);
    }

    public Student editStudent(Student student) {
        if (students.containsKey(student.getId())) {
            students.put(student.getId(), student);
            return student;
        }
        return null;
    }

    public Student removeStudent(long id) {
        return students.remove(id);
    }

    public Collection<Student> getStudentsByAge(int age) {
        return students.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    public Collection<Student> getAll() {
        return students.values();
    }
}
