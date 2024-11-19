package io.github.kiransr99.parg.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentCompleteDataResponse {
    private Long studentId;
    private String rollNumber;
    private String name;
    private String className;
    private String section;
    private Date dateOfBirth;
    private int age;
    private String gender;
    private BigDecimal height;
    private BigDecimal weight;
    private BigDecimal bmi;
    private String bmiLevel;
    private String percentile;
    private String comment;
    private List<GameResponse> games;
}

