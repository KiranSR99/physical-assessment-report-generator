package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.Exam;
import io.github.kiransr99.parg.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "SELECT s.id, se.roll_number, s.name, c.name AS class_name, se.section, s.date_of_birth, s.age, s.gender, " +
            "pr.height, pr.weight, pr.bmi, pr.bmi_level, pr.percentile, pr.comment, pt.name AS physical_test_name, ptm.value AS physical_test_value " +
            "FROM student_enrollment se " +
            "JOIN student s ON se.student_id = s.id " +
            "JOIN class c ON se.class_id = c.id " +
            "LEFT JOIN physical_report pr ON se.id = pr.student_enrollment_id " +
            "LEFT JOIN physical_test_performance_metric ptm ON pr.id = ptm.physical_report_id " +
            "LEFT JOIN physical_test pt ON ptm.physical_test_id = pt.id " +
            "WHERE c.id = :classId",
            nativeQuery = true)
    List<Object[]> findStudentCompleteDataByClassId(@Param("classId") Long classId);


}
