package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.PhysicalTestPerformanceMetric;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PhysicalTestPerformanceMetricResponse {
    public Long id;
    public PhysicalTestResponse physicalTest;
    public PhysicalReportResponse physicalReport;
    public BigDecimal value;

    public PhysicalTestPerformanceMetricResponse(PhysicalTestPerformanceMetric physicalTestPerformance) {
        this.id = physicalTestPerformance.getId();
        this.physicalTest = new PhysicalTestResponse(physicalTestPerformance.getPhysicalTest());
        this.physicalReport = new PhysicalReportResponse(physicalTestPerformance.getPhysicalReport());
        this.value = physicalTestPerformance.getValue();
    }
}
