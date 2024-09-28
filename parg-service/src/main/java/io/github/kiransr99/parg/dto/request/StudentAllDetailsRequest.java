package io.github.kiransr99.parg.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAllDetailsRequest {
    private Long classId;
    private Long examId;
    private String rollNumber;
    private String name;
    private String className;
    private String section;
    private LocalDate dateOfBirth;
    private Integer age;
    private String gender;
    private BigDecimal height;
    private BigDecimal weight;
    private BigDecimal bmi;
    private String bmiLevel;
    private String percentile;
    private String comment;
    private List<GameRequest> games;
}
