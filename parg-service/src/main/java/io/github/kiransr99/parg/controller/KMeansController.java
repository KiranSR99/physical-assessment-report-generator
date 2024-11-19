package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.request.ClusteringRequest;
import io.github.kiransr99.parg.dto.response.ClusteringResponse;
import io.github.kiransr99.parg.service.KMeansService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parg/api/v1/clustering")
public class KMeansController extends BaseController {

    private final KMeansService kMeansService;

    @PostMapping("/kmeans")
    public ResponseEntity<GlobalApiResponse<ClusteringResponse>> clusterStudents(@RequestBody ClusteringRequest request) {
        return successResponse(kMeansService.kMeansClustering(request), "Students clustered successfully.");
    }

}
