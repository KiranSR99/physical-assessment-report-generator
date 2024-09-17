package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.PhysicalTestRequest;
import io.github.kiransr99.parg.dto.request.PhysicalTestUpdateRequest;
import io.github.kiransr99.parg.dto.response.PhysicalTestResponse;
import io.github.kiransr99.parg.service.PhysicalTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(URL_CONSTANTS.PHYSICAL_TEST_URL)
public class PhysicalTestController extends BaseController {
    private final PhysicalTestService physicalTestService;
    @PostMapping(URL_CONSTANTS.SAVE_PHYSICAL_TEST)
    public ResponseEntity<GlobalApiResponse<PhysicalTestResponse>> createPhysicalTest(@RequestBody PhysicalTestRequest physicalTestRequest) {
        return successResponse(physicalTestService.createPhysicalTest(physicalTestRequest), SYSTEM_MESSAGE.PHYSICAL_TEST_SAVED);
    }

    @GetMapping(URL_CONSTANTS.GET_PHYSICAL_TEST_BY_ID)
    public ResponseEntity<GlobalApiResponse<PhysicalTestResponse>> getPhysicalTest(@PathVariable Long physicalTestId) {
        return successResponse(physicalTestService.getPhysicalTest(physicalTestId), SYSTEM_MESSAGE.PHYSICAL_TEST_FETCHED_BY_ID);
    }

    @GetMapping(URL_CONSTANTS.GET_ALL_PHYSICAL_TESTS)
    public ResponseEntity<GlobalApiResponse<Page<PhysicalTestResponse>>> getAllPhysicalTests(Pageable pageable) {
        return successResponse(physicalTestService.getAllPhysicalTests(pageable), SYSTEM_MESSAGE.PHYSICAL_TEST_FETCHED);
    }

    @PutMapping(URL_CONSTANTS.UPDATE_PHYSICAL_TEST)
    public ResponseEntity<GlobalApiResponse<PhysicalTestResponse>> updatePhysicalTest(@PathVariable Long physicalTestId, @Validated @RequestBody PhysicalTestUpdateRequest physicalTestUpdateRequest) {
        return successResponse(physicalTestService.updatePhysicalTest(physicalTestId, physicalTestUpdateRequest), SYSTEM_MESSAGE.PHYSICAL_TEST_UPDATED);
    }

    @DeleteMapping(URL_CONSTANTS.DELETE_PHYSICAL_TEST)
    public ResponseEntity<GlobalApiResponse<String>> deletePhysicalTest(@PathVariable Long physicalTestId) {
        physicalTestService.deletePhysicalTest(physicalTestId);
        return successResponse(SYSTEM_MESSAGE.PHYSICAL_TEST_DELETED);
    }
}
