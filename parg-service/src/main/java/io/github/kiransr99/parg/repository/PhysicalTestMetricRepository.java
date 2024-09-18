package io.github.kiransr99.parg.repository;

import io.github.kiransr99.parg.entity.PhysicalTestMetric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhysicalTestMetricRepository extends JpaRepository<PhysicalTestMetric, Long> {
}
