package io.github.kiransr99.parg.enums;


import lombok.Getter;


@Getter
public enum BMIPercentile {
    BELOW_3RD("Below 3rd percentile"),
    P3_TO_P5("3rd-5th percentile"),
    P5_TO_P10("5th-10th percentile"),
    P10_TO_P25("10th-25th percentile"),
    P25_TO_P50("25th-50th percentile"),
    P50_TO_P75("50th-75th percentile"),
    P75_TO_P85("75th-85th percentile"),
    P85_TO_P90("85th-90th percentile"),
    P90_TO_P95("90th-95th percentile"),
    P95_TO_P97("95th-97th percentile"),
    ABOVE_97TH("Above 97th percentile"),
    UNKNOWN("Percentile data not available");

    private final String description;

    BMIPercentile(String description) {
        this.description = description;
    }

}