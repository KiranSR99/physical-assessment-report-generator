package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.Class;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassResponse {
    private Long id;
    private String name;
    private SchoolResponse school;
    private ExamResponse exam;
    private List<SectionResponse> sections;

    public ClassResponse(Class savedClass) {
        this.id = savedClass.getId();
        this.name = savedClass.getName();
        this.school = new SchoolResponse(savedClass.getSchool());
        this.exam = new ExamResponse(savedClass.getExam());
        if (savedClass.getSections() != null) {
            savedClass.getSections().forEach(section -> this.sections.add(new SectionResponse(section)));
        }
    }

}
