package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.PhysicalTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PhysicalTestRepository extends JpaRepository<PhysicalTest, Long> {

    Optional<PhysicalTest> findByName(String testName);
}
