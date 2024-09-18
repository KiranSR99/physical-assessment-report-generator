package io.github.kiransr99.parg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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

    @ManyToOne
    @JoinColumn(name = "physical_report_id")
    private PhysicalReport physicalReport;
}
