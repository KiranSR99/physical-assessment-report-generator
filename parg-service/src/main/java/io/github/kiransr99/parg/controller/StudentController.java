package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.StudentRequest;
import io.github.kiransr99.parg.dto.request.StudentUpdateRequest;
import io.github.kiransr99.parg.dto.response.StudentResponse;
import io.github.kiransr99.parg.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(URL_CONSTANTS.STUDENT_URL)
@RequiredArgsConstructor
public class StudentController extends BaseController {
    private final StudentService studentService;

    @PostMapping(URL_CONSTANTS.SAVE_STUDENT)
    public ResponseEntity<GlobalApiResponse<StudentResponse>> saveStudent (@Validated @RequestBody StudentRequest request){
        return successResponse(studentService.saveStudent(request), SYSTEM_MESSAGE.STUDENT_SAVED);
    }

    @PostMapping("/{schoolId}/upload")
    public ResponseEntity<GlobalApiResponse<List<StudentResponse>>> uploadExcel(@PathVariable Long schoolId, @RequestParam("file") MultipartFile file) throws IOException {
        return successResponse(studentService.uploadExcel(schoolId, file), SYSTEM_MESSAGE.STUDENTS_UPLOADED);
    }

    @GetMapping(URL_CONSTANTS.GET_STUDENT_BY_ID)
    public ResponseEntity<GlobalApiResponse<StudentResponse>> getStudentById (@PathVariable Long studentId){
        return successResponse(studentService.getStudent(studentId), SYSTEM_MESSAGE.STUDENT_FETCHED_BY_ID);
    }

    @PutMapping(URL_CONSTANTS.UPDATE_STUDENT)
    public ResponseEntity<GlobalApiResponse<StudentResponse>> updateStudent (@PathVariable Long studentId, @Validated @RequestBody StudentUpdateRequest studentUpdateRequest){
        return successResponse(studentService.updateStudent(studentId, studentUpdateRequest), SYSTEM_MESSAGE.STUDENT_UPDATED);
    }

    @DeleteMapping(URL_CONSTANTS.DELETE_STUDENT)
    public ResponseEntity<GlobalApiResponse<String>> deleteStudent (@PathVariable Long studentId){
        studentService.deleteStudent(studentId);
        return successResponse(SYSTEM_MESSAGE.STUDENT_DELETED);
    }

}
