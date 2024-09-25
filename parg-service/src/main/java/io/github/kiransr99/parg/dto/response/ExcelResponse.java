package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.PhysicalReport;
import io.github.kiransr99.parg.entity.StudentEnrollment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ExcelResponse {
    private SchoolResponse school;
    private List<ExcelDetailsResponse> students;

    public ExcelResponse(List<StudentEnrollment> savedStudentEnrollment, List<PhysicalReport> physicalReport) {
        this.school = new SchoolResponse(savedStudentEnrollment.get(0).getClassName().getSchool());
        this.students = new ArrayList<>();
        savedStudentEnrollment.forEach(student -> {
            PhysicalReport report = physicalReport.stream().filter(physicalReport1 -> physicalReport1.getStudentEnrollment().getId().equals(student.getId())).findFirst().orElse(null);
            if (report != null) {
                this.students.add(new ExcelDetailsResponse(student, report));
            }
        });
    }
}
