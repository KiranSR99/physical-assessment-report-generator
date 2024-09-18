package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.PhysicalTestMetric;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhysicalTestMetricResponse {
    private Long physicalTestMetricId;
    private Long physicalTestId;
    private String name;
    private String unit;

    public PhysicalTestMetricResponse(PhysicalTestMetric physicalTestMetric) {
        this.physicalTestMetricId = physicalTestMetric.getId();
        this.physicalTestId = physicalTestMetric.getPhysicalTest().getId();
        this.name = physicalTestMetric.getName();
        this.unit = physicalTestMetric.getUnit();
    }
}
