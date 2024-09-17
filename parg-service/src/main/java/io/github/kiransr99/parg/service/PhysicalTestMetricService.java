package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.PhysicalTestMetricRequest;
import io.github.kiransr99.parg.dto.request.PhysicalTestMetricUpdateRequest;
import io.github.kiransr99.parg.dto.response.PhysicalTestMetricResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PhysicalTestMetricService {
    PhysicalTestMetricResponse createPhysicalTestMetric(PhysicalTestMetricRequest physicalTestMetricRequest);
    PhysicalTestMetricResponse getPhysicalTestMetric(Long physicalTestMetricId);
    Page<PhysicalTestMetricResponse> getAllPhysicalTestMetrics(Pageable pageable);
    PhysicalTestMetricResponse updatePhysicalTestMetric(Long physicalTestMetricId, PhysicalTestMetricUpdateRequest physicalTestMetricUpdateRequest);
    void deletePhysicalTestMetric(Long physicalTestMetricId);
}
