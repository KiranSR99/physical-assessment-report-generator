package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.request.PhysicalReportRequest;
import io.github.kiransr99.parg.dto.request.PhysicalReportUpdateRequest;
import io.github.kiransr99.parg.dto.response.PhysicalReportResponse;
import io.github.kiransr99.parg.entity.PhysicalReport;
import io.github.kiransr99.parg.entity.StudentEnrollment;
import io.github.kiransr99.parg.repository.PhysicalReportRepository;
import io.github.kiransr99.parg.repository.StudentEnrollmentRepository;
import io.github.kiransr99.parg.service.PhysicalReportService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhysicalReportImpl implements PhysicalReportService {
    private final PhysicalReportRepository physicalReportRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;

    @Override
    public PhysicalReportResponse savePhysicalReport(PhysicalReportRequest physicalReportRequest) {
        log.info("Creating Physical Report for Student Enrollment Id: {}", physicalReportRequest.getStudentEnrollmentId());
        StudentEnrollment studentEnrollment = findStudentEnrollmentById(physicalReportRequest.getStudentEnrollmentId());
        PhysicalReport physicalReport = buildPhysicalReportFromRequest(physicalReportRequest, studentEnrollment);
        return new PhysicalReportResponse(physicalReportRepository.save(physicalReport));
    }

    @Override
    public Page<PhysicalReportResponse> getAllPhysicalReport(Pageable pageable) {
        log.info("Fetching all Physical Reports");
        Page<PhysicalReport> physicalReportPage = physicalReportRepository.findAll(pageable);
        return physicalReportPage.map(PhysicalReportResponse::new);
    }

    @Override
    public PhysicalReportResponse getPhysicalReportById(Long id) {
        log.info("Fetching Physical Report by Id: {}", id);
        PhysicalReport physicalReport = physicalReportRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_REPORT_NOT_FOUND)
        );
        return new PhysicalReportResponse(physicalReport);
    }

    @Override
    public PhysicalReportResponse updatePhysicalReport(Long id, PhysicalReportUpdateRequest physicalReportUpdateRequest) {
        log.info("Updating Physical Report by Id: {}", id);
        PhysicalReport physicalReport = physicalReportRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_REPORT_NOT_FOUND)
        );
        StudentEnrollment studentEnrollment = findStudentEnrollmentById(physicalReportUpdateRequest.getStudentEnrollmentId());
        updatePhysicalReportFromRequest(physicalReport, physicalReportUpdateRequest, studentEnrollment);
        return new PhysicalReportResponse(physicalReportRepository.save(physicalReport));
    }

    @Override
    public void deletePhysicalReport(Long id) {
        log.info("Deleting Physical Report by Id: {}", id);
        PhysicalReport physicalReport = physicalReportRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.PHYSICAL_REPORT_NOT_FOUND)
        );
        physicalReportRepository.delete(physicalReport);
    }

    private StudentEnrollment findStudentEnrollmentById(Long studentEnrollmentId) {
        return studentEnrollmentRepository.findById(studentEnrollmentId).orElseThrow(
                () -> new EntityNotFoundException("Student Enrollment not found")
        );
    }

    private PhysicalReport buildPhysicalReportFromRequest(PhysicalReportRequest request, StudentEnrollment studentEnrollment) {
        return PhysicalReport.builder()
                .studentEnrollment(studentEnrollment)
                .height(request.getHeight())
                .weight(request.getWeight())
                .bmi(request.getBmi())
                .bmiLevel(request.getBmiLevel())
                .percentile(request.getPercentile())
                .comment(request.getComment())
                .build();
    }

    private void updatePhysicalReportFromRequest(PhysicalReport physicalReport, PhysicalReportUpdateRequest request, StudentEnrollment studentEnrollment) {
        physicalReport.setStudentEnrollment(studentEnrollment);
        physicalReport.setHeight(request.getHeight());
        physicalReport.setWeight(request.getWeight());
        physicalReport.setBmi(request.getBmi());
        physicalReport.setBmiLevel(request.getBmiLevel());
        physicalReport.setPercentile(request.getPercentile());
        physicalReport.setComment(request.getComment());
    }
}