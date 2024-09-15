package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.request.StudentEnrollmentRequest;
import io.github.kiransr99.parg.dto.response.StudentEnrollmentResponse;
import io.github.kiransr99.parg.entity.AcademicYear;
import io.github.kiransr99.parg.entity.Section;
import io.github.kiransr99.parg.entity.Student;
import io.github.kiransr99.parg.entity.StudentEnrollment;
import io.github.kiransr99.parg.repository.AcademicYearRepository;
import io.github.kiransr99.parg.repository.SectionRepository;
import io.github.kiransr99.parg.repository.StudentEnrollmentRepository;
import io.github.kiransr99.parg.repository.StudentRepository;
import io.github.kiransr99.parg.service.StudentEnrollmentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Service
@Slf4j
@RequiredArgsConstructor

public class StudentEnrollmentImpl implements StudentEnrollmentService {

    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final StudentRepository studentRepository;
    private final SectionRepository sectionRepository;
    private final AcademicYearRepository academicYearRepository;

    @Override
    public StudentEnrollmentResponse saveStudentEnrollment(StudentEnrollmentRequest studentEnrollmentRequest) {
        log.info("Saving student enrollment: {}", studentEnrollmentRequest);
        Student student = studentRepository.findById(studentEnrollmentRequest.getStudentId()).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.STUDENT_NOT_FOUND)
        );
        Section section = sectionRepository.findById(studentEnrollmentRequest.getSectionId()).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.SECTION_NOT_FOUND)
        );
        AcademicYear academicYear = academicYearRepository.findById(studentEnrollmentRequest.getAcademicYearId()).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.ACADEMIC_YEAR_NOT_FOUND)
        );
        StudentEnrollment studentEnrollment = new StudentEnrollment();
        studentEnrollment.setStudent(student);
        studentEnrollment.setSection(section);
        studentEnrollment.setAcademicYear(academicYear);
        StudentEnrollment savedStudentEnrollment = studentEnrollmentRepository.save(studentEnrollment);
        return new StudentEnrollmentResponse(savedStudentEnrollment);
    }
}
