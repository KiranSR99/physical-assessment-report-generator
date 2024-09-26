package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.request.PhysicalReportRequest;
import io.github.kiransr99.parg.dto.request.PhysicalReportUpdateRequest;
import io.github.kiransr99.parg.dto.response.PhysicalReportResponse;
import io.github.kiransr99.parg.entity.PhysicalReport;
import io.github.kiransr99.parg.entity.StudentEnrollment;
import io.github.kiransr99.parg.enums.BMIPercentile;
import io.github.kiransr99.parg.repository.PhysicalReportRepository;
import io.github.kiransr99.parg.repository.StudentEnrollmentRepository;
import io.github.kiransr99.parg.service.PhysicalReportService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhysicalReportImpl implements PhysicalReportService {
    private final PhysicalReportRepository physicalReportRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final BMICalculator bmiCalculator;

    @Override
    public PhysicalReportResponse savePhysicalReport(PhysicalReportRequest physicalReportRequest) {
        log.info("Creating Physical Report for Student Enrollment Id: {}", physicalReportRequest.getStudentEnrollmentId());
        StudentEnrollment studentEnrollment = findStudentEnrollmentById(physicalReportRequest.getStudentEnrollmentId());
        PhysicalReport physicalReport = buildPhysicalReportFromRequest(physicalReportRequest, studentEnrollment);
        return new PhysicalReportResponse(physicalReportRepository.save(physicalReport));
    }

    @Override
    public List<PhysicalReportResponse> savePhysicalReports(List<PhysicalReportRequest> physicalReportRequests) {
        log.info("Saving multiple students' physical reports");

        List<PhysicalReport> physicalReports = physicalReportRequests.stream().map(request -> {
            StudentEnrollment studentEnrollment = findStudentEnrollmentById(request.getStudentEnrollmentId());
            return buildPhysicalReportFromRequest(request, studentEnrollment);
        }).toList();

        List<PhysicalReport> savedPhysicalReports = physicalReportRepository.saveAll(physicalReports);

        return savedPhysicalReports.stream().map(PhysicalReportResponse::new).toList();
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
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.STUDENT_ENROLLMENT_NOT_FOUND)
        );
    }

    private PhysicalReport buildPhysicalReportFromRequest(PhysicalReportRequest request, StudentEnrollment studentEnrollment) {
        BigDecimal height = request.getHeight();
        BigDecimal weight = request.getWeight();
        String bmi = bmiCalculator.calculateBMI(weight, height);
        Double ageInMonths = calculateAgeInMonths(studentEnrollment.getStudent().getDateOfBirth());
        BMIPercentile percentile = bmiCalculator.findPercentile(
                studentEnrollment.getStudent().getGender().equalsIgnoreCase("male") ? 1 : 2,
                ageInMonths,
                new BigDecimal(bmi)
        );
        String bmiLevel = bmiCalculator.determineBMILevel(percentile);
        String comment = bmiCalculator.generateComment(percentile);

        return PhysicalReport.builder()
                .studentEnrollment(studentEnrollment)
                .height(height)
                .weight(weight)
                .bmi(new BigDecimal(bmi))
                .bmiLevel(bmiLevel)
                .percentile(percentile.getDescription())
                .comment(comment)
                .build();
    }

    private void updatePhysicalReportFromRequest(PhysicalReport physicalReport, PhysicalReportUpdateRequest request, StudentEnrollment studentEnrollment) {
        BigDecimal height = request.getHeight();
        BigDecimal weight = request.getWeight();
        String bmi = bmiCalculator.calculateBMI(weight, height);
        Double ageInMonths = calculateAgeInMonths(studentEnrollment.getStudent().getDateOfBirth());
        BMIPercentile percentile = bmiCalculator.findPercentile(
                studentEnrollment.getStudent().getGender().equalsIgnoreCase("male") ? 1 : 2,
                ageInMonths,
                new BigDecimal(bmi)
        );
        String bmiLevel = bmiCalculator.determineBMILevel(percentile);
        String comment = bmiCalculator.generateComment(percentile);

        physicalReport.setStudentEnrollment(studentEnrollment);
        physicalReport.setHeight(height);
        physicalReport.setWeight(weight);
        physicalReport.setBmi(new BigDecimal(bmi));
        physicalReport.setBmiLevel(bmiLevel);
        physicalReport.setPercentile(percentile.getDescription());
        physicalReport.setComment(comment);
    }

    private Double calculateAgeInMonths(LocalDate dateOfBirth) {
        Period period = Period.between(dateOfBirth, LocalDate.now());
        return period.getYears() * 12 + period.getMonths() + period.getDays() / 30.0;
    }
}