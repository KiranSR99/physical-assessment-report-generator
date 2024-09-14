package io.github.kiransr99.parg.service;

import io.github.kiransr99.parg.dto.request.AcademicYearRequest;
import io.github.kiransr99.parg.dto.request.AcademicYearUpdateRequest;
import io.github.kiransr99.parg.dto.response.AcademicYearResponse;

public interface AcademicYearService {
    AcademicYearResponse createAcademicYear(AcademicYearRequest academicYearRequest);
    AcademicYearResponse getAcademicYear(Long id);
    AcademicYearResponse updateAcademicYear(Long id, AcademicYearUpdateRequest academicYearUpdateRequest);
    void deleteAcademicYear(Long id);
}
