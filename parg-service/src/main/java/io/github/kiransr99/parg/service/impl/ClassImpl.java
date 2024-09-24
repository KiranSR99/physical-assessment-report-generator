package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.request.ClassListRequest;
import io.github.kiransr99.parg.dto.request.ClassRequest;
import io.github.kiransr99.parg.dto.response.ClassResponse;
import io.github.kiransr99.parg.entity.Class;
import io.github.kiransr99.parg.entity.Exam;
import io.github.kiransr99.parg.entity.School;
import io.github.kiransr99.parg.repository.ClassRepository;
import io.github.kiransr99.parg.repository.ExamRepository;
import io.github.kiransr99.parg.repository.SchoolRepository;
import io.github.kiransr99.parg.service.ClassService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassImpl implements ClassService {
    private final ClassRepository classRepository;
    private final SchoolRepository schoolRepository;
    private final ExamRepository examRepository;

    @Override
    public List<ClassResponse> saveClass(ClassRequest request) {
        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.SCHOOL_NOT_FOUND));
        Exam exam = examRepository.findById(request.getExamId()).orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.EXAM_NOT_FOUND));
        return request.getClasses().stream()
                .map(classRequest -> {
                    Class newClass = new Class();
                    newClass.setName(classRequest.getName());
                    newClass.setSchool(school);
                    newClass.setExam(exam);
                    Class savedClass = classRepository.save(newClass);
                    return new ClassResponse(savedClass);
                })
                .toList();
    }

    @Override
    public List<ClassResponse> getAllClasses() {
        List<Class> classes = classRepository.findByStatusTrue();
        return classes.stream()
                .map(ClassResponse::new)
                .toList();
    }

    @Override
    public List<ClassResponse> getAllClassesBySchoolId(Long schoolId) {
        School school = schoolRepository.findById(schoolId)
                .orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.SCHOOL_NOT_FOUND));
        return classRepository.findBySchoolAndStatusTrue(school).stream()
                .map(ClassResponse::new)
                .toList();
    }

    @Override
    public List<ClassResponse> getAllClassesByExamId(Long examId) {
        Exam exam = examRepository.findById(examId).orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.EXAM_NOT_FOUND));
        return classRepository.findByExamAndStatusTrue(exam).stream()
                .map(ClassResponse::new)
                .toList();
    }

    @Override
    public ClassResponse getClassById(Long classId) {
        return classRepository.findById(classId)
                .filter(Class::isStatus)
                .map(ClassResponse::new)
                .orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.CLASS_NOT_FOUND));
    }

    @Override
    public ClassResponse updateClass(Long classId, ClassListRequest request) {
        Class existingClass = classRepository.findById(classId)
                .filter(Class::isStatus)
                .orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.CLASS_NOT_FOUND));
        existingClass.setName(request.getName());
        Class updatedClass = classRepository.save(existingClass);
        return new ClassResponse(updatedClass);
    }

    @Override
    public String deleteClass(Long classId) {
        Class existingClass = classRepository.findById(classId)
                .filter(Class::isStatus)
                .orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.CLASS_NOT_FOUND));
        existingClass.setStatus(false);
        classRepository.save(existingClass);
        return SYSTEM_MESSAGE.CLASS_DELETED;
    }
}