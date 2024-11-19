package io.github.kiransr99.parg.enums;
import lombok.Getter;

@Getter
public enum BMIPercentile {

    LESS_THAN_5TH("Less than 5th percentile"),
    P5_TO_P84("5th to 84th percentile"),
    P85_TO_P94("85th to 94th percentile"),
    GREATER_THAN_95TH("95th percentile or higher"),
    UNKNOWN("Unknown percentile range");

    private final String description;

    BMIPercentile(String description) {
        this.description = description;
    }

}