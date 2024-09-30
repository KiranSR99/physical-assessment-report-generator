package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.StudentAllDetailsRequest;
import io.github.kiransr99.parg.dto.request.StudentAllDetailsUpdateRequest;
import io.github.kiransr99.parg.dto.request.StudentDataSaveRequest;
import io.github.kiransr99.parg.dto.response.StudentDataSaveResponse;
import io.github.kiransr99.parg.service.StudentAllDetailsService;
import io.github.kiransr99.parg.service.StudentDataSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/parg/api/v1/student-data")
@RequiredArgsConstructor
public class StudentDataSaveController extends BaseController {
    private final StudentDataSaveService studentDataSaveService;
    private final StudentAllDetailsService studentAllDetailsService;

    @PostMapping("/saveComplete")
    public ResponseEntity<GlobalApiResponse<String>> saveCompleteData(@RequestBody StudentDataSaveRequest request) {
        studentDataSaveService.saveAllStudentData(request);
        return successResponse(null, "Complete student data added successfully.");
    }

    @PostMapping("/save-all-details")
    public ResponseEntity<GlobalApiResponse<String>> saveAllDetails(@RequestBody List<StudentAllDetailsRequest> request) {
        studentAllDetailsService.saveAllStudentDetails(request);
        return buildResponse("All details saved successfully.");
    }

    @PutMapping("/update-student")
    public ResponseEntity<GlobalApiResponse<String>> updateAllDetails(@RequestBody StudentAllDetailsUpdateRequest request) {
        studentAllDetailsService.updateStudentDetails(request);
        return buildResponse("All details updated successfully.");
    }

    @PutMapping("/update-multiple-students")
    public ResponseEntity<GlobalApiResponse<String>> updateMultipleStudents(@RequestBody List<StudentAllDetailsUpdateRequest> request) {
        studentAllDetailsService.updateMultipleStudents(request);
        return buildResponse("Multiple students updated successfully.");
    }

    @DeleteMapping("/delete-student/{studentId}")
    public ResponseEntity<GlobalApiResponse<String>> deleteStudent(@PathVariable Long studentId) {
        studentAllDetailsService.deleteStudentDetails(studentId);
        return buildResponse("Student deleted successfully.");
    }

    @DeleteMapping("/delete-multiple-students")
    public ResponseEntity<GlobalApiResponse<String>> deleteMultipleStudents(@RequestBody List<Long> studentIds) {
        studentAllDetailsService.deleteMultipleStudents(studentIds);
        return buildResponse("Multiple students deleted successfully.");
    }

    private ResponseEntity<GlobalApiResponse<String>> buildResponse(String message) {
        return ResponseEntity.ok(
                new GlobalApiResponse<>(LocalDateTime.now(), message, null, "SUCCESS")
        );
    }
}

