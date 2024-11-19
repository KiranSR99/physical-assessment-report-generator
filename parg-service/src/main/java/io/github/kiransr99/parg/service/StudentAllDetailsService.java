package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.GameRequest;
import io.github.kiransr99.parg.dto.request.StudentAllDetailsRequest;
import io.github.kiransr99.parg.dto.request.StudentAllDetailsUpdateRequest;
import io.github.kiransr99.parg.entity.Class;
import io.github.kiransr99.parg.entity.*;
import io.github.kiransr99.parg.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class StudentAllDetailsService {
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final PhysicalReportRepository physicalReportRepository;
    private final PhysicalTestPerformanceRepository physicalTestPerformanceRepository;
    private final PhysicalTestRepository physicalTestRepository;
    private final ExamRepository examRepository;
    private final PhysicalTestPerformanceMetricRepository physicalTestPerformanceMetricRepository;

    public void saveAllStudentDetails(List<StudentAllDetailsRequest> studentAllDetailsRequests) {
        for (StudentAllDetailsRequest studentAllDetailsRequest : studentAllDetailsRequests) {
            saveStudentDetails(studentAllDetailsRequest);
        }
    }

    private void saveStudentDetails(StudentAllDetailsRequest request) {
        //Save Student
        Student student = new Student();
        student.setName(request.getName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setAge(request.getAge());
        student.setGender(request.getGender());
        studentRepository.save(student);

        // Save Student Enrollment
        Class studentClass = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new IllegalArgumentException("Class not found"));
        Exam exam = examRepository.findById(request.getExamId()).orElseThrow(() -> new IllegalArgumentException("Exam not found"));
        StudentEnrollment enrollment = new StudentEnrollment();
        enrollment.setStudent(student);
        enrollment.setClassName(studentClass);
        enrollment.setExam(exam);
        enrollment.setSection(request.getSection());
        enrollment.setRollNumber(request.getRollNumber());
        studentEnrollmentRepository.save(enrollment);

        // Save Physical Report
        PhysicalReport physicalReport = new PhysicalReport();
        physicalReport.setStudentEnrollment(enrollment);
        physicalReport.setHeight(request.getHeight());
        physicalReport.setWeight(request.getWeight());
        physicalReport.setBmi(request.getBmi());
        physicalReport.setBmiLevel(request.getBmiLevel());
        physicalReport.setPercentile(request.getPercentile());
        physicalReport.setComment(request.getComment());
        physicalReportRepository.save(physicalReport);

        // Save Physical Test Performance Metric
        for (GameRequest gameRequest : request.getGames()) {
            PhysicalTest physicalTest = physicalTestRepository.findById(gameRequest.getGameId())
                    .orElseThrow(() -> new EntityNotFoundException("Physical Test not found."));

            PhysicalTestPerformanceMetric metric = new PhysicalTestPerformanceMetric();
            metric.setPhysicalTest(physicalTest);
            metric.setPhysicalReport(physicalReport);
            metric.setValue(gameRequest.getValue());
            physicalTestPerformanceMetricRepository.save(metric);
        }

    }


    public void updateMultipleStudents(List<StudentAllDetailsUpdateRequest> studentAllDetailsUpdateRequests) {
        for (StudentAllDetailsUpdateRequest request : studentAllDetailsUpdateRequests) {
            updateStudentDetails(request);
        }
    }

    public void updateStudentDetails(StudentAllDetailsUpdateRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        student.setName(request.getName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        studentRepository.save(student);

        StudentEnrollment enrollment = studentEnrollmentRepository.findByStudentId(student.getId())
                .orElseThrow(() -> new EntityNotFoundException("Student Enrollment not found"));
        Class studentClass = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new IllegalArgumentException("Class not found"));
        enrollment.setClassName(studentClass);
        enrollment.setSection(request.getSection());
        enrollment.setRollNumber(request.getRollNumber());
        studentEnrollmentRepository.save(enrollment);

        PhysicalReport physicalReport = physicalReportRepository.findByStudentEnrollmentId(enrollment.getId())
                .orElseThrow(() -> new EntityNotFoundException("Physical Report not found"));
        physicalReport.setHeight(request.getHeight());
        physicalReport.setWeight(request.getWeight());
        physicalReport.setBmi(request.getBmi());
        physicalReport.setBmiLevel(request.getBmiLevel());
        physicalReport.setPercentile(request.getPercentile());
        physicalReport.setComment(request.getComment());
        physicalReportRepository.save(physicalReport);

        for (GameRequest gameRequest : request.getGames()) {
            PhysicalTest physicalTest = physicalTestRepository.findById(gameRequest.getGameId())
                    .orElseThrow(() -> new EntityNotFoundException("Physical Test not found"));
            PhysicalTestPerformanceMetric metric = physicalTestPerformanceMetricRepository.findByPhysicalTestIdAndPhysicalReportId(physicalTest.getId(), physicalReport.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Physical Test Performance Metric not found"));
            metric.setValue(gameRequest.getValue());
            physicalTestPerformanceMetricRepository.save(metric);
        }
    }


    public void deleteMultipleStudents(List<Long> studentIds) {
        for (Long studentId : studentIds) {
            deleteStudentDetails(studentId);
        }
    }

    public void deleteStudentDetails(Long studentId) {
        log.info("Deleting student with ID: {}", studentId);

        // Fetch and delete the student entity
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        log.info("Student found: {}. Proceeding with deletion of related entities.", student.getName());

        // Fetch student enrollment
        Optional<StudentEnrollment> enrollmentOpt = studentEnrollmentRepository.findByStudentId(studentId);

        if (enrollmentOpt.isPresent()) {
            StudentEnrollment enrollment = enrollmentOpt.get();
            log.info("Student enrollment found for student ID: {}. Proceeding with deletion.", studentId);

            // Fetch and delete physical report
            Optional<PhysicalReport> physicalReportOpt = physicalReportRepository.findByStudentEnrollmentId(enrollment.getId());
            if (physicalReportOpt.isPresent()) {
                PhysicalReport physicalReport = physicalReportOpt.get();
                log.info("Physical report found for enrollment ID: {}. Deleting related metrics.", enrollment.getId());

                // Fetch and delete physical test performance metrics
                List<PhysicalTestPerformanceMetric> metrics = physicalTestPerformanceMetricRepository.findByPhysicalReportId(physicalReport.getId());
                if (!metrics.isEmpty()) {
                    physicalTestPerformanceMetricRepository.deleteAll(metrics);
                    log.info("Deleted {} physical test performance metrics for physical report ID: {}", metrics.size(), physicalReport.getId());
                }

                // Delete physical report
                physicalReportRepository.delete(physicalReport);
                log.info("Deleted physical report with ID: {}", physicalReport.getId());
            }

            // Delete student enrollment
            studentEnrollmentRepository.delete(enrollment);
            log.info("Deleted student enrollment with ID: {}", enrollment.getId());
        } else {
            log.warn("Student enrollment not found for student ID: {}. Continuing deletion.", studentId);
        }

        // Delete the student entity itself
        studentRepository.delete(student);
        log.info("Deleted student with ID: {}", studentId);
    }


}
