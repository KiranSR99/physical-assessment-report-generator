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
public class PhysicalTest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String unit;

    // One-to-Many relationship for performance metrics
    @OneToMany(mappedBy = "physicalTest", cascade = CascadeType.ALL)
    private List<PhysicalTestPerformanceMetric> performanceMetrics;

    // Many-to-Many relationship with Class
    @ManyToMany(mappedBy = "physicalTests") // This maps the relationship to the owning side (Class entity)
    private List<Class> classes;
}
