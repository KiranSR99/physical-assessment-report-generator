package io.github.kiransr99.parg.dto.request;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PhysicalTestPerformanceRequest {
    private Long id;
    private Long physicalTestId;
    private Long physicalReportId;
}
