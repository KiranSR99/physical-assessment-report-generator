package io.github.kiransr99.parg.controller;

import io.github.kiransr99.parg.constant.URL_CONSTANTS;
import io.github.kiransr99.parg.controller.base.BaseController;
import io.github.kiransr99.parg.dto.GlobalApiResponse;
import io.github.kiransr99.parg.dto.response.ExcelResponse;
import io.github.kiransr99.parg.entity.BMIData;
import io.github.kiransr99.parg.service.ExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(URL_CONSTANTS.EXCEL_URL)
@RequiredArgsConstructor
public class ExcelController extends BaseController {
    private final ExcelService excelService;

    @PostMapping("/saveExcelData/{examId}")
    public ResponseEntity<GlobalApiResponse<ExcelResponse>> saveExcelData(@PathVariable("examId") Long schoolId, @RequestParam("file") MultipartFile file) throws IOException {
        return successResponse(excelService.saveExcelData(schoolId, file), "Excel data saved successfully");
    }

}
