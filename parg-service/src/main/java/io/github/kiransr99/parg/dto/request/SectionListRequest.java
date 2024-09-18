package io.github.kiransr99.parg.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SectionListRequest {
    private Long classId;
    private List<SectionRequest> section;
}