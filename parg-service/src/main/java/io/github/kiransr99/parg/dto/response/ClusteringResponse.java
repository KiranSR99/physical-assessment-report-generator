package io.github.kiransr99.parg.dto.response;

import io.github.kiransr99.parg.entity.ClusteringStudent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClusteringResponse {
    private List<List<ClusteringStudent>> clusters;
}
