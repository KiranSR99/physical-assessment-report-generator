package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    @Query("SELECT e FROM Exam e  WHERE e.school.schoolId = :schoolId")
    List<Exam> findAllExamBySchoolId(@Param("schoolId") Long schoolId);

}
