package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.request.PhysicalTestRequest;
import io.github.kiransr99.parg.dto.request.PhysicalTestUpdateRequest;
import io.github.kiransr99.parg.dto.response.PhysicalTestResponse;
import io.github.kiransr99.parg.entity.PhysicalTest;
import io.github.kiransr99.parg.repository.PhysicalTestRepository;
import io.github.kiransr99.parg.service.PhysicalTestService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhysicalTestImpl implements PhysicalTestService {
    private final PhysicalTestRepository physicalTestRepository;
    @Override
    public PhysicalTestResponse createPhysicalTest(PhysicalTestRequest physicalTestRequest) {
        log.info("Creating physical test: {}", physicalTestRequest);
        PhysicalTest physicalTest = new PhysicalTest();
        physicalTest.setName(physicalTestRequest.getPhysicalTestName());
        physicalTest.setDescription(physicalTestRequest.getPhysicalTestDescription());
        physicalTest.setUnit(physicalTestRequest.getPhysicalTestUnit());
        physicalTestRepository.save(physicalTest);
        return new PhysicalTestResponse(physicalTest);
    }

    @Override
    public PhysicalTestResponse getPhysicalTest(Long id) {
        log.info("Fetching physical test with id: {}", id);
        PhysicalTest physicalTest = physicalTestRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_TEST_NOT_FOUND)
        );
        return new PhysicalTestResponse(physicalTest);
    }

    @Override
    public Page<PhysicalTestResponse> getAllPhysicalTests(Pageable pageable) {
        log.info("Fetching all physical tests");
        return physicalTestRepository.findAll(pageable).map(PhysicalTestResponse::new);
    }

    @Override
    public PhysicalTestResponse updatePhysicalTest(Long id, PhysicalTestUpdateRequest physicalTestUpdateRequest) {
        log.info("Updating physical test with id: {}", id);
        PhysicalTest physicalTest = physicalTestRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_TEST_NOT_FOUND)
        );
        physicalTest.setName(physicalTestUpdateRequest.getPhysicalTestName());
        physicalTest.setDescription(physicalTestUpdateRequest.getPhysicalTestDescription());
        physicalTest.setUnit(physicalTestUpdateRequest.getPhysicalTestUnit());
        physicalTestRepository.save(physicalTest);
        return new PhysicalTestResponse(physicalTest);
    }

    @Override
    public void deletePhysicalTest(Long id) {
        log.info("Deleting physical test with id: {}", id);
        PhysicalTest physicalTest = physicalTestRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_TEST_NOT_FOUND)
        );
        physicalTestRepository.delete(physicalTest);
    }
}
