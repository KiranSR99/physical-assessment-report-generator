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
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
            // Loop over each sheet
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);

                // Extract the headers for the current sheet
                List<String> physicalTestHeader = extractPhysicalTestHeaders(sheet);

                // Process rows in the current sheet
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

            // Save student enrollment and physical report before processing test data
            studentEnrollmentRepository.save(studentEnrollment);
            physicalReportRepository.save(physicalReport);

            processPhysicalTestData(row, physicalTestHeader, physicalReport);

            // Add to list after successful save
            studentEnrollmentList.add(studentEnrollment);
            physicalReportList.add(physicalReport);
            physicalReportRepository.save(physicalReport);
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
        Row headerRow = sheet.getRow(0); // Assume first row contains headers
        int totalCells = headerRow.getPhysicalNumberOfCells();
        log.info("Total Cells in sheet '{}': {}", sheet.getSheetName(), totalCells);

        for (int j = 8; j < totalCells; j++) { // Assume physical test headers start from cell index 8
            String header = headerRow.getCell(j).getStringCellValue();
            physicalTestHeader.add(header);
            log.info("Header for sheet '{}', cell {}: {}", sheet.getSheetName(), j, header);
        }

        return physicalTestHeader;
    }


    private StudentEnrollment parseStudentData(Row row, School school, Exam exam) {
        StudentEnrollment studentEnrollment = new StudentEnrollment();
        studentEnrollment.setExam(exam);

        Student student = new Student();
        Cell rollNumberCell = row.getCell(0);
        if (rollNumberCell != null && rollNumberCell.getCellType() == CellType.NUMERIC) {
            studentEnrollment.setRollNumber(String.valueOf((int) rollNumberCell.getNumericCellValue()));
        }

        Cell nameCell = row.getCell(1);
        if (nameCell != null && nameCell.getCellType() == CellType.STRING) {
            student.setName(nameCell.getStringCellValue());
        }

        Class studentClass = parseClass(row.getCell(2), school);
        studentEnrollment.setClassName(studentClass);

        Cell sectionCell = row.getCell(3);
        if (sectionCell != null && sectionCell.getCellType() == CellType.STRING) {
            studentEnrollment.setSection(sectionCell.getStringCellValue());
        }

        Cell genderCell = row.getCell(4);
        if (genderCell != null && genderCell.getCellType() == CellType.STRING) {
            student.setGender(genderCell.getStringCellValue());
        }

        Cell dobCell = row.getCell(5);
        if (dobCell != null) {
            if (dobCell.getCellType() == CellType.NUMERIC) {
                student.setDateOfBirth(dobCell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            } else if (dobCell.getCellType() == CellType.STRING) {
                String dobString = dobCell.getStringCellValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                try {
                    student.setDateOfBirth(LocalDate.parse(dobString, formatter));
                } catch (DateTimeParseException e) {
                    log.error("Error parsing date of birth: {}", dobString, e);
                }
            }
        }

        student.setAge(student.getDateOfBirth().until(LocalDate.now()).getYears());

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
        Cell heightCell = row.getCell(6);
        Cell weightCell = row.getCell(7);
        if (heightCell != null && heightCell.getCellType() == CellType.NUMERIC) {
            BigDecimal height = BigDecimal.valueOf(heightCell.getNumericCellValue());
            physicalReport.setHeight(height);
        }

        if (weightCell != null && weightCell.getCellType() == CellType.NUMERIC) {
            BigDecimal weight = BigDecimal.valueOf(weightCell.getNumericCellValue());
            physicalReport.setWeight(weight);
        }

        log.info("Height: {}, Weight: {}", physicalReport.getHeight(), physicalReport.getWeight());

        // Calculate BMI
        BigDecimal bmi = new BigDecimal(bmiCalculator.calculateBMI(physicalReport.getWeight(), physicalReport.getHeight()));
        log.info("BMI: {}", bmi);
        physicalReport.setBmi(bmi);

        // Calculate Percentile
        Double ageInMonths = calculateAgeInMonths(studentEnrollment.getStudent().getDateOfBirth());
        BMIPercentile percentile = bmiCalculator.findPercentile(
                studentEnrollment.getStudent().getGender().equalsIgnoreCase("male") ? 1 : 2,
                ageInMonths,
                bmi
        );
        log.info("Age: {}, Gender: {}, BMI: {}", ageInMonths, studentEnrollment.getStudent().getGender(), bmi);

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

    private Double calculateAgeInMonths(LocalDate dateOfBirth) {
        Period period = Period.between(dateOfBirth, LocalDate.now());
        return period.getYears() * 12 + period.getMonths() + period.getDays() / 30.0;
    }

    private void processPhysicalTestData(Row row, List<String> physicalTestHeader, PhysicalReport physicalReport) {
        for (int j = 8; j < row.getPhysicalNumberOfCells(); j++) {
            Cell testCell = row.getCell(j);
            if (testCell != null && testCell.getCellType() == CellType.NUMERIC) {
                String testName = physicalTestHeader.get(j - 8);
                PhysicalTest physicalTest = physicalTestRepository.findByName(testName).orElse(null);
                if (physicalTest != null) {
                    savePhysicalTestPerformance(physicalReport, row, j, physicalTest);
                }
            }
        }
    }


    private void savePhysicalTestPerformance(PhysicalReport physicalReport, Row row, int j, PhysicalTest physicalTest) {
        // Create and save performance record
//        PhysicalTestPerformance performance = new PhysicalTestPerformance();
//        performance.setPhysicalReport(physicalReport);
//        performance.setPhysicalTest(physicalTest);
//        physicalTestPerformanceRepository.save(performance);  // Ensure this is being saved correctly

        // Create and save the metric
        PhysicalTestPerformanceMetric metric = new PhysicalTestPerformanceMetric();
        metric.setPhysicalReport(physicalReport);
        metric.setPhysicalTest(physicalTest);
        metric.setValue(BigDecimal.valueOf(row.getCell(j).getNumericCellValue()));
        physicalTestPerformanceMetricRepository.save(metric);  // Ensure this is being saved correctly

        // Add the metric to the report's list
        physicalReport.getPerformanceMetrics().add(metric);
    }


}
