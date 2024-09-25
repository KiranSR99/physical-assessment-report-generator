package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.response.ExcelResponse;
import io.github.kiransr99.parg.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(URL_CONSTANTS.EXCEL_URL)
@RequiredArgsConstructor
public class ExcelController extends BaseController {
    private final ExcelService excelService;

    @PostMapping("/saveExcelData/{schoolId}")
    public ResponseEntity<GlobalApiResponse<ExcelResponse>> saveExcelData(@PathVariable("schoolId") Long schoolId, @RequestParam("file") MultipartFile file) throws IOException {
        return successResponse(excelService.saveExcelData(schoolId, file), "Excel data saved successfully");
    }
}
