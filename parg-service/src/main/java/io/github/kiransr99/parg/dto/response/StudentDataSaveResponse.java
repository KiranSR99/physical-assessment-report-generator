package io.github.kiransr99.parg.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDataSaveResponse {
    private List<StudentResponse> students;
    private List<StudentEnrollmentResponse> studentEnrollments;
    private List<PhysicalReportResponse> physicalReports;
    private List<PhysicalTestPerformanceMetricResponse> physicalTestPerformances;

}
