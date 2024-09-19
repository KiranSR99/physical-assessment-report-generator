package io.github.kiransr99.parg.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcademicYearUpdateRequest {
    private Integer year;
    private String examName;
}
