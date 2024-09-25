package io.github.kiransr99.parg.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentEnrollmentRequest {
    private Long classId;
    public Long examId;
    private List<StudentEnrollmentListRequest> studentEnrollments;
}