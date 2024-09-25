package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.PhysicalTestPerformanceMetricRequest;
import io.github.kiransr99.parg.dto.response.PhysicalTestPerformanceMetricResponse;

import java.util.List;

public interface PhysicalTestPerformanceMetricService {
    PhysicalTestPerformanceMetricResponse savePhysicalTestPerformance(PhysicalTestPerformanceMetricRequest request);

    List<PhysicalTestPerformanceMetricResponse> saveMultiplePhysicalTestPerformances(List<PhysicalTestPerformanceMetricRequest> requests);

    PhysicalTestPerformanceMetricResponse updatePhysicalTestPerformance(Long physicalTestPerformanceMetricId, PhysicalTestPerformanceMetricRequest request);

    void deletePhysicalTestPerformance(Long id);

    PhysicalTestPerformanceMetricResponse getPhysicalTestPerformance(Long id);

    List<PhysicalTestPerformanceMetricResponse> getAllPhysicalTestPerformance();
}
