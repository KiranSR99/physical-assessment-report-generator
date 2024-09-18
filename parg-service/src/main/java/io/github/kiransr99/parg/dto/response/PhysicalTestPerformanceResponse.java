package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.PhysicalTestPerformance;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhysicalTestPerformanceResponse {
    private Long id;
    private PhysicalTestResponse physicalTest;
    private PhysicalReportResponse physicalReport;


    public PhysicalTestPerformanceResponse(PhysicalTestPerformance physicalTestPerformance) {
        this.id = physicalTestPerformance.getId();
        this.physicalTest = new PhysicalTestResponse(physicalTestPerformance.getPhysicalTest());
//        this.physicalReport = new PhysicalReportResponse(physicalTestPerformance.getPhysicalReport());
    }
}
