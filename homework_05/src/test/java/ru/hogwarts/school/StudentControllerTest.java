package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class StudentControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    StudentRepository studentRepository;

    @SpyBean
    StudentService studentService;

    @InjectMocks
    StudentController studentController;

    @Test
    void getStudentPositiveTest() throws Exception {
        long id = 1;
        String name = "A";
        int age = 11;
        Student s1 = new Student(id, name, age);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(s1));

        mvc.perform(get("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

        verify(studentRepository, times(1)).findById(id);
    }

    @Test
    void getStudentNegativeTest() throws Exception {
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        mvc.perform(get("/student/42")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getStudentByAgePositiveTest() throws Exception {
        Student s1 = new Student(1, "A", 10);
        Student s2 = new Student(2, "B", 10);

        List<Student> list = List.of(s1, s2);

        when(studentRepository.findByAgeLessThan(anyInt())).thenReturn(list);

        mvc.perform(get("/student/age/10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(s1.getId()))
                .andExpect(jsonPath("$.[0].name").value(s1.getName()))
                .andExpect(jsonPath("$.[0].age").value(s1.getAge()))
                .andExpect(jsonPath("$.[1].id").value(s2.getId()))
                .andExpect(jsonPath("$.[1].name").value(s2.getName()))
                .andExpect(jsonPath("$.[1].age").value(s2.getAge()));

    }

    @Test
    void getStudentByAgeNegativeTest() throws Exception {
        when(studentRepository.findByAgeLessThan(anyInt())).thenReturn(Collections.emptyList());

        mvc.perform(get("/student/age/100500")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void addStudentTest() throws Exception {
        long id = 1;
        String name = "A";
        int age = 11;
        Student s = new Student(id, name, age);

        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("age", age);

        when(studentRepository.save(any(Student.class))).thenReturn(s);

        mvc.perform(post("/student")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jo.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    void editStudentPositiveTest() throws Exception {
        long id = 1;
        String name = "A";
        int age = 11;
        Student s = new Student(id, name, age);

        JSONObject jo = new JSONObject();
        jo.put("name", name);
        jo.put("age", age);

        when(studentRepository.save(any(Student.class))).thenReturn(s);

        mvc.perform(put("/student")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jo.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    void editStudentNegativeTest() throws Exception {
        when(studentRepository.save(any(Student.class))).thenReturn(null);

        mvc.perform(put("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void removeStudentTest() throws Exception {
        mvc.perform(delete("/student/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void findStudentsByAgeBetweenTest() throws Exception {
        Student s1 = new Student(1, "A", 10);
        Student s2 = new Student(2, "B", 20);

        List<Student> list = List.of(s1, s2);

        when(studentRepository.findStudentsByAgeBetween(anyInt(), anyInt())).thenReturn(list);

        mvc.perform(get("/student/age-between")
                        .param("minAge", "10")
                        .param("maxAge", "20")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(s1.getId()))
                .andExpect(jsonPath("$.[0].name").value(s1.getName()))
                .andExpect(jsonPath("$.[0].age").value(s1.getAge()))
                .andExpect(jsonPath("$.[1].id").value(s2.getId()))
                .andExpect(jsonPath("$.[1].name").value(s2.getName()))
                .andExpect(jsonPath("$.[1].age").value(s2.getAge()));
    }

    @Test
    void getFacultyTest() throws Exception {
        Faculty faculty = new Faculty(1, "Faculty", "green");
        Student student = new Student(1, "Student", 15);
        student.setFaculty(faculty);

        when(studentRepository.findById(anyLong())).thenReturn(Optional.of(student));

        mvc.perform(get("/student/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(faculty.getId()))
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }
}
