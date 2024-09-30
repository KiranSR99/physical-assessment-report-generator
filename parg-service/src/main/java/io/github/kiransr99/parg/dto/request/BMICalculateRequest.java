package io.github.kiransr99.parg.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BMICalculateRequest {
    private double age;
    private int gender;
    private double height;
    private double weight;
}
