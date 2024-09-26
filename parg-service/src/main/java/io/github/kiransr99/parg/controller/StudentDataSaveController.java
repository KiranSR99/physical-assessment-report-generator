package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.StudentDataSaveRequest;
import io.github.kiransr99.parg.dto.response.StudentDataSaveResponse;
import io.github.kiransr99.parg.service.StudentDataSaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student-data")
@RequiredArgsConstructor
public class StudentDataSaveController extends BaseController {
    private final StudentDataSaveService studentDataSaveService;

    @PostMapping("/saveComplete")
    public ResponseEntity<GlobalApiResponse<StudentDataSaveResponse>> saveCompleteData(@RequestBody StudentDataSaveRequest request) {
        return successResponse(studentDataSaveService.saveAllStudentData(request), "Complete student data added successfully.");
    }
}
