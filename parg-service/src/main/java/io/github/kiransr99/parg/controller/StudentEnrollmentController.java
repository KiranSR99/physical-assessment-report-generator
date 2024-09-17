package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.StudentEnrollmentRequest;
import io.github.kiransr99.parg.dto.response.StudentEnrollmentResponse;
import io.github.kiransr99.parg.service.StudentEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(URL_CONSTANTS.STUDENT_ENROLLMENT_URL)
@RequiredArgsConstructor
public class StudentEnrollmentController extends BaseController {

    private final StudentEnrollmentService studentEnrollmentService;

    @PostMapping(URL_CONSTANTS.SAVE_STUDENT_ENROLLMENT)
    public ResponseEntity<GlobalApiResponse<StudentEnrollmentResponse>> saveStudentEnrollment(StudentEnrollmentRequest studentEnrollmentRequest) {
        return successResponse(studentEnrollmentService.saveStudentEnrollment(studentEnrollmentRequest), "Student enrollment saved successfully");
    }

    @GetMapping(URL_CONSTANTS.GET_STUDENT_ENROLLMENT_BY_ID)
    public ResponseEntity<GlobalApiResponse<StudentEnrollmentResponse>> getStudentEnrollment(@PathVariable Long studentEnrollmentId) {
        return successResponse(studentEnrollmentService.getStudentEnrollment(studentEnrollmentId), "Student enrollment fetched successfully");
    }

    @PutMapping(URL_CONSTANTS.UPDATE_STUDENT_ENROLLMENT)
    public ResponseEntity<GlobalApiResponse<StudentEnrollmentResponse>> updateStudentEnrollment(@PathVariable Long studentEnrollmentId, @RequestBody StudentEnrollmentRequest studentEnrollmentRequest) {
        return successResponse(studentEnrollmentService.updateStudentEnrollment(studentEnrollmentId, studentEnrollmentRequest), "Student enrollment updated successfully");
    }

    @DeleteMapping(URL_CONSTANTS.DELETE_STUDENT_ENROLLMENT)
    public ResponseEntity<GlobalApiResponse<String>> deleteStudentEnrollment(@PathVariable Long studentEnrollmentId) {
        studentEnrollmentService.deleteStudentEnrollment(studentEnrollmentId);
        return successResponse("Student enrollment deleted successfully", "Student enrollment deleted successfully");
    }

    @GetMapping(URL_CONSTANTS.GET_ALL_STUDENT_ENROLLMENTS)
    public ResponseEntity<GlobalApiResponse<List<StudentEnrollmentResponse>>> getAllStudentEnrollments(Pageable pageable) {
        return successResponse(studentEnrollmentService.getAllStudentEnrollments(pageable), "Student enrollments fetched successfully");
    }
}
