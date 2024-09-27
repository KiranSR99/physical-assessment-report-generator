package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.PhysicalTestPerformanceMetric;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhysicalTestPerformanceMetricRepository extends JpaRepository<PhysicalTestPerformanceMetric, Long> {
    Optional<PhysicalTestPerformanceMetric> findByPhysicalTestIdAndPhysicalReportId(Long id, Long id1);

    List<PhysicalTestPerformanceMetric> findByPhysicalReportId(Long id);
}
