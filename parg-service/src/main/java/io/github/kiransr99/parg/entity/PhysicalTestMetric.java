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
public class PhysicalTestMetric {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "physical_test_id")
    private PhysicalTest physicalTest;

    @Column(nullable = false)
    private String name;

    private String unit;

    @OneToMany(mappedBy = "physicalTestMetric")
    private List<PhysicalTestPerformanceMetric> performanceMetrics;
}
