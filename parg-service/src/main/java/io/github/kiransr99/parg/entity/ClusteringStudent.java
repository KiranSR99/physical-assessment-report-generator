package io.github.kiransr99.parg.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClusteringStudent {
    private String name;
    private int age;
    private String gender;
    private double bmi;
}
