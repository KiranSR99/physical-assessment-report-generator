package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.entity.ClusteringStudent;

import java.util.ArrayList;
import java.util.List;

public class NormalizerService {

    public static double normalize(double value, double min, double max) {
        return (value - min) / (max - min);
    }

    public static List<double[]> normalizeData(List<ClusteringStudent> students) {
        // Calculate min and max for BMI and Age
        double minBmi = students.stream().mapToDouble(ClusteringStudent::getBmi).min().orElse(0);
        double maxBmi = students.stream().mapToDouble(ClusteringStudent::getBmi).max().orElse(1);

        double minAge = students.stream().mapToInt(ClusteringStudent::getAge).min().orElse(0);
        double maxAge = students.stream().mapToInt(ClusteringStudent::getAge).max().orElse(1);

        List<double[]> normalizedData = new ArrayList<>();
        for (ClusteringStudent student : students) {
            double[] normalized = new double[3]; // BMI, Age, Gender
            normalized[0] = normalize(student.getBmi(), minBmi, maxBmi);
            normalized[1] = normalize(student.getAge(), minAge, maxAge);
            normalized[2] = student.getGender().equalsIgnoreCase("male") ? 0 : 1; // Male = 0, Female = 1
            normalizedData.add(normalized);
        }

        return normalizedData;
    }
}

