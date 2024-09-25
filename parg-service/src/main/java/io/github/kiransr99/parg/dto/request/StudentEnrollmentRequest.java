package io.github.kiransr99.parg.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentEnrollmentRequest {
    public Long studentId;
    private Long classId;
    public Long sectionId;
    public Long academicYearId;
    public String rollNumber;
}