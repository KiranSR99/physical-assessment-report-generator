package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<School, Long> {
    List<School> findByStatusTrue();
}
