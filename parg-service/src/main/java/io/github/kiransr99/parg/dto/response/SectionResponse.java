package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.Section;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectionResponse {
    private Long id;
    private String name;

    public SectionResponse(Section savedSection) {
        this.id = savedSection.getId();
        this.name = savedSection.getName();
    }
}
