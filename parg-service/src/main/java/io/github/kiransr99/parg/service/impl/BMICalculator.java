package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.entity.BMIData;
import io.github.kiransr99.parg.enums.BMIPercentile;
import io.github.kiransr99.parg.repository.BMIDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;


@Component
@RequiredArgsConstructor
@Slf4j
public class BMICalculator {
    private final BMIDataRepository bmiDataRepository;

    public String calculateBMI(BigDecimal weight, BigDecimal heightInInches) {
        // Check if weight and height are valid
        if (weight == null || heightInInches == null || weight.compareTo(BigDecimal.ZERO) <= 0 || heightInInches.compareTo(BigDecimal.ZERO) <= 0) {
            return "Invalid weight or height. They must be positive numbers.";
        }

        // Convert height from inches to meters
        BigDecimal heightInMeters = heightInInches.multiply(new BigDecimal("0.0254"));

        // Calculate BMI: weight / (height * height), with rounding to 2 decimal places
        BigDecimal bmi = weight.divide(heightInMeters.multiply(heightInMeters), 2, RoundingMode.HALF_UP);

        return bmi.toString();
    }


    public BMIPercentile findPercentile(int sex, double age, BigDecimal bmi) {
        Optional<BMIData> bmiDataOptional = bmiDataRepository.findClosestBySexAndAge(sex, age);
        if (bmiDataOptional.isPresent()) {
            BMIData bmiData = bmiDataOptional.get();
            if (bmi.compareTo(BigDecimal.valueOf(bmiData.getP3())) < 0) {
                return BMIPercentile.BELOW_3RD;
            } else if (bmi.compareTo(BigDecimal.valueOf(bmiData.getP5())) < 0) {
                return BMIPercentile.P3_TO_P5;
            } else if (bmi.compareTo(BigDecimal.valueOf(bmiData.getP10())) < 0) {
                return BMIPercentile.P5_TO_P10;
            } else if (bmi.compareTo(BigDecimal.valueOf(bmiData.getP25())) < 0) {
                return BMIPercentile.P10_TO_P25;
            } else if (bmi.compareTo(BigDecimal.valueOf(bmiData.getP50())) < 0) {
                return BMIPercentile.P25_TO_P50;
            } else if (bmi.compareTo(BigDecimal.valueOf(bmiData.getP75())) < 0) {
                return BMIPercentile.P50_TO_P75;
            } else if (bmi.compareTo(BigDecimal.valueOf(bmiData.getP85())) < 0) {
                return BMIPercentile.P75_TO_P85;
            } else if (bmi.compareTo(BigDecimal.valueOf(bmiData.getP90())) < 0) {
                return BMIPercentile.P85_TO_P90;
            } else if (bmi.compareTo(BigDecimal.valueOf(bmiData.getP95())) < 0) {
                return BMIPercentile.P90_TO_P95;
            } else if (bmi.compareTo(BigDecimal.valueOf(bmiData.getP97())) < 0) {
                return BMIPercentile.P95_TO_P97;
            } else {
                return BMIPercentile.ABOVE_97TH;
            }
        }
        return BMIPercentile.UNKNOWN;
    }

    public String determineBMILevel(BMIPercentile percentile) {
        return switch (percentile) {
            case P3_TO_P5, P5_TO_P10, P10_TO_P25 -> "Underweight";
            case P25_TO_P50, P50_TO_P75 -> "Normal weight";
            case P75_TO_P85, P85_TO_P90, P90_TO_P95, P95_TO_P97 -> "Overweight";
            default -> "Obese";
        };
    }

    public String generateComment(BMIPercentile percentile) {
        String bmiLevel = determineBMILevel(percentile);
        return "BMI Level: " + bmiLevel + ", Percentile: " + percentile.getDescription();
    }
}