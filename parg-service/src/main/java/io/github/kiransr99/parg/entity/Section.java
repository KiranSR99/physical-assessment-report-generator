package io.github.kiransr99.parg.entity;

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
public class Section {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class className;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @OneToMany(mappedBy = "section")
    private List<StudentEnrollment> studentEnrollments;

    private boolean status = true;

}
