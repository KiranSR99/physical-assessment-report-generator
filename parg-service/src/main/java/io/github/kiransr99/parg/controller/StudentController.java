package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.StudentRequest;
import io.github.kiransr99.parg.dto.request.StudentUpdateRequest;
import io.github.kiransr99.parg.dto.response.StudentCompleteDataResponse;
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

    @PostMapping(URL_CONSTANTS.SAVE_STUDENTS)
    public ResponseEntity<GlobalApiResponse<List<StudentResponse>>> saveStudents (@Validated @RequestBody List<StudentRequest> requests){
        return successResponse(studentService.saveStudents(requests), SYSTEM_MESSAGE.STUDENT_SAVED);
    }

    @PostMapping("/{schoolId}/upload")
    public ResponseEntity<GlobalApiResponse<List<StudentResponse>>> uploadExcel(@PathVariable Long schoolId, @RequestParam("file") MultipartFile file) throws IOException {
        return successResponse(studentService.uploadExcel(schoolId, file), SYSTEM_MESSAGE.STUDENTS_UPLOADED);
    }

    @GetMapping(URL_CONSTANTS.GET_STUDENT_BY_ID)
    public ResponseEntity<GlobalApiResponse<StudentResponse>> getStudentById (@PathVariable Long studentId){
        return successResponse(studentService.getStudent(studentId), SYSTEM_MESSAGE.STUDENT_FETCHED_BY_ID);
    }

    @GetMapping(URL_CONSTANTS.GET_ALL_STUDENTS_BY_EXAM_ID)
    public ResponseEntity<GlobalApiResponse<List<StudentResponse>>> getAllStudentsByExamId (@PathVariable Long examId){
        return successResponse(studentService.getAllStudentsByExamId(examId), SYSTEM_MESSAGE.ALL_EXAM_FETCHED);
    }

    @GetMapping(URL_CONSTANTS.GET_ALL_STUDENTS_BY_CLASS_ID)
    public ResponseEntity<GlobalApiResponse<List<StudentResponse>>> getAllStudentsByClassId (@PathVariable Long classId){
        return successResponse(studentService.getAllStudentsByClassId(classId), "Students fetched according to Class Id successfully.");
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

    @GetMapping("/getStudentCompleteData/{classId}")
    public ResponseEntity<GlobalApiResponse<List<StudentCompleteDataResponse>>> getStudentCompleteData(@PathVariable Long classId){
        return successResponse(studentService.getStudentCompleteDataByClassId(classId), "Students fetched according to Class Id successfully.");
    }

}
