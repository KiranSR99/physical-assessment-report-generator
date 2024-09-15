package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.StudentEnrollmentRequest;
import io.github.kiransr99.parg.dto.response.StudentEnrollmentResponse;

public interface StudentEnrollmentService {
    StudentEnrollmentResponse saveStudentEnrollment(StudentEnrollmentRequest studentEnrollmentRequest);
}
