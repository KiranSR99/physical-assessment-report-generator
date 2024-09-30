package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.BMICalculateRequest;
import io.github.kiransr99.parg.dto.response.BMICalculateResponse;
import io.github.kiransr99.parg.service.BMIDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parg/api/v1/bmi")
public class BMIDetailController extends BaseController {
    private final BMIDetailService bmiDetailService;

    @PostMapping("/bmi-details")
    public ResponseEntity<GlobalApiResponse<BMICalculateResponse>> bmiDetails(@RequestBody BMICalculateRequest request) {
        return successResponse(bmiDetailService.calculateBMIDetails(request), "BMI and related details fetched successfully.");
    }
}
