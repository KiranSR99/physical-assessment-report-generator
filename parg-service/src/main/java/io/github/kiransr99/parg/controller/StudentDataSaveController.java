package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.StudentAllDetailsListRequest;
import io.github.kiransr99.parg.dto.request.StudentAllDetailsRequest;
import io.github.kiransr99.parg.dto.request.StudentDataSaveRequest;
import io.github.kiransr99.parg.dto.response.StudentDataSaveResponse;
import io.github.kiransr99.parg.service.StudentAllDetailsService;
import io.github.kiransr99.parg.service.StudentDataSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student-data")
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
}
