package io.github.kiransr99.parg.constant;

public class URL_CONSTANTS {
    private static final String BASE_URL = "/parg/api/v1";
    public static final String SCHOOL_URL = "/parg/api/v1/school";
    public static final String GET_ALL_SCHOOLS = "/getAllSchools";
    public static final String SAVE_SCHOOL = "/saveSchool";

    public static final String UPDATE_SCHOOL = "/updateSchool";
    public static final String GET_SCHOOL_BY_ID = "/getSchoolById/{schoolId}";
    public static final String DELETE_SCHOOL = "/deleteSchool/{schoolId}";

    // Student URL
    public static final String STUDENT_URL = "/parg/api/v1/student";
    public static final String SAVE_STUDENT = "/saveStudent";
    public static final String UPDATE_STUDENT = "/updateStudent/{studentId}";
    public static final String GET_STUDENT_BY_ID = "/getStudentById/{studentId}";
    public static final String DELETE_STUDENT = "/deleteStudent/{studentId}";

    // Academic Year URL
    public static final String ACADEMIC_YEAR_URL = "/parg/api/v1/academicYear";
    public static final String SAVE_ACADEMIC_YEAR = "/saveAcademicYear";
    public static final String UPDATE_ACADEMIC_YEAR = "/updateAcademicYear/{academicYearId}";
    public static final String GET_ACADEMIC_YEAR_BY_ID = "/getAcademicYearById/{academicYearId}";
    public static final String DELETE_ACADEMIC_YEAR = "/deleteAcademicYear/{academicYearId}";

}
