package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.ClassListRequest;
import io.github.kiransr99.parg.dto.request.ClassRequest;
import io.github.kiransr99.parg.dto.response.ClassResponse;

import java.util.List;

public interface ClassService {
    List<ClassResponse> saveClass(ClassRequest request);

    List<ClassResponse> getAllClasses();

    List<ClassResponse> getAllClassesBySchoolId(Long schoolId);

    ClassResponse getClassById(Long classId);

    ClassResponse updateClass(Long classId, ClassListRequest request);

    ClassResponse deleteClass(Long classId);
}
