package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.StudentEnrollment;

public class StudentEnrollmentResponse {

    public StudentResponse student;
    private ClassResponse className;
    public SectionResponse section;
    public ExamResponse academicYear;
    public String rollNumber;
    public StudentEnrollmentResponse(StudentEnrollment savedStudentEnrollment) {
        this.student = new StudentResponse(savedStudentEnrollment.getStudent());
        this.section = new SectionResponse(savedStudentEnrollment.getSection());
        this.academicYear = new ExamResponse(savedStudentEnrollment.getExam());
        this.rollNumber = savedStudentEnrollment.getRollNumber();
        this.className = new ClassResponse(savedStudentEnrollment.getClassName());
    }
}
