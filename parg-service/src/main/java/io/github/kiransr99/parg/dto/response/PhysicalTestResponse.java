package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.PhysicalTest;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhysicalTestResponse {
    private Long physicalTestId;
    private String physicalTestName;
    private String physicalTestDescription;

    public PhysicalTestResponse(PhysicalTest physicalTest) {
        this.physicalTestId = physicalTest.getId();
        this.physicalTestName = physicalTest.getName();
        this.physicalTestDescription = physicalTest.getDescription();
    }
}
