package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentEnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {
    List<StudentEnrollment> findByExamId(Long examId);
    List<StudentEnrollment> findByClassNameId(Long classId);

    Optional<StudentEnrollment> findByStudentId(Long studentId);
}
