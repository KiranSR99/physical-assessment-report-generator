package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.StudentEnrollment;

public class StudentEnrollmentResponse {

    public StudentResponse student;
    public SectionResponse section;
    public AcademicYearResponse academicYear;
    public StudentEnrollmentResponse(StudentEnrollment savedStudentEnrollment) {
        this.student = new StudentResponse(savedStudentEnrollment.getStudent());
        this.section = new SectionResponse(savedStudentEnrollment.getSection());
        this.academicYear = new AcademicYearResponse(savedStudentEnrollment.getAcademicYear());
    }
}
