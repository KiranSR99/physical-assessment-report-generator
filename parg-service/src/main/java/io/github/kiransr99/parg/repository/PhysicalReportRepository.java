package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.PhysicalReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhysicalReportRepository extends JpaRepository<PhysicalReport, Long> {
    Optional<PhysicalReport> findByStudentEnrollmentId(Long id);
}
