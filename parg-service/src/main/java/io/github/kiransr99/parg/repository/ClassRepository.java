package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.Class;
import io.github.kiransr99.parg.entity.Exam;
import io.github.kiransr99.parg.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    List<Class> findByStatusTrue();
    List<Class> findBySchoolAndStatusTrue(School school);
    List<Class> findByExamAndStatusTrue(Exam exam);
    Class findByName(String name);

    Optional<Class> findByNameAndSchool(String name, School school);
}
