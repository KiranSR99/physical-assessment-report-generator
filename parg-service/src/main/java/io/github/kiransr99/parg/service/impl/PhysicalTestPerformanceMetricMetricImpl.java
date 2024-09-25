package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.request.PhysicalTestPerformanceMetricRequest;
import io.github.kiransr99.parg.dto.response.PhysicalTestPerformanceMetricResponse;
import io.github.kiransr99.parg.entity.PhysicalReport;
import io.github.kiransr99.parg.entity.PhysicalTest;
import io.github.kiransr99.parg.entity.PhysicalTestPerformanceMetric;
import io.github.kiransr99.parg.repository.PhysicalReportRepository;
import io.github.kiransr99.parg.repository.PhysicalTestPerformanceMetricRepository;
import io.github.kiransr99.parg.repository.PhysicalTestRepository;
import io.github.kiransr99.parg.service.PhysicalTestPerformanceMetricService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhysicalTestPerformanceMetricMetricImpl implements PhysicalTestPerformanceMetricService {
    private final PhysicalTestPerformanceMetricRepository physicalTestPerformanceMetricRepository;
    private final PhysicalReportRepository physicalReportRepository;
    private final PhysicalTestRepository physicalTestRepository;

    @Override
    public PhysicalTestPerformanceMetricResponse savePhysicalTestPerformance(PhysicalTestPerformanceMetricRequest request) {
        log.info("Creating Physical Test Performance: {}", request);
        PhysicalTest physicalTest = physicalTestRepository.findById(request.getPhysicalTestId()).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_TEST_NOT_FOUND)
        );
        PhysicalReport physicalReport = physicalReportRepository.findById(request.getPhysicalReportId()).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_REPORT_NOT_FOUND)
        );
        PhysicalTestPerformanceMetric physicalTestPerformanceMetric = PhysicalTestPerformanceMetric.builder()
                .value(request.getValue())
                .physicalTest(physicalTest)
                .physicalReport(physicalReport)
                .build();
        return new PhysicalTestPerformanceMetricResponse(physicalTestPerformanceMetricRepository.save(physicalTestPerformanceMetric));

    }

    @Override
    public List<PhysicalTestPerformanceMetricResponse> saveMultiplePhysicalTestPerformances(List<PhysicalTestPerformanceMetricRequest> requests) {
        log.info("Saving multiple Physical Test Performances: {}", requests);

        List<PhysicalTestPerformanceMetricResponse> responses = requests.stream().map(request -> {
            // Fetch the required PhysicalTest and PhysicalReport for each request
            PhysicalTest physicalTest = physicalTestRepository.findById(request.getPhysicalTestId()).orElseThrow(
                    () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_TEST_NOT_FOUND)
            );
            PhysicalReport physicalReport = physicalReportRepository.findById(request.getPhysicalReportId()).orElseThrow(
                    () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_REPORT_NOT_FOUND)
            );

            // Create a new PhysicalTestPerformanceMetric object for each request
            PhysicalTestPerformanceMetric physicalTestPerformanceMetric = PhysicalTestPerformanceMetric.builder()
                    .value(request.getValue())
                    .physicalTest(physicalTest)
                    .physicalReport(physicalReport)
                    .build();

            // Save the object and return its response
            return new PhysicalTestPerformanceMetricResponse(physicalTestPerformanceMetricRepository.save(physicalTestPerformanceMetric));
        }).toList();

        return responses;
    }

    @Override
    public PhysicalTestPerformanceMetricResponse updatePhysicalTestPerformance(Long physicalTestPerformanceId , PhysicalTestPerformanceMetricRequest request) {
        log.info("Updating Physical Test Performance: {}", request);
        PhysicalTestPerformanceMetric physicalTestPerformanceMetric = physicalTestPerformanceMetricRepository.findById(physicalTestPerformanceId).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_TEST_PERFORMANCE_NOT_FOUND)
        );
        physicalTestPerformanceMetric.setValue(request.getValue());
        return new PhysicalTestPerformanceMetricResponse(physicalTestPerformanceMetricRepository.save(physicalTestPerformanceMetric));
    }

    @Override
    public void deletePhysicalTestPerformance(Long id) {
        log.info("Deleting Physical Test Performance: {}", id);
        PhysicalTestPerformanceMetric physicalTestPerformanceMetric = physicalTestPerformanceMetricRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_TEST_PERFORMANCE_NOT_FOUND)
        );
        physicalTestPerformanceMetricRepository.delete(physicalTestPerformanceMetric);
    }

    @Override
    public PhysicalTestPerformanceMetricResponse getPhysicalTestPerformance(Long id) {
        log.info("Getting Physical Test Performance: {}", id);
        PhysicalTestPerformanceMetric physicalTestPerformanceMetric = physicalTestPerformanceMetricRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_TEST_PERFORMANCE_NOT_FOUND)
        );
        return new PhysicalTestPerformanceMetricResponse(physicalTestPerformanceMetric);
    }

    @Override
    public List<PhysicalTestPerformanceMetricResponse> getAllPhysicalTestPerformance() {
        log.info("Getting all Physical Test Performance");
        return physicalTestPerformanceMetricRepository.findAll().stream().map(PhysicalTestPerformanceMetricResponse::new).toList();
    }
}
