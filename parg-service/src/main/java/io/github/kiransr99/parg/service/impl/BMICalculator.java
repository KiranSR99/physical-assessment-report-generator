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

        // Calculate BMI: weight / (height * height), with rounding to 2 decimal places
        BigDecimal bmi = weight.divide(heightInInches.multiply(heightInInches), 2, RoundingMode.HALF_UP);

        return bmi.toString();
    }

    public BMIPercentile findPercentile(int sex, double age, BigDecimal bmi) {
        Optional<BMIData> bmiDataOptional = bmiDataRepository.findClosestBySexAndAge(sex, age);
        if (bmiDataOptional.isPresent()) {
            BMIData bmiData = bmiDataOptional.get();
            if (bmi.compareTo(BigDecimal.valueOf(bmiData.getP5())) < 0) {
                return BMIPercentile.LESS_THAN_5TH;
            } else if (bmi.compareTo(BigDecimal.valueOf(bmiData.getP85())) < 0) {
                return BMIPercentile.P5_TO_P84;
            } else if (bmi.compareTo(BigDecimal.valueOf(bmiData.getP95())) < 0) {
                return BMIPercentile.P85_TO_P94;
            } else {
                return BMIPercentile.GREATER_THAN_95TH;
            }
        }
        return BMIPercentile.UNKNOWN;
    }

    public String determineBMILevel(BMIPercentile percentile) {
        return switch (percentile) {
            case LESS_THAN_5TH -> "Underweight";
            case P5_TO_P84 -> "Healthy";
            case P85_TO_P94 -> "Overweight";
            case GREATER_THAN_95TH -> "Obese";
            default -> "Unknown";
        };
    }

    public String generateComment(BMIPercentile percentile) {
        String comment;
        switch (percentile) {
            case LESS_THAN_5TH -> comment = "Eat more frequently. Eat five to six smaller meals during the day rather than two or three large meals.";
            case P5_TO_P84 -> comment = "Continue good discipline in eating habits and physical fitness.";
            case P85_TO_P94 -> comment = "Cut down on carbohydrates, intake proteins and vegetables. Also, work out often.";
            case GREATER_THAN_95TH -> comment = "Limit energy intake from total fats and sugars. Increase consumption of fruits and vegetables, as well as legumes, and engage in regular physical activity for 60 minutes a day.";
            default -> comment = "No specific recommendation.";
        }
        return comment;
    }
}
