package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.StudentRequest;
import io.github.kiransr99.parg.dto.request.StudentUpdateRequest;
import io.github.kiransr99.parg.dto.response.StudentResponse;

public interface StudentService {
    StudentResponse saveStudent(StudentRequest studentRequest);
    StudentResponse getStudent(Long id);
    StudentResponse updateStudent(Long id, StudentUpdateRequest studentUpdateRequest);
    void deleteStudent(Long id);
}
