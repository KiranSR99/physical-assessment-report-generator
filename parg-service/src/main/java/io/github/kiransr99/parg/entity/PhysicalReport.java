package io.github.kiransr99.parg.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_enrollment_id")
    @JsonBackReference
    private StudentEnrollment studentEnrollment;

    @Column(precision = 5, scale = 2)
    private BigDecimal height;

    @Column(precision = 5, scale = 2)
    private BigDecimal weight;

    @Column(precision = 5, scale = 2)
    private BigDecimal bmi;

    private String bmiLevel;
    private String percentile;
    private String comment;

    @OneToMany(mappedBy = "physicalReport", cascade = CascadeType.ALL)
    private List<PhysicalTestPerformanceMetric> performanceMetrics;
}