package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.request.PhysicalTestMetricRequest;
import io.github.kiransr99.parg.dto.request.PhysicalTestMetricUpdateRequest;
import io.github.kiransr99.parg.dto.response.PhysicalTestMetricResponse;
import io.github.kiransr99.parg.entity.PhysicalTest;
import io.github.kiransr99.parg.entity.PhysicalTestMetric;
import io.github.kiransr99.parg.repository.PhysicalTestMetricRepository;
import io.github.kiransr99.parg.repository.PhysicalTestRepository;
import io.github.kiransr99.parg.service.PhysicalTestMetricService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhysicalTestMetricImpl implements PhysicalTestMetricService {
    private final PhysicalTestMetricRepository physicalTestMetricRepository;
    private final PhysicalTestRepository physicalTestRepository;
    @Override
    public PhysicalTestMetricResponse createPhysicalTestMetric(PhysicalTestMetricRequest physicalTestMetricRequest) {
        log.info("Creating physical test metric: {}", physicalTestMetricRequest);
        PhysicalTestMetric physicalTestMetric = new PhysicalTestMetric();
        PhysicalTest physicalTest = physicalTestRepository.findById(physicalTestMetricRequest.getPhysicalTestId()).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_TEST_NOT_FOUND)
        );
        physicalTestMetric.setPhysicalTest(physicalTest);
        physicalTestMetric.setName(physicalTestMetricRequest.getName());
        physicalTestMetric.setUnit(physicalTestMetricRequest.getUnit());
        physicalTestMetricRepository.save(physicalTestMetric);
        return new PhysicalTestMetricResponse(physicalTestMetric);
    }

    @Override
    public PhysicalTestMetricResponse getPhysicalTestMetric(Long physicalTestMetricId) {
        log.info("Fetching physical test metric with id: {}", physicalTestMetricId);
        PhysicalTestMetric physicalTestMetric = physicalTestMetricRepository.findById(physicalTestMetricId).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_TEST_METRIC_NOT_FOUND)
        );
        return new PhysicalTestMetricResponse(physicalTestMetric);
    }

    @Override
    public Page<PhysicalTestMetricResponse> getAllPhysicalTestMetrics(Pageable pageable) {
        log.info("Fetching all physical test metrics");
        return physicalTestMetricRepository.findAll(pageable).map(PhysicalTestMetricResponse::new);
    }

    @Override
    public PhysicalTestMetricResponse updatePhysicalTestMetric(Long physicalTestMetricId, PhysicalTestMetricUpdateRequest physicalTestMetricUpdateRequest) {
        log.info("Updating physical test metric with id: {}", physicalTestMetricId);
        PhysicalTestMetric physicalTestMetric = physicalTestMetricRepository.findById(physicalTestMetricId).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_TEST_METRIC_NOT_FOUND)
        );
        if(physicalTestMetricUpdateRequest.getPhysicalTestId() != null){
            PhysicalTest physicalTest = physicalTestRepository.findById(physicalTestMetricUpdateRequest.getPhysicalTestId()).orElseThrow(
                    () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_TEST_NOT_FOUND)
            );
            physicalTestMetric.setPhysicalTest(physicalTest);
        }
        physicalTestMetric.setName(physicalTestMetricUpdateRequest.getName());
        physicalTestMetric.setUnit(physicalTestMetricUpdateRequest.getUnit());
        physicalTestMetricRepository.save(physicalTestMetric);
        return new PhysicalTestMetricResponse(physicalTestMetric);
    }

    @Override
    public void deletePhysicalTestMetric(Long physicalTestMetricId) {
        log.info("Deleting physical test metric with id: {}", physicalTestMetricId);
        PhysicalTestMetric physicalTestMetric = physicalTestMetricRepository.findById(physicalTestMetricId).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_TEST_METRIC_NOT_FOUND)
        );
        physicalTestMetricRepository.delete(physicalTestMetric);
    }
}
