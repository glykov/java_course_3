package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable Long studentId) {
        Student foundStudent = studentService.getStudent(studentId);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @GetMapping("/age/{studentAge}")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@PathVariable Integer studentAge) {
        Collection<Student> result = studentService.getStudentsByAge(studentAge);
        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student addedStudent = studentService.addStudent(student);
        return ResponseEntity.ok(addedStudent);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student editedStudent = studentService.editStudent(student);
        if (editedStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(editedStudent);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity<Student> removeStudent(@PathVariable Long studentId) {
        Student removedStudent = studentService.removeStudent(studentId);
        if (removedStudent == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(removedStudent);
    }

}
