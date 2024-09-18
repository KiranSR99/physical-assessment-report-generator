package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.PhysicalTestMetricRequest;
import io.github.kiransr99.parg.dto.request.PhysicalTestMetricUpdateRequest;
import io.github.kiransr99.parg.dto.response.PhysicalTestMetricResponse;
import io.github.kiransr99.parg.service.PhysicalTestMetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(URL_CONSTANTS.PHYSICAL_TEST_METRIC_URL)
public class PhysicalTestMetricController extends BaseController {
    private final PhysicalTestMetricService physicalTestMetricService;
    @PostMapping(URL_CONSTANTS.SAVE_PHYSICAL_TEST_METRIC)
    public ResponseEntity<GlobalApiResponse<PhysicalTestMetricResponse>> savePhysicalTestMetric(@Validated @RequestBody PhysicalTestMetricRequest physicalTestMetricRequest) {
        return successResponse(physicalTestMetricService.createPhysicalTestMetric(physicalTestMetricRequest), SYSTEM_MESSAGE.PHYSICAL_TEST_METRIC_SAVED);
    }
    @GetMapping(URL_CONSTANTS.GET_PHYSICAL_TEST_METRIC_BY_ID)
    public ResponseEntity<GlobalApiResponse<PhysicalTestMetricResponse>> getPhysicalTestMetric(@PathVariable Long physicalTestMetricId) {
        return successResponse(physicalTestMetricService.getPhysicalTestMetric(physicalTestMetricId), SYSTEM_MESSAGE.PHYSICAL_TEST_METRIC_FETCHED_BY_ID);
    }
    @GetMapping(URL_CONSTANTS.GET_ALL_PHYSICAL_TEST_METRICS)
    public ResponseEntity<GlobalApiResponse<Page<PhysicalTestMetricResponse>>> getAllPhysicalTestMetrics(Pageable pageable) {
        return successResponse(physicalTestMetricService.getAllPhysicalTestMetrics(pageable), SYSTEM_MESSAGE.PHYSICAL_TEST_METRIC_FETCHED);
    }
    @PutMapping(URL_CONSTANTS.UPDATE_PHYSICAL_TEST_METRIC)
    public ResponseEntity<GlobalApiResponse<PhysicalTestMetricResponse>> updatePhysicalTestMetric(@PathVariable Long physicalTestMetricId, @Validated @RequestBody PhysicalTestMetricUpdateRequest physicalTestMetricUpdateRequest) {
        return successResponse(physicalTestMetricService.updatePhysicalTestMetric(physicalTestMetricId, physicalTestMetricUpdateRequest), SYSTEM_MESSAGE.PHYSICAL_TEST_METRIC_UPDATED);
    }
    @DeleteMapping(URL_CONSTANTS.DELETE_PHYSICAL_TEST_METRIC)
    public ResponseEntity<GlobalApiResponse<String>> deletePhysicalTestMetric(@PathVariable Long physicalTestMetricId) {
        physicalTestMetricService.deletePhysicalTestMetric(physicalTestMetricId);
        return successResponse(SYSTEM_MESSAGE.PHYSICAL_TEST_METRIC_DELETED);
    }
}
