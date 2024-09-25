package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.response.ExcelResponse;
import io.github.kiransr99.parg.entity.*;
import io.github.kiransr99.parg.entity.Class;
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
    private final SchoolRepository schoolRepository;
    private final ClassRepository classRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;
    private final SectionRepository sectionRepository;
    private final PhysicalReportRepository physicalReportRepository;

    @Override
    public ExcelResponse saveExcelData(Long schoolId, MultipartFile file) throws IOException {
        School school = schoolRepository.findById(schoolId).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.SCHOOL_NOT_FOUND)
        );
        List<StudentEnrollment> studentEnrollmentList = new ArrayList<>();
        List<PhysicalReport> physicalReportList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;  // Skip header row
                StudentEnrollment studentEnrollment = new StudentEnrollment();
                Student student = new Student();
//                Class studentClass = classRepository.findByNameAndSchool(getCellValue(row.getCell(2)), school).orElseThrow(
//                        () -> new EntityNotFoundException(SYSTEM_MESSAGE.CLASS_NOT_FOUND)
//                );
                log.info("Searching for class with name: {} and school: {}", getCellValue(row.getCell(2)), school.getName());

                Class studentClass = classRepository.findByNameAndSchool(getCellValue(row.getCell(2)), school).orElseThrow(
                        () -> new EntityNotFoundException(SYSTEM_MESSAGE.CLASS_NOT_FOUND)
                );
                log.info("Searching for class with name: {} and school: {}", getCellValue(row.getCell(2)), school.getName());
                PhysicalReport physicalReport = new PhysicalReport();

                // Handle Roll Number (Cell 0)
                studentEnrollment.setRollNumber(getCellValue(row.getCell(0)));

                // Handle Name (Cell 1)
                student.setFirstName(getCellValue(row.getCell(1)));
                student.setLastName(getCellValue(row.getCell(1)));
                studentEnrollment.setClassName(studentClass);

                // Handle Section (Cell 3 and 4)
                Section section = studentClass.getSections()
                        .stream()
                        .filter(s -> s.getName().equals(getCellValue(row.getCell(4))))
                        .findFirst()
                        .orElseGet(() -> {
                            Section newSection = new Section();
                            newSection.setName(getCellValue(row.getCell(3)));
                            newSection.setClassName(studentClass);
                            return sectionRepository.save(newSection);
                        });
                studentEnrollment.setSection(section);

                // Handle Gender (Cell 4)
                student.setGender(getCellValue(row.getCell(4)));

                // Handle DOB (Cell 5)
                if (row.getCell(5).getCellType() == CellType.NUMERIC) {
                    student.setDateOfBirth(row.getCell(5).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                    student.setAge(calculateAge(student.getDateOfBirth()));
                }

                // Handle Height (Cell 6) and Weight (Cell 7)
                physicalReport.setHeight(getNumericCellValue(row.getCell(6)));
                physicalReport.setWeight(getNumericCellValue(row.getCell(7)));

                if (physicalReport.getHeight() != null && physicalReport.getWeight() != null) {
                    physicalReport.setBmi(physicalReport.getWeight().divide(
                            physicalReport.getHeight().multiply(physicalReport.getHeight()), 2, RoundingMode.HALF_UP));
                }

                // Save all the data
                studentRepository.save(student);
                studentEnrollment.setStudent(student);
                studentEnrollmentRepository.save(studentEnrollment);
                physicalReport.setStudentEnrollment(studentEnrollment);
                physicalReportRepository.save(physicalReport);

                studentEnrollmentList.add(studentEnrollment);
                physicalReportList.add(physicalReport);
            }
        } catch (IOException e) {
            log.error("Error reading Excel file", e);
            throw e;
        }
        return new ExcelResponse(studentEnrollmentList, physicalReportList);
    }

    private Integer calculateAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    // Helper method to handle different cell types
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());  // Handle numeric as String if required
        } else {
            return null;
        }
    }

    // Helper method for numeric values
    private BigDecimal getNumericCellValue(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC) {
            return null;
        }
        return BigDecimal.valueOf(cell.getNumericCellValue());
    }

}