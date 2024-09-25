package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.response.ExcelResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ExcelService {
    ExcelResponse saveExcelData(Long examId, MultipartFile file) throws IOException;
}
