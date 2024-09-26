package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.StudentRequest;
import io.github.kiransr99.parg.dto.request.StudentUpdateRequest;
import io.github.kiransr99.parg.dto.response.StudentCompleteDataResponse;
import io.github.kiransr99.parg.dto.response.StudentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StudentService {
    StudentResponse saveStudent(StudentRequest studentRequest);
    List<StudentResponse> saveStudents(List<StudentRequest> studentRequests);
    StudentResponse getStudent(Long id);
    List<StudentResponse> getAllStudentsByExamId(Long examId);
    List<StudentResponse> getAllStudentsByClassId(Long classId);
    StudentResponse updateStudent(Long id, StudentUpdateRequest studentUpdateRequest);
    void deleteStudent(Long id);

    List<StudentResponse> uploadExcel(Long schoolId, MultipartFile file) throws IOException;

    List<StudentCompleteDataResponse> getStudentCompleteDataByClassId(Long classId);
}
