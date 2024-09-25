package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.PhysicalReport;
import io.github.kiransr99.parg.entity.StudentEnrollment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalReportResponse {
    private Long studentEnrollmentId;
    private BigDecimal height;
    private BigDecimal weight;
    private BigDecimal bmi;

    private String bmiLevel;
    private String percentile;
    private String comment;

    public PhysicalReportResponse(PhysicalReport physicalReport) {
        this.studentEnrollmentId = physicalReport.getStudentEnrollment().getId();
        this.height = physicalReport.getHeight();
        this.weight = physicalReport.getWeight();
        this.bmi = physicalReport.getBmi();
        this.bmiLevel = physicalReport.getBmiLevel();
        this.percentile = physicalReport.getPercentile();
        this.comment = physicalReport.getComment();
    }
}
