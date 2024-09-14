package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    List<School> findByStatusTrue();
}
