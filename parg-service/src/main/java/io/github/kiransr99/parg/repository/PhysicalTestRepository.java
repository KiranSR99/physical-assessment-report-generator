package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.PhysicalTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PhysicalTestRepository extends JpaRepository<PhysicalTest, Long> {
    List<PhysicalTest> findByClassesId(Long classId);

    Optional<PhysicalTest> findByName(String testName);
}
