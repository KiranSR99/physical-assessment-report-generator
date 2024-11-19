package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.PhysicalReport;
import io.github.kiransr99.parg.entity.StudentEnrollment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ExcelDetailsResponse {
    private String name;
    private Integer rollNo;
    private String grade;
    private String section;
    private String gender;
    private LocalDate dob;
    private int age;
    private BigDecimal height;
    private BigDecimal weight;
    private BigDecimal bmi;
    private String bmiLevel;
    private String bmiPercentile;
    private String comment;
    private List<PhysicalTestPerformanceMetricResponse> physicalTestPerformanceMetrics;

    public ExcelDetailsResponse(StudentEnrollment savedStudentEnrollment, PhysicalReport physicalReport) {
        this.name = savedStudentEnrollment.getStudent().getName();
        this.rollNo = Integer.parseInt(savedStudentEnrollment.getRollNumber());
        this.grade = savedStudentEnrollment.getClassName().getName();
        this.section = savedStudentEnrollment.getSection();
        this.gender = savedStudentEnrollment.getStudent().getGender();
        this.dob = savedStudentEnrollment.getStudent().getDateOfBirth();
        this.age = savedStudentEnrollment.getStudent().getDateOfBirth().until(LocalDate.now()).getYears();
        this.height = physicalReport.getHeight();
        this.weight = physicalReport.getWeight();
        this.bmi = physicalReport.getBmi();
        this.bmiLevel = physicalReport.getBmiLevel();
        this.bmiPercentile = physicalReport.getPercentile();
        this.comment = physicalReport.getComment();


        this.physicalTestPerformanceMetrics = physicalReport.getPerformanceMetrics().stream()
                .map(PhysicalTestPerformanceMetricResponse::new)
                .toList();
    }
}
