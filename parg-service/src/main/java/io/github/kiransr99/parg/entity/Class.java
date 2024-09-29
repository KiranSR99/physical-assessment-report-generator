package io.github.kiransr99.parg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @OneToMany(mappedBy = "className")
    private List<Section> sections = new ArrayList<>();

    private boolean status = true;

    // Many-to-Many relationship with PhysicalTest
    @ManyToMany
    @JoinTable(
            name = "class_physical_test", // Join table name
            joinColumns = @JoinColumn(name = "class_id"), // Foreign key for Class entity
            inverseJoinColumns = @JoinColumn(name = "physical_test_id") // Foreign key for PhysicalTest entity
    )
    private List<PhysicalTest> physicalTests = new ArrayList<>();
}
