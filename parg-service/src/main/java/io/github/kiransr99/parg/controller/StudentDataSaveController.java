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

import java.util.List;

@RestController
@RequestMapping("/parg/api/v1/student-data")
@RequiredArgsConstructor
public class StudentDataSaveController extends BaseController {
    private final StudentDataSaveService studentDataSaveService;
    private final StudentAllDetailsService studentAllDetailsService;

    @PostMapping("/saveComplete")
    public ResponseEntity<GlobalApiResponse<StudentDataSaveResponse>> saveCompleteData(@RequestBody StudentDataSaveRequest request) {
        return successResponse(studentDataSaveService.saveAllStudentData(request), "Complete student data added successfully.");
    }

    @PostMapping("/save-all-details")
    public ResponseEntity<String> saveAllDetails(@RequestBody List<StudentAllDetailsRequest> request) {
        studentAllDetailsService.saveAllStudentDetails(request);
        return ResponseEntity.ok("All details saved successfully.");
    }

    @PutMapping("/update-student")
    public ResponseEntity<String> updateAllDetails(@RequestBody StudentAllDetailsUpdateRequest request) {
        studentAllDetailsService.updateStudentDetails(request);
        return ResponseEntity.ok("All details updated successfully.");
    }

    @PutMapping("/update-multiple-students")
    public ResponseEntity<String> updateMultipleStudents(@RequestBody List<StudentAllDetailsUpdateRequest> request) {
        studentAllDetailsService.updateMultipleStudents(request);
        return ResponseEntity.ok("Multiple students updated successfully.");
    }

    @DeleteMapping("/delete-student/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long studentId) {
        studentAllDetailsService.deleteStudentDetails(studentId);
        return ResponseEntity.ok("Student deleted successfully.");
    }

    @DeleteMapping("/delete-multiple-students")
    public ResponseEntity<String> deleteMultipleStudents(@RequestBody List<Long> studentIds) {
        studentAllDetailsService.deleteMultipleStudents(studentIds);
        return ResponseEntity.ok("Multiple students deleted successfully.");
    }
}
