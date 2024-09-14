package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.Class;
import io.github.kiransr99.parg.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    List<Class> findBySchool(School school);
}
