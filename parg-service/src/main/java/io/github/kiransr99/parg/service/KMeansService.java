package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.ClusteringRequest;
import io.github.kiransr99.parg.dto.response.ClusteringResponse;
import io.github.kiransr99.parg.entity.ClusteringStudent;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KMeansService {

    public ClusteringResponse kMeansClustering(ClusteringRequest clusteringRequest) {
        // Extract `k`, `maxIterations`, and `students` from the request
        List<ClusteringStudent> students = clusteringRequest.getStudents();
        int k = clusteringRequest.getK();
        int maxIterations = clusteringRequest.getMaxIterations();

        // Validate the inputs
        if (students == null || students.isEmpty()) {
            throw new IllegalArgumentException("The list of students cannot be null or empty.");
        }
        if (k <= 0 || maxIterations <= 0) {
            throw new IllegalArgumentException("Invalid values for 'k' or 'maxIterations'.");
        }

        // Step 1: Extract only BMI values for clustering
        List<Double> bmiValues = new ArrayList<>();
        for (ClusteringStudent student : students) {
            bmiValues.add(student.getBmi());
        }

        // Step 2: Initialize centroids randomly
        Random random = new Random();
        double[] centroids = new double[k];
        for (int i = 0; i < k; i++) {
            centroids[i] = bmiValues.get(random.nextInt(bmiValues.size()));
        }

        int[] clusterAssignments = new int[bmiValues.size()];
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            // Step 3: Assign each BMI value to the nearest centroid
            clusterAssignments = assignClusters(bmiValues, centroids);

            // Step 4: Update centroids
            double[] newCentroids = updateCentroids(bmiValues, clusterAssignments, k);
            if (Arrays.equals(newCentroids, centroids)) {
                break; // Stop if centroids don't change
            }
            centroids = newCentroids;
        }

        // Step 5: Group students into clusters
        List<List<ClusteringStudent>> clusters = groupStudentsByClusters(students, clusterAssignments, k);

        // Return the ClusteringResponse
        return new ClusteringResponse(clusters);
    }

    private int[] assignClusters(List<Double> bmiValues, double[] centroids) {
        int[] clusterAssignments = new int[bmiValues.size()];
        for (int i = 0; i < bmiValues.size(); i++) {
            double minDistance = Double.MAX_VALUE;
            int clusterIndex = -1;
            for (int j = 0; j < centroids.length; j++) {
                double distance = Math.abs(bmiValues.get(i) - centroids[j]);
                if (distance < minDistance) {
                    minDistance = distance;
                    clusterIndex = j;
                }
            }
            clusterAssignments[i] = clusterIndex;
        }
        return clusterAssignments;
    }

    private double[] updateCentroids(List<Double> bmiValues, int[] clusterAssignments, int k) {
        double[] newCentroids = new double[k];
        int[] pointsPerCluster = new int[k];

        for (int i = 0; i < bmiValues.size(); i++) {
            int cluster = clusterAssignments[i];
            pointsPerCluster[cluster]++;
            newCentroids[cluster] += bmiValues.get(i);
        }

        for (int i = 0; i < k; i++) {
            if (pointsPerCluster[i] > 0) {
                newCentroids[i] /= pointsPerCluster[i];
            }
        }
        return newCentroids;
    }

    private List<List<ClusteringStudent>> groupStudentsByClusters(List<ClusteringStudent> students, int[] clusterAssignments, int k) {
        List<List<ClusteringStudent>> clusters = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            clusters.add(new ArrayList<>());
        }
        for (int i = 0; i < students.size(); i++) {
            clusters.get(clusterAssignments[i]).add(students.get(i));
        }
        return clusters;
    }
}
