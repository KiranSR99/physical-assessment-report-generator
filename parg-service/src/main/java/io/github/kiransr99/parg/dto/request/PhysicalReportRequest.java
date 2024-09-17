package io.github.kiransr99.parg.dto.request;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalReportRequest {
    private Long studentEnrollmentId;
    private BigDecimal height;
    private BigDecimal weight;
    private BigDecimal bmi;

    private String bmiLevel;
    private String percentile;
    private String comment;
}
