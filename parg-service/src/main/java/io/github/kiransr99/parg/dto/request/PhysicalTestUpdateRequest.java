package io.github.kiransr99.parg.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysicalTestUpdateRequest {
    private String physicalTestName;
    private String physicalTestDescription;
    private String physicalTestUnit;
}
