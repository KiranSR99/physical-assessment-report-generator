package io.github.kiransr99.parg.constant;

public class URL_CONSTANTS {

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
    public static final String UPDATE_SECTION = "/updateSection";
    public static final String GET_SECTION_BY_ID = "/getSectionById/{sectionId}";
    public static final String DELETE_SECTION = "/deleteSection/{sectionId}";
}
