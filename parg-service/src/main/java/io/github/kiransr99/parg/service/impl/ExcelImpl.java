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

                if (row.getRowNum() == 0) continue;

                StudentEnrollment studentEnrollment = new StudentEnrollment();
                Student student = new Student();

                // Handle class column value (Cell 2)
                String className;
                if (row.getCell(2).getCellType() == CellType.NUMERIC) {
                    className = String.valueOf((int) row.getCell(2).getNumericCellValue());
                } else if (row.getCell(2).getCellType() == CellType.STRING) {
                    className = row.getCell(2).getStringCellValue().trim();
                } else {
                    throw new EntityNotFoundException("Invalid class name format");
                }

                Class studentClass = classRepository.findByNameAndSchool(className, school).orElseThrow(
                        () -> new EntityNotFoundException(SYSTEM_MESSAGE.CLASS_NOT_FOUND)
                );

                PhysicalReport physicalReport = new PhysicalReport();

                studentEnrollment.setRollNumber(String.valueOf((int) row.getCell(0).getNumericCellValue()));
                student.setName(row.getCell(1).getStringCellValue());
                studentEnrollment.setClassName(studentClass);

                // Section handling
                Section section = studentClass.getSections()
                        .stream()
                        .filter(s -> s.getName().equals(row.getCell(3).getStringCellValue()))
                        .findFirst()
                        .orElseGet(() -> {
                            Section newSection = new Section();
                            newSection.setName(row.getCell(3).getStringCellValue());
                            newSection.setClassName(studentClass);
                            return sectionRepository.save(newSection);
                        });
                studentEnrollment.setSection(section);

                student.setGender(row.getCell(4).getStringCellValue());
                student.setDateOfBirth(row.getCell(5).getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                student.setAge(calculateAge(student.getDateOfBirth()));

                physicalReport.setHeight(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()));
                physicalReport.setWeight(BigDecimal.valueOf(row.getCell(7).getNumericCellValue()));
                physicalReport.setBmi(physicalReport.getWeight().divide(physicalReport.getHeight().multiply(physicalReport.getHeight()), 2, RoundingMode.HALF_UP));

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
}
