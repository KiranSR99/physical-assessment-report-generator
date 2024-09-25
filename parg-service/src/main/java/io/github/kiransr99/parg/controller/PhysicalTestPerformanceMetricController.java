package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.PhysicalTestPerformanceMetricRequest;
import io.github.kiransr99.parg.dto.response.PhysicalTestPerformanceMetricResponse;
import io.github.kiransr99.parg.service.PhysicalTestPerformanceMetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(URL_CONSTANTS.PHYSICAL_TEST_PERFORMANCE_METRIC_URL)
@RequiredArgsConstructor
public class PhysicalTestPerformanceMetricController extends BaseController {

    private final PhysicalTestPerformanceMetricService physicalTestPerformanceMetricService;

    @PostMapping(URL_CONSTANTS.SAVE_PHYSICAL_TEST_PERFORMANCE_METRIC)
    public ResponseEntity<GlobalApiResponse<PhysicalTestPerformanceMetricResponse>> savePhysicalTestPerformanceMetric(@RequestBody PhysicalTestPerformanceMetricRequest request) {
        return successResponse(physicalTestPerformanceMetricService.savePhysicalTestPerformance(request), SYSTEM_MESSAGE.PHYSICAL_TEST_PERFORMANCE_SAVED);
    }

    @PostMapping(URL_CONSTANTS.SAVE_MULTIPLE_PHYSICAL_TEST_PERFORMANCE_METRICS)
    public ResponseEntity<GlobalApiResponse<List<PhysicalTestPerformanceMetricResponse>>> saveMultiplePhysicalTestPerformanceMetrics(@RequestBody List<PhysicalTestPerformanceMetricRequest> requests) {
        return successResponse(physicalTestPerformanceMetricService.saveMultiplePhysicalTestPerformances(requests), SYSTEM_MESSAGE.PHYSICAL_TEST_PERFORMANCE_SAVED);
    }

    @PutMapping(URL_CONSTANTS.UPDATE_PHYSICAL_TEST_PERFORMANCE_METRIC)
    public ResponseEntity<GlobalApiResponse<PhysicalTestPerformanceMetricResponse>> updatePhysicalTestPerformanceMetric(@PathVariable Long physicalTestPerformanceMetricId,@RequestBody PhysicalTestPerformanceMetricRequest request) {
        return successResponse(physicalTestPerformanceMetricService.updatePhysicalTestPerformance(physicalTestPerformanceMetricId, request), SYSTEM_MESSAGE.PHYSICAL_TEST_PERFORMANCE_UPDATED);
    }

    @DeleteMapping(URL_CONSTANTS.DELETE_PHYSICAL_TEST_PERFORMANCE_METRIC)
    public ResponseEntity<GlobalApiResponse<String>> deletePhysicalTestPerformanceMetric(@PathVariable Long physicalTestPerformanceMetricId) {
        physicalTestPerformanceMetricService.deletePhysicalTestPerformance(physicalTestPerformanceMetricId);
        return successResponse(SYSTEM_MESSAGE.PHYSICAL_TEST_PERFORMANCE_DELETED);
    }

    @GetMapping(URL_CONSTANTS.GET_PHYSICAL_TEST_PERFORMANCE_METRIC_BY_ID)
    public ResponseEntity<GlobalApiResponse<PhysicalTestPerformanceMetricResponse>> getPhysicalTestPerformanceMetricById(@PathVariable Long physicalTestPerformanceMetricId) {
        return successResponse(physicalTestPerformanceMetricService.getPhysicalTestPerformance(physicalTestPerformanceMetricId), SYSTEM_MESSAGE.PHYSICAL_TEST_PERFORMANCE_FETCHED_BY_ID);
    }

    @GetMapping(URL_CONSTANTS.GET_ALL_PHYSICAL_TEST_PERFORMANCE_METRICS)
    public ResponseEntity<GlobalApiResponse<List<PhysicalTestPerformanceMetricResponse>>> getAllPhysicalTestPerformanceMetric() {
        return successResponse(physicalTestPerformanceMetricService.getAllPhysicalTestPerformance(), SYSTEM_MESSAGE.PHYSICAL_TEST_PERFORMANCE_FETCHED);
    }
}
