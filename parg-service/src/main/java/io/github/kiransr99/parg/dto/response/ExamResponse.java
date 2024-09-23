package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.Exam;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ExamResponse {
    private Long id;
    private Integer year;
    private String examName;

    public ExamResponse(Exam exam) {
        this.id = exam.getId();
        this.year = exam.getYear();
        this.examName = exam.getExamName();
    }
}
