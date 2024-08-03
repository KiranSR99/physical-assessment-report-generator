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
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalTestPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "physical_report_id")
    private PhysicalReport physicalReport;

    @ManyToOne
    @JoinColumn(name = "physical_test_id")
    private PhysicalTest physicalTest;

    @OneToMany(mappedBy = "physicalTestPerformance")
    private List<PhysicalTestPerformanceMetric> physicalTestPerformanceMetricList;
}
