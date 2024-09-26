package io.github.kiransr99.parg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalTestPerformanceMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(precision = 10, scale = 2)
    private BigDecimal value;

    @ManyToOne
    @JoinColumn(name = "physical_test_id", nullable = false)
    private PhysicalTest physicalTest; // Reference to PhysicalTest

    @ManyToOne
    @JoinColumn(name = "physical_report_id", nullable = false)
    private PhysicalReport physicalReport; // Reference to PhysicalReport
}
