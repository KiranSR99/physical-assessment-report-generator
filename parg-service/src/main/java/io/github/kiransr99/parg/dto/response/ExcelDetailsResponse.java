package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.PhysicalReport;
import io.github.kiransr99.parg.entity.StudentEnrollment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    public ExcelDetailsResponse(StudentEnrollment savedStudentEnrollment, PhysicalReport physicalReport) {
        this.name = savedStudentEnrollment.getStudent().getFirstName() + " " + savedStudentEnrollment.getStudent().getLastName();
        this.rollNo = Integer.parseInt(savedStudentEnrollment.getRollNumber());
        this.grade = savedStudentEnrollment.getClassName().getName();
        this.section = savedStudentEnrollment.getSection().getName();
        this.gender = savedStudentEnrollment.getStudent().getGender();
        this.dob = savedStudentEnrollment.getStudent().getDateOfBirth();
        this.age = savedStudentEnrollment.getStudent().getAge();
        this.height = physicalReport.getHeight();
        this.weight = physicalReport.getWeight();
    }
}
