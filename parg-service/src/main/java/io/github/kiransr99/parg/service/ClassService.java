package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.ClassListRequest;
import io.github.kiransr99.parg.dto.request.ClassRequest;
import io.github.kiransr99.parg.dto.response.ClassResponse;

import java.util.List;

public interface ClassService {
    List<ClassResponse> saveClass(ClassRequest request);

    List<ClassResponse> getAllClasses();

    List<ClassResponse> getAllClassesBySchoolId(Long schoolId);

    List<ClassResponse> getAllClassesByExamId(Long examId);

    ClassResponse getClassById(Long classId);

    ClassResponse updateClass(Long classId, ClassListRequest request);

    String deleteClass(Long classId);
}
