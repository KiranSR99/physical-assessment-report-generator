package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.enums.BMIPercentile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class BMICalculateResponse {
    private BigDecimal bmi;
    private BMIPercentile percentile;
    private String bmiLevel;
    private String comment;

    public BMICalculateResponse(BigDecimal bmi, BMIPercentile percentile, String bmiLevel, String comment) {
        this.bmi = bmi;
        this.percentile = percentile;
        this.bmiLevel = bmiLevel;
        this.comment = comment;
    }
}
