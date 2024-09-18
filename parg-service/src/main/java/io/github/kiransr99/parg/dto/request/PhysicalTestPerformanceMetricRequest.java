package io.github.kiransr99.parg.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class PhysicalTestPerformanceMetricRequest {
    private Long physicalTestId;
    private Long physicalReportId;
    private BigDecimal value;
}
