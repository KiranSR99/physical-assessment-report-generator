package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.PhysicalReportRequest;
import io.github.kiransr99.parg.dto.request.PhysicalReportUpdateRequest;
import io.github.kiransr99.parg.dto.response.PhysicalReportResponse;
import io.github.kiransr99.parg.service.PhysicalReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(URL_CONSTANTS.PHYSICAL_REPORT_URL)
public class PhysicalReportController extends BaseController {
    private final PhysicalReportService physicalReportService;

    @PostMapping(URL_CONSTANTS.SAVE_PHYSICAL_REPORT)
    public ResponseEntity<GlobalApiResponse<PhysicalReportResponse>> savePhysicalReport(@Validated @RequestBody PhysicalReportRequest physicalReportRequest) {
        return successResponse(physicalReportService.savePhysicalReport(physicalReportRequest), SYSTEM_MESSAGE.PHYSICAL_REPORT_SAVED);
    }

    @GetMapping(URL_CONSTANTS.GET_PHYSICAL_REPORT_BY_ID)
    public ResponseEntity<GlobalApiResponse<PhysicalReportResponse>> getPhysicalReportById(@PathVariable Long physicalReportId) {
        return successResponse(physicalReportService.getPhysicalReportById(physicalReportId), SYSTEM_MESSAGE.PHYSICAL_REPORT_FETCHED_BY_ID);
    }

    @PutMapping(URL_CONSTANTS.UPDATE_PHYSICAL_REPORT)
    public ResponseEntity<GlobalApiResponse<PhysicalReportResponse>> updatePhysicalReport(@PathVariable Long physicalReportId, @Validated @RequestBody PhysicalReportUpdateRequest physicalReportUpdateRequest) {
        return successResponse(physicalReportService.updatePhysicalReport(physicalReportId, physicalReportUpdateRequest), SYSTEM_MESSAGE.PHYSICAL_REPORT_UPDATED);
    }

    @GetMapping(URL_CONSTANTS.GET_ALL_PHYSICAL_REPORTS)
    public ResponseEntity<GlobalApiResponse<Page<PhysicalReportResponse>>> getAllPhysicalReports(Pageable pageable) {
        return successResponse(physicalReportService.getAllPhysicalReport(pageable), SYSTEM_MESSAGE.PHYSICAL_REPORT_FETCHED);
    }

    @DeleteMapping(URL_CONSTANTS.DELETE_PHYSICAL_REPORT)
    public ResponseEntity<GlobalApiResponse<String>> deletePhysicalReport(@PathVariable Long physicalReportId) {
        physicalReportService.deletePhysicalReport(physicalReportId);
        return successResponse(SYSTEM_MESSAGE.PHYSICAL_REPORT_DELETED);
    }
}
