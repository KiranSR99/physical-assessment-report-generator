package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.request.StudentEnrollmentRequest;
import io.github.kiransr99.parg.dto.response.StudentEnrollmentResponse;
import io.github.kiransr99.parg.entity.Class;
import io.github.kiransr99.parg.entity.Exam;
import io.github.kiransr99.parg.entity.Section;
import io.github.kiransr99.parg.entity.Student;
import io.github.kiransr99.parg.entity.StudentEnrollment;
import io.github.kiransr99.parg.repository.*;
import io.github.kiransr99.parg.service.StudentEnrollmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor

public class StudentEnrollmentImpl implements StudentEnrollmentService {

    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final ClassRepository classRepository;
    private final StudentRepository studentRepository;
    private final ExamRepository examRepository;

    @Override
    public List<StudentEnrollmentResponse> saveStudentEnrollment(StudentEnrollmentRequest studentEnrollmentRequest) {
        log.info("Saving student enrollments for classId: {} and examId: {}", studentEnrollmentRequest.getClassId(), studentEnrollmentRequest.getExamId());

        // Fetch the shared Class and Exam entities once
        Class studentClass = classRepository.findById(studentEnrollmentRequest.getClassId())
                .orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.CLASS_NOT_FOUND));
        Exam exam = examRepository.findById(studentEnrollmentRequest.getExamId())
                .orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.EXAM_NOT_FOUND));

        // Loop through the student enrollments
        List<StudentEnrollment> enrollments = studentEnrollmentRequest.getStudentEnrollments().stream().map(request -> {
            // Fetch the Student entity
            Student student = studentRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.STUDENT_NOT_FOUND));

            // Fetch the Section entity (Assuming section is a unique string identifier)
//            Section section = sectionRepository.findByName(request.getSection())
//                    .orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.SECTION_NOT_FOUND));

            // Create and populate the StudentEnrollment entity
            StudentEnrollment studentEnrollment = new StudentEnrollment();
            studentEnrollment.setStudent(student);
            studentEnrollment.setClassName(studentClass);
            studentEnrollment.setSection(request.getSection());
            studentEnrollment.setExam(exam);
            studentEnrollment.setRollNumber(request.getRollNumber());

            return studentEnrollment;
        }).collect(Collectors.toList());

        // Save all enrollments in bulk
        List<StudentEnrollment> savedEnrollments = studentEnrollmentRepository.saveAll(enrollments);

        // Convert the saved enrollments to response DTOs
        return savedEnrollments.stream()
                .map(StudentEnrollmentResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public StudentEnrollmentResponse getStudentEnrollment(Long studentEnrollmentId) {
        log.info("Getting student enrollment: {}", studentEnrollmentId);
        StudentEnrollment studentEnrollment = studentEnrollmentRepository.findById(studentEnrollmentId).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.STUDENT_ENROLLMENT_NOT_FOUND)
        );
        return new StudentEnrollmentResponse(studentEnrollment);
    }

    @Override
    public List<StudentEnrollmentResponse> getAllStudentEnrollments(Pageable pageable) {
        log.info("Getting all student enrollments");
        Page<StudentEnrollment> studentEnrollments = studentEnrollmentRepository.findAll(pageable);
        return studentEnrollments.stream().map(StudentEnrollmentResponse::new).toList();
    }

    @Override
    public void deleteStudentEnrollment(Long studentEnrollmentId) {
        log.info("Deleting student enrollment: {}", studentEnrollmentId);
        studentEnrollmentRepository.deleteById(studentEnrollmentId);
    }

    @Override
    public StudentEnrollmentResponse updateStudentEnrollment(Long studentEnrollmentId, StudentEnrollmentRequest studentEnrollmentRequest) {
        log.info("Updating student enrollment: {}", studentEnrollmentRequest);
        StudentEnrollment studentEnrollment = studentEnrollmentRepository.findById(studentEnrollmentId).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.STUDENT_ENROLLMENT_NOT_FOUND)
        );

        // Update shared fields
        Class studentClass = classRepository.findById(studentEnrollmentRequest.getClassId()).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.CLASS_NOT_FOUND)
        );
        Exam exam = examRepository.findById(studentEnrollmentRequest.getExamId()).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.EXAM_NOT_FOUND)
        );
        studentEnrollment.setExam(exam);
        studentEnrollment.setClassName(studentClass);

        // Update individual fields (like roll number and section) if they are being updated
        studentEnrollment.setRollNumber(studentEnrollmentRequest.getStudentEnrollments().get(0).getRollNumber());
        studentEnrollment.setSection(studentEnrollmentRequest.getStudentEnrollments().get(0).getSection());

//        Section section = sectionRepository.findByName(studentEnrollmentRequest.getStudentEnrollments().get(0).getSection())
//                .orElseThrow(() -> new EntityNotFoundException(SYSTEM_MESSAGE.SECTION_NOT_FOUND));

        StudentEnrollment savedStudentEnrollment = studentEnrollmentRepository.save(studentEnrollment);
        return new StudentEnrollmentResponse(savedStudentEnrollment);
    }
}
