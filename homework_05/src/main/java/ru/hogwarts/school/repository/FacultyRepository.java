package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Collection<Faculty> findByColorLike(String color);

    Collection<Faculty> findAllByColorContainingIgnoreCaseOrNameContainingIgnoreCase(String color, String name);
}
