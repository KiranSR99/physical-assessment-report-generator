package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.response.ExcelResponse;
import io.github.kiransr99.parg.entity.*;
import io.github.kiransr99.parg.entity.Class;
import io.github.kiransr99.parg.enums.BMIPercentile;
import io.github.kiransr99.parg.repository.*;
import io.github.kiransr99.parg.service.ExcelService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExcelImpl implements ExcelService {
    private final StudentRepository studentRepository;
    private final ClassRepository classRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final PhysicalReportRepository physicalReportRepository;
    private final PhysicalTestRepository physicalTestRepository;
    private final PhysicalTestPerformanceRepository physicalTestPerformanceRepository;
    private final PhysicalTestPerformanceMetricRepository physicalTestPerformanceMetricRepository;
    private final ExamRepository examRepository;
    private final BMIDataRepository bmiDataRepository;
    private final BMICalculator bmiCalculator;

    @Override
    public ExcelResponse saveExcelData(Long examId, MultipartFile file) throws IOException {
        Exam exam = getExamById(examId);
        School school = exam.getSchool();

        List<StudentEnrollment> studentEnrollmentList = new ArrayList<>();
        List<PhysicalReport> physicalReportList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                List<String> physicalTestHeader = extractPhysicalTestHeaders(sheet);

                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue;  // Skip header row

                    processRow(row, school, exam, physicalTestHeader, studentEnrollmentList, physicalReportList);
                }
            }
        } catch (IOException e) {
            log.error("Error reading Excel file", e);
            throw e;
        }
        return new ExcelResponse(studentEnrollmentList, physicalReportList);
    }

    private void processRow(Row row, School school, Exam exam, List<String> physicalTestHeader,
                            List<StudentEnrollment> studentEnrollmentList, List<PhysicalReport> physicalReportList) {
        try {
            StudentEnrollment studentEnrollment = parseStudentData(row, school, exam);
            PhysicalReport physicalReport = createPhysicalReport(row, studentEnrollment);

            processPhysicalTestData(row, physicalTestHeader, physicalReport);
            saveData(studentEnrollment, physicalReport);

            studentEnrollmentList.add(studentEnrollment);
            physicalReportList.add(physicalReport);
        } catch (Exception e) {
            log.error("Error processing row {}: {}", row.getRowNum(), e.getMessage());
        }
    }

    private Exam getExamById(Long examId) {
        return examRepository.findById(examId).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.EXAM_NOT_FOUND)
        );
    }

    private List<String> extractPhysicalTestHeaders(Sheet sheet) {
        List<String> physicalTestHeader = new ArrayList<>();
        Row headerRow = sheet.getRow(0);
        int totalCells = headerRow.getPhysicalNumberOfCells();
        log.info("Total Cells: {}", totalCells);

        for (int j = 8; j < totalCells; j++) {
            String header = headerRow.getCell(j).getStringCellValue();
            physicalTestHeader.add(header);
            log.info("Header Row Cell Data: {}", header);
        }
        return physicalTestHeader;
    }

    private StudentEnrollment parseStudentData(Row row, School school, Exam exam) {
        StudentEnrollment studentEnrollment = new StudentEnrollment();
        studentEnrollment.setExam(exam);

        Student student = new Student();
        studentEnrollment.setRollNumber(String.valueOf((int) row.getCell(0).getNumericCellValue()));
        student.setName(row.getCell(1).getStringCellValue());

        Class studentClass = parseClass(row.getCell(2), school);
        studentEnrollment.setClassName(studentClass);
        studentEnrollment.setSection(row.getCell(3).getStringCellValue());

        student.setGender(row.getCell(4).getStringCellValue());
        student.setDateOfBirth(row.getCell(5).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        student.setAge(calculateAge(student.getDateOfBirth()));

        studentRepository.save(student);  // Save student
        studentEnrollment.setStudent(student);

        return studentEnrollment;
    }

    private Class parseClass(Cell classCell, School school) {
        String className;
        if (classCell.getCellType() == CellType.NUMERIC) {
            className = String.valueOf((int) classCell.getNumericCellValue());
        } else if (classCell.getCellType() == CellType.STRING) {
            className = classCell.getStringCellValue().trim();
        } else {
            throw new EntityNotFoundException("Invalid class name format");
        }

        return classRepository.findByNameAndSchool(className, school).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.CLASS_NOT_FOUND)
        );
    }

    private PhysicalReport createPhysicalReport(Row row, StudentEnrollment studentEnrollment) {
        PhysicalReport physicalReport = new PhysicalReport();
        physicalReport.setStudentEnrollment(studentEnrollment);

        // Extract height and weight from the row
        BigDecimal height = BigDecimal.valueOf(row.getCell(6).getNumericCellValue());
        BigDecimal weight = BigDecimal.valueOf(row.getCell(7).getNumericCellValue());
        physicalReport.setHeight(height);
        physicalReport.setWeight(weight);
        log.info("Height: {}, Weight: {}", height, weight);

        // Calculate BMI
        BigDecimal bmi = new BigDecimal(bmiCalculator.calculateBMI(weight, height));
        log.info("BMI: {}", bmi);
        physicalReport.setBmi(bmi);

        // Calculate Percentile
//        log gender, age, bmi
        log.info("Age: {}, Gender: {}, BMI: {}", studentEnrollment.getStudent().getAge(), studentEnrollment.getStudent().getGender(), bmi);

        BMIPercentile percentile = bmiCalculator.findPercentile(
                studentEnrollment.getStudent().getGender().equalsIgnoreCase("male") ? 1 : 2,
                studentEnrollment.getStudent().getAge(),
                bmi
        );
        log.info("Percentile: {}", percentile);
        physicalReport.setPercentile(percentile.getDescription());

        // Determine BMI Level
        String bmiLevel = bmiCalculator.determineBMILevel(percentile);
        log.info("BMI Level: {}", bmiLevel);
        physicalReport.setBmiLevel(bmiLevel);

        // Generate Comment
        String comment = bmiCalculator.generateComment(percentile);
        log.info("Comment: {}", comment);
        physicalReport.setComment(comment);

        return physicalReport;
    }

    private void processPhysicalTestData(Row row, List<String> physicalTestHeader, PhysicalReport physicalReport) {
        for (int j = 8; j < row.getPhysicalNumberOfCells(); j++) {
            String testName = physicalTestHeader.get(j - 8);
            PhysicalTest physicalTest = physicalTestRepository.findByName(testName).orElse(null);
            if (physicalTest != null) {
                savePhysicalTestPerformance(physicalReport, row, j, physicalTest);
            }
        }
    }

    private void savePhysicalTestPerformance(PhysicalReport physicalReport, Row row, int j, PhysicalTest physicalTest) {
        PhysicalTestPerformance performance = new PhysicalTestPerformance();
        performance.setPhysicalReport(physicalReport);
        performance.setPhysicalTest(physicalTest);
        physicalTestPerformanceRepository.save(performance);

        PhysicalTestPerformanceMetric metric = new PhysicalTestPerformanceMetric();
        metric.setPhysicalReport(physicalReport);
        metric.setPhysicalTest(physicalTest);
        metric.setValue(BigDecimal.valueOf(row.getCell(j).getNumericCellValue()));
        physicalTestPerformanceMetricRepository.save(metric);
    }

    private void saveData(StudentEnrollment studentEnrollment, PhysicalReport physicalReport) {
        studentEnrollmentRepository.save(studentEnrollment);
        physicalReportRepository.save(physicalReport);
    }

    private Integer calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

}
