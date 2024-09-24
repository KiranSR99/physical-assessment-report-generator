package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.request.StudentRequest;
import io.github.kiransr99.parg.dto.request.StudentUpdateRequest;
import io.github.kiransr99.parg.dto.response.StudentResponse;
import io.github.kiransr99.parg.entity.Student;
import io.github.kiransr99.parg.repository.StudentRepository;
import io.github.kiransr99.parg.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentImpl implements StudentService {
    private final StudentRepository studentRepository;
    @Override
    public StudentResponse saveStudent(StudentRequest studentRequest) {
        log.info("Saving student: {}", studentRequest);
        Student student = new Student();
        student.setName(studentRequest.getName());
        student.setDateOfBirth(studentRequest.getDateOfBirth());
        student.setAge(studentRequest.getAge());
        student.setGender(studentRequest.getGender());
        return new StudentResponse(studentRepository.save(student));
    }

    @Override
    public StudentResponse getStudent(Long id) {
        log.info("Fetching student with id: {}", id);
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.STUDENT_NOT_FOUND)
        );
        return new StudentResponse(student);
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentUpdateRequest studentUpdateRequest) {
        log.info("Updating student with id: {}", id);
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.STUDENT_NOT_FOUND)
        );
        student.setName(studentUpdateRequest.getName());
        student.setDateOfBirth(studentUpdateRequest.getDateOfBirth());
        student.setAge(studentUpdateRequest.getAge());
        student.setGender(studentUpdateRequest.getGender());
        return new StudentResponse(studentRepository.save(student));
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.STUDENT_NOT_FOUND)
        );
        log.info("Deleting student : {}", student);
        studentRepository.deleteById(id);
    }
}
