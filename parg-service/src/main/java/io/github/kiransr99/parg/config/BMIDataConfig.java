package io.github.kiransr99.parg.config;

import io.github.kiransr99.parg.entity.BMIData;
import io.github.kiransr99.parg.repository.BMIDataRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BMIDataConfig {

    private final BMIDataRepository bmiDataRepository;

    @PostConstruct
    public void initDatabase() {
        if (bmiDataRepository.count() == 0) {
            List<BMIData> bmiDataList = new ArrayList<>();
            bmiDataList.addAll(parseCSV("/Excel/Boys.csv", 1)); // For boys
            bmiDataList.addAll(parseCSV("/Excel/Girls.csv", 2)); // For girls
            bmiDataRepository.saveAll(bmiDataList);
        }
    }

    private List<BMIData> parseCSV(String filePath, int sex) {
        List<BMIData> bmiDataList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filePath))))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                BMIData bmiData = getBmiData(values, sex);
                bmiDataList.add(bmiData);
            }
        } catch (Exception e) {
            log.error("Error reading CSV file: " + filePath, e);
        }
        return bmiDataList;
    }

    private BMIData getBmiData(String[] values, int sex) {
        BMIData bmiData = new BMIData();
        bmiData.setSex(sex);
        bmiData.setAge(Double.parseDouble(values[0]));
        bmiData.setP3(Double.parseDouble(values[1]));
        bmiData.setP5(Double.parseDouble(values[2]));
        bmiData.setP10(Double.parseDouble(values[3]));
        bmiData.setP25(Double.parseDouble(values[4]));
        bmiData.setP50(Double.parseDouble(values[5]));
        bmiData.setP75(Double.parseDouble(values[6]));
        bmiData.setP85(Double.parseDouble(values[7]));
        bmiData.setP90(Double.parseDouble(values[8]));
        bmiData.setP95(Double.parseDouble(values[9]));
        bmiData.setP97(Double.parseDouble(values[10]));
        return bmiData;
    }
}
