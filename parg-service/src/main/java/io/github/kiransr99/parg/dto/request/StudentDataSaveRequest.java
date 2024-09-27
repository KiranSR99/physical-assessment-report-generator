package io.github.kiransr99.parg.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDataSaveRequest {
    private List<StudentRequest> students;
    private StudentEnrollmentRequest studentEnrollments;
    private List<PhysicalReportRequest> physicalReports;
    private List<PhysicalTestPerformanceMetricRequest> physicalTestPerformanceMetrics;
}
