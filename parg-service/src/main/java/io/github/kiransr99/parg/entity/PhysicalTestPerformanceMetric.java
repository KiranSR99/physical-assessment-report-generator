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

    @ManyToOne
    @JoinColumn(name = "physical_test_performace_id")
    private PhysicalTestPerformance physicalTestPerformance;

    @ManyToOne
    @JoinColumn(name = "physical_test_metric_id")
    private PhysicalTestMetric physicalTestMetric;

    @Column(precision = 10, scale = 2)
    private BigDecimal value;
}
