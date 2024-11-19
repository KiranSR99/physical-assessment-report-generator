package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.StudentDataSaveRequest;
import io.github.kiransr99.parg.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentDataSaveService {
    private final StudentService studentService;
    private final StudentEnrollmentService studentEnrollmentService;
    private final PhysicalReportService physicalReportService;
    private final PhysicalTestPerformanceMetricService physicalTestPerformanceMetricService;

    public StudentDataSaveResponse saveAllStudentData(StudentDataSaveRequest request) {
        StudentDataSaveResponse response = new StudentDataSaveResponse();

        // Save the students
        List<StudentResponse> savedStudents = studentService.saveStudents(request.getStudents());
        response.setStudents(savedStudents);

        // Save student enrollments
        List<StudentEnrollmentResponse> savedEnrollments = studentEnrollmentService.saveStudentEnrollment(request.getStudentEnrollments());
        response.setStudentEnrollments(savedEnrollments);

        // Save physical reports
        List<PhysicalReportResponse> savedPhysicalReports = physicalReportService.savePhysicalReports(request.getPhysicalReports());
        response.setPhysicalReports(savedPhysicalReports);

        // Save physical test performance metrics
        List<PhysicalTestPerformanceMetricResponse> savedPerformances = physicalTestPerformanceMetricService.saveMultiplePhysicalTestPerformances(request.getPhysicalTestPerformanceMetrics());
        response.setPhysicalTestPerformances(savedPerformances);

        return response;
    }
}
