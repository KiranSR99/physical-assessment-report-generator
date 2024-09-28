package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.request.StudentRequest;
import io.github.kiransr99.parg.dto.request.StudentUpdateRequest;
import io.github.kiransr99.parg.dto.response.GameResponse;
import io.github.kiransr99.parg.dto.response.StudentCompleteDataResponse;
import io.github.kiransr99.parg.dto.response.StudentResponse;
import io.github.kiransr99.parg.entity.Exam;
import io.github.kiransr99.parg.entity.School;
import io.github.kiransr99.parg.entity.Student;
import io.github.kiransr99.parg.entity.StudentEnrollment;
import io.github.kiransr99.parg.repository.ExamRepository;
import io.github.kiransr99.parg.repository.SchoolRepository;
import io.github.kiransr99.parg.repository.StudentEnrollmentRepository;
import io.github.kiransr99.parg.repository.StudentRepository;
import io.github.kiransr99.parg.service.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudentImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final SchoolRepository schoolRepository;
    private final ExamRepository examRepository;
    private final StudentEnrollmentRepository studentEnrollmentRepository;

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
    public List<StudentResponse> saveStudents(List<StudentRequest> studentRequests) {
        log.info("Saving multiple students: {}", studentRequests);

        // Map the StudentRequest objects to Student entities and save them
        List<Student> students = studentRequests.stream()
                .map(request -> {
                    Student student = new Student();
                    student.setName(request.getName());
                    student.setDateOfBirth(request.getDateOfBirth());
                    student.setAge(request.getAge());
                    student.setGender(request.getGender());
                    return student;
                })
                .toList();

        // Save all the students at once using saveAll
        List<Student> savedStudents = studentRepository.saveAll(students);

        // Map the saved entities to StudentResponse and return the list
        return savedStudents.stream()
                .map(StudentResponse::new)
                .collect(Collectors.toList());
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
    public List<StudentResponse> getAllStudentsByExamId(Long examId) {
        List<StudentEnrollment> enrollments = studentEnrollmentRepository.findByExamId(examId);
        return enrollments.stream()
                .map( enrollment -> {
                    Student student = enrollment.getStudent();
                    return new StudentResponse(student);
                }).collect(Collectors.toList());
    }

    @Override
    public List<StudentResponse> getAllStudentsByClassId(Long classId) {
        List<StudentEnrollment> enrollments = studentEnrollmentRepository.findByClassNameId(classId);
        return enrollments.stream()
                .map(enrollment -> {
                    Student student = enrollment.getStudent();
                    return new StudentResponse(student);
                }).collect(Collectors.toList());
    }

    @Override
    public StudentResponse updateStudent(Long id, StudentUpdateRequest studentUpdateRequest) {
        log.info("Updating student with id: {}", id);
        Student student = studentRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.STUDENT_NOT_FOUND)
        );
        student.setName(studentUpdateRequest.getName());
        student.setDateOfBirth(studentUpdateRequest.getDateOfBirth());
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

    @Override
    public List<StudentResponse> uploadExcel(Long schoolId, MultipartFile file) throws IOException {
        School school = schoolRepository.findById(schoolId).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.SCHOOL_NOT_FOUND)
        );
        List<StudentResponse> studentResponses = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                StudentRequest studentRequest = new StudentRequest();
                studentRequest.setName(row.getCell(0).getStringCellValue());
                studentRequest.setDateOfBirth(row.getCell(2).getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate());
                studentRequest.setAge((int) row.getCell(3).getNumericCellValue());
                studentRequest.setGender(row.getCell(4).getStringCellValue());

                StudentResponse studentResponse = saveStudent(studentRequest);
                studentResponses.add(studentResponse);
            }
        } catch (IOException e) {
            log.error("Error reading Excel file", e);
            throw e;
        }
        return studentResponses;
    }

    @Override
    public List<StudentCompleteDataResponse> getStudentCompleteDataByClassId(Long classId) {
        log.info("Fetching complete data for students in class id: {}", classId);

        // Retrieve raw data from the repository
        List<Object[]> results = studentRepository.findStudentCompleteDataByClassId(classId);

        // Group the results by studentId (first element in the result array)
        return results.stream()
                .collect(Collectors.groupingBy(row -> (Long) row[0])) // Group by studentId
                .values().stream()
                .map(group -> {
                    Object[] firstRow = group.get(0); // Get the first row for static data
                    List<GameResponse> games = group.stream()
                            .map(row -> new GameResponse((String) row[14], (BigDecimal) row[15])) // map to GameResponse
                            .toList();

                    return new StudentCompleteDataResponse(
                            (Long) firstRow[0],  // studentId
                            (String) firstRow[1],  // roll number
                            (String) firstRow[2],  // name
                            (String) firstRow[3],  // class name
                            (String) firstRow[4],  // section
                            ((Date) firstRow[5]),  // date of birth
                            (int) firstRow[6],  // age
                            (String) firstRow[7],  // gender
                            (BigDecimal) firstRow[8],  // height
                            (BigDecimal) firstRow[9],  // weight
                            (BigDecimal) firstRow[10],  // bmi
                            (String) firstRow[11],  // bmi level
                            (String) firstRow[12],  // percentile
                            (String) firstRow[13],  // comment
                            games  // games (physical tests)
                    );
                })
                .toList();
    }




}
