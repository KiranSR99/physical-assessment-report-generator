package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.AcademicYear;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AcademicYearResponse {
    private Long id;
    private Integer year;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

//    public AcademicYearResponse(AcademicYear academicYear) {
//        this.id = academicYear.getId();
//        this.year = academicYear.getYear();
//        this.startDate = academicYear.getStartDate();
//        this.endDate = academicYear.getEndDate();
//    }

    public AcademicYearResponse(AcademicYear academicYear) {
        this.id = academicYear.getId();
        this.year = academicYear.getYear();
    }
}
