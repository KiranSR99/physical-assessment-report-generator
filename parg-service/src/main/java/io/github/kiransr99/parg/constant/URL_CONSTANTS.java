package io.github.kiransr99.parg.constant;

public class URL_CONSTANTS {

    public static final String STUDENT_ENROLLMENT_URL = "/parg/api/v1/studentEnrollment";
    public static final String SAVE_STUDENT_ENROLLMENT = "/saveStudentEnrollment";
    public static final String UPDATE_STUDENT_ENROLLMENT = "/updateStudentEnrollment/{studentEnrollmentId}";
    public static final String GET_STUDENT_ENROLLMENT_BY_ID = "/getStudentEnrollmentById/{studentEnrollmentId}";
    public static final String DELETE_STUDENT_ENROLLMENT = "/deleteStudentEnrollment/{studentEnrollmentId}";
    public static final String GET_ALL_STUDENT_ENROLLMENTS = "/getAllStudentEnrollments";


    //    physicalTestPerformanceMetric
    public static final String PHYSICAL_TEST_PERFORMANCE_METRIC_URL = "/parg/api/v1/physicalTestPerformanceMetric";
    public static final String SAVE_PHYSICAL_TEST_PERFORMANCE_METRIC = "/savePhysicalTestPerformanceMetric";
    public static final String UPDATE_PHYSICAL_TEST_PERFORMANCE_METRIC = "/updatePhysicalTestPerformanceMetric/{physicalTestPerformanceMetricId}";
    public static final String GET_PHYSICAL_TEST_PERFORMANCE_METRIC_BY_ID = "/getPhysicalTestPerformanceMetricById/{physicalTestPerformanceMetricId}";
    public static final String DELETE_PHYSICAL_TEST_PERFORMANCE_METRIC = "/deletePhysicalTestPerformanceMetric/{physicalTestPerformanceMetricId}";
    public static final String GET_ALL_PHYSICAL_TEST_PERFORMANCE_METRICS = "/getAllPhysicalTestPerformanceMetrics";


    private URL_CONSTANTS() {
    }

    // SCHOOL URLS
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

    // CLASS URLS
    public static final String CLASS_URL = "/parg/api/v1/class";
    public static final String GET_ALL_CLASSES = "/getAllClasses";
    public static final String GET_CLASSES_BY_SCHOOL_ID = "/getClassesBySchoolId/{schoolId}";
    public static final String SAVE_CLASS = "/saveClass";
    public static final String UPDATE_CLASS = "/updateClass/{classId}";
    public static final String GET_CLASS_BY_ID = "/getClassById/{classId}";
    public static final String DELETE_CLASS = "/deleteClass/{classId}";

    // SECTION URLS
    public static final String SECTION_URL = "/parg/api/v1/section";
    public static final String GET_ALL_SECTIONS = "/getAllSections";
    public static final String SAVE_SECTION = "/saveSection";
    public static final String UPDATE_SECTION = "/updateSection/{sectionId}";
    public static final String GET_SECTION_BY_ID = "/getSectionById/{sectionId}";
    public static final String DELETE_SECTION = "/deleteSection/{sectionId}";

    // Physical Test URL
    public static final String PHYSICAL_TEST_URL = "/parg/api/v1/physicalTest";
    public static final String SAVE_PHYSICAL_TEST = "/savePhysicalTest";
    public static final String UPDATE_PHYSICAL_TEST = "/updatePhysicalTest/{physicalTestId}";
    public static final String GET_PHYSICAL_TEST_BY_ID = "/getPhysicalTestById/{physicalTestId}";
    public static final String DELETE_PHYSICAL_TEST = "/deletePhysicalTest/{physicalTestId}";
    public static final String GET_ALL_PHYSICAL_TESTS = "/getAllPhysicalTests";

    // Physical Test Metric URL
    public static final String PHYSICAL_TEST_METRIC_URL = "/parg/api/v1/physicalTestMetric";
    public static final String SAVE_PHYSICAL_TEST_METRIC = "/savePhysicalTestMetric";
    public static final String UPDATE_PHYSICAL_TEST_METRIC = "/updatePhysicalTestMetric/{physicalTestMetricId}";
    public static final String GET_PHYSICAL_TEST_METRIC_BY_ID = "/getPhysicalTestMetricById/{physicalTestMetricId}";
    public static final String DELETE_PHYSICAL_TEST_METRIC = "/deletePhysicalTestMetric/{physicalTestMetricId}";
    public static final String GET_ALL_PHYSICAL_TEST_METRICS = "/getAllPhysicalTestMetrics";

    // Physical Report URL
    public static final String PHYSICAL_REPORT_URL = "/parg/api/v1/physicalReport";
    public static final String SAVE_PHYSICAL_REPORT = "/savePhysicalReport";
    public static final String UPDATE_PHYSICAL_REPORT = "/updatePhysicalReport/{physicalReportId}";
    public static final String GET_PHYSICAL_REPORT_BY_ID = "/getPhysicalReportById/{physicalReportId}";
    public static final String DELETE_PHYSICAL_REPORT = "/deletePhysicalReport/{physicalReportId}";
    public static final String GET_ALL_PHYSICAL_REPORTS = "/getAllPhysicalReports";
}
