package io.github.kiransr99.parg.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassRequest {
    private Long schoolId;
    private List<ClassListRequest> classes;
}
