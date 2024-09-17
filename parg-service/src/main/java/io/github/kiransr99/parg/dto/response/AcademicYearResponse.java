package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.AcademicYear;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AcademicYearResponse {
    private Long id;
    private Integer year;

    public AcademicYearResponse(Long id, Integer year) {
        this.id = id;
        this.year = year;
    }

    public AcademicYearResponse(AcademicYear academicYear) {
        this.id = academicYear.getId();
        this.year = academicYear.getYear();
    }
}
