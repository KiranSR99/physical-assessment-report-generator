package io.github.kiransr99.parg.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalTestMetricUpdateRequest {
    private Long physicalTestId;
    private String name;
    private String unit;
}
