package io.github.kiransr99.parg.constant;

public class SYSTEM_MESSAGE {
    private SYSTEM_MESSAGE() {}
    public static final String SCHOOL_SAVED = "School saved successfully.";
    public static final String SCHOOL_FETCHED = "All school fetched successfully.";
    public static final String SCHOOL_UPDATED = "School updated successfully.";
    public static final String SCHOOL_DELETED = "School deleted successfully.";

    // CLASS RELATED
    public static final String CLASS_SAVED = "Class saved successfully.";
    public static final String CLASS_FETCHED = "All class fetched successfully.";
    public static final String CLASS_UPDATED = "Class updated successfully.";
    public static final String CLASS_DELETED = "Class deleted successfully.";
    public static final Exception SCHOOL_NOT_FOUND = new Exception("School not found");
    public static final Exception CLASS_NOT_FOUND = new Exception("Class not found");


    // Student Message
    public static final String STUDENT_SAVED = "Student saved successfully.";
    public static final String STUDENT_FETCHED = "All student fetched successfully.";
    public static final String STUDENT_UPDATED = "Student updated successfully.";
    public static final String STUDENT_DELETED = "Student deleted successfully.";
    public static final String STUDENT_NOT_FOUND = "Student not found.";
    public static final String STUDENT_FETCHED_BY_ID = "Student fetched successfully.";

    // Academic Year Message
    public static final String ACADEMIC_YEAR_SAVED = "Academic year saved successfully.";
    public static final String ACADEMIC_YEAR_FETCHED = "All academic year fetched successfully.";
    public static final String ACADEMIC_YEAR_UPDATED = "Academic year updated successfully.";
    public static final String ACADEMIC_YEAR_DELETED = "Academic year deleted successfully.";
    public static final String ACADEMIC_YEAR_NOT_FOUND = "Academic year not found.";
    public static final String ACADEMIC_YEAR_FETCHED_BY_ID = "Academic year fetched successfully.";
}
