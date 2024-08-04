package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Integer> {
}
