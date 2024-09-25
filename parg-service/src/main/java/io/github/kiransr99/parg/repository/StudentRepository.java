package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.Exam;
import io.github.kiransr99.parg.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
