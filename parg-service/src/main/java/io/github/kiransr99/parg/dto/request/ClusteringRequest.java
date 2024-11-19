package io.github.kiransr99.parg.dto.request;

import io.github.kiransr99.parg.entity.ClusteringStudent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClusteringRequest {
    private List<ClusteringStudent> students;
    private int k;
    private int maxIterations;
}
