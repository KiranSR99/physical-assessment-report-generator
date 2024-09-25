package io.github.kiransr99.parg.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentEnrollmentListRequest {
    public Long studentId;
    public String rollNumber;
    private String section;
}
