package io.github.kiransr99.parg.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BMIData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int sex;
    private double age;
    private double p3;
    private double p5;
    private double p10;
    private double p25;
    private double p50;
    private double p75;
    private double p85;
    private double p90;
    private double p95;
    private double p97;
}
