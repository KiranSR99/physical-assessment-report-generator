package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.PhysicalTestRequest;
import io.github.kiransr99.parg.dto.request.PhysicalTestUpdateRequest;
import io.github.kiransr99.parg.dto.response.PhysicalTestResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PhysicalTestService {
    PhysicalTestResponse createPhysicalTest(PhysicalTestRequest physicalTestRequest);
    PhysicalTestResponse getPhysicalTest(Long id);
    List<PhysicalTestResponse> getPhysicalTestsByClassId(Long classId);
    Page<PhysicalTestResponse> getAllPhysicalTests(Pageable pageable);
    PhysicalTestResponse updatePhysicalTest(Long id, PhysicalTestUpdateRequest physicalTestUpdateRequest);
    void deletePhysicalTest(Long id);
}
