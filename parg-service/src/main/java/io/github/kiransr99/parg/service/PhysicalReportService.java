package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.PhysicalReportRequest;
import io.github.kiransr99.parg.dto.request.PhysicalReportUpdateRequest;
import io.github.kiransr99.parg.dto.response.PhysicalReportResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PhysicalReportService {
    PhysicalReportResponse savePhysicalReport(PhysicalReportRequest physicalReportRequest);
    Page<PhysicalReportResponse> getAllPhysicalReport(Pageable pageable);
    PhysicalReportResponse getPhysicalReportById(Long id);
    PhysicalReportResponse updatePhysicalReport(Long id, PhysicalReportUpdateRequest physicalReportUpdateRequest);
    void deletePhysicalReport(Long id);
}
