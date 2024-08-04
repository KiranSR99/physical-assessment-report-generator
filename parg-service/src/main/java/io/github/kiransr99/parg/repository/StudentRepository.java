package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
