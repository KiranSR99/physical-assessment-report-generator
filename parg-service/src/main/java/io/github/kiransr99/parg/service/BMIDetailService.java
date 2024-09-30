package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.BMICalculateRequest;
import io.github.kiransr99.parg.dto.response.BMICalculateResponse;
import io.github.kiransr99.parg.enums.BMIPercentile;
import io.github.kiransr99.parg.service.impl.BMICalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class BMIDetailService {
    private final BMICalculator bmiCalculator;

    public BMICalculateResponse calculateBMIDetails(BMICalculateRequest request) {
        // Convert height and weight to BigDecimal
        BigDecimal weight = BigDecimal.valueOf(request.getWeight());
        BigDecimal height = BigDecimal.valueOf(request.getHeight());

        // Calculate BMI
        String bmiValue = bmiCalculator.calculateBMI(weight, height);
        BigDecimal bmi = new BigDecimal(bmiValue);

        // Find BMI percentile based on age, gender, and BMI
        BMIPercentile percentile = bmiCalculator.findPercentile(request.getGender(), request.getAge(), bmi);

        // Determine BMI level (e.g., Normal, Overweight)
        String bmiLevel = bmiCalculator.determineBMILevel(percentile);

        // Generate comment (BMI level and description)
        String comment = bmiCalculator.generateComment(percentile);

        // Create a response object
        return new BMICalculateResponse(bmi, percentile, bmiLevel, comment);
    }
}
