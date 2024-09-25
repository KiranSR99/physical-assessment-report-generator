package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.StudentEnrollmentRequest;
import io.github.kiransr99.parg.dto.response.StudentEnrollmentResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentEnrollmentService {
    // Now returns a list of responses, as we are enrolling multiple students at once.
    List<StudentEnrollmentResponse> saveStudentEnrollment(StudentEnrollmentRequest studentEnrollmentRequest);

    StudentEnrollmentResponse getStudentEnrollment(Long studentEnrollmentId);

    void deleteStudentEnrollment(Long studentEnrollmentId);

    StudentEnrollmentResponse updateStudentEnrollment(Long studentEnrollmentId, StudentEnrollmentRequest studentEnrollmentRequest);

    List<StudentEnrollmentResponse> getAllStudentEnrollments(Pageable pageable);
}
