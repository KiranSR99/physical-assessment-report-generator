package io.github.kiransr99.parg;

import io.github.kiransr99.parg.config.FilePathProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(FilePathProperties.class)
public class PhysicalAssessmentReportGeneratorApplication {
	public static void main(String[] args) {
		SpringApplication.run(PhysicalAssessmentReportGeneratorApplication.class, args);
	}

}
