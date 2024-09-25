package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.StudentEnrollment;

public class StudentEnrollmentResponse {

    public StudentResponse student;
    private ClassResponse className;
//    public SectionResponse section;
    public ExamResponse exam;
    public String rollNumber;
    public String section;
    public StudentEnrollmentResponse(StudentEnrollment savedStudentEnrollment) {
        this.student = new StudentResponse(savedStudentEnrollment.getStudent());
//        this.section = new SectionResponse(savedStudentEnrollment.getSection());
        this.exam = new ExamResponse(savedStudentEnrollment.getExam());
        this.rollNumber = savedStudentEnrollment.getRollNumber();
        this.section = savedStudentEnrollment.getSection();
        this.className = new ClassResponse(savedStudentEnrollment.getClassName());
    }
}
