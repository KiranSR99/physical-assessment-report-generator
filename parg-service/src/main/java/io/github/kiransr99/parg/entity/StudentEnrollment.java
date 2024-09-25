package io.github.kiransr99.parg.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentEnrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

//    @ManyToOne
//    @JoinColumn(name = "section_id")
//    private Section section;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class className;

    @ManyToOne
    @JoinColumn(name = "academic_year_id")
    private Exam exam;

    private String rollNumber;
    private String section;

    @OneToMany(mappedBy = "studentEnrollment")
    @JsonManagedReference
    private List<PhysicalReport> physicalReports;
}
