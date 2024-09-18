package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.constant.SYSTEM_MESSAGE;
import io.github.kiransr99.parg.dto.request.AcademicYearRequest;
import io.github.kiransr99.parg.dto.request.AcademicYearUpdateRequest;
import io.github.kiransr99.parg.dto.response.AcademicYearResponse;
import io.github.kiransr99.parg.entity.AcademicYear;
import io.github.kiransr99.parg.repository.AcademicYearRepository;
import io.github.kiransr99.parg.service.AcademicYearService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AcademicYearImpl implements AcademicYearService {
    private final AcademicYearRepository academicYearRepository;
    @Override
    public AcademicYearResponse createAcademicYear(AcademicYearRequest academicYearRequest) {
        log.info("Creating academic year: {}", academicYearRequest);
        AcademicYear academicYear = new AcademicYear();
        academicYear.setYear(academicYearRequest.getYear());
        academicYear.setStartDate(academicYearRequest.getStartDate());
        academicYear.setEndDate(academicYearRequest.getEndDate());
        academicYearRepository.save(academicYear);
        return new AcademicYearResponse(academicYear);
    }

    @Override
    public AcademicYearResponse getAcademicYear(Long id) {
        log.info("Fetching academic year with id: {}", id);
        AcademicYear academicYear = academicYearRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.ACADEMIC_YEAR_NOT_FOUND)
        );
        return new AcademicYearResponse(academicYear);
    }

    @Override
    public AcademicYearResponse updateAcademicYear(Long id, AcademicYearUpdateRequest academicYearUpdateRequest) {
        log.info("Updating academic year with id: {}", id);
        AcademicYear academicYear = academicYearRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.ACADEMIC_YEAR_NOT_FOUND)
        );
        academicYear.setYear(academicYearUpdateRequest.getYear());
        academicYear.setStartDate(academicYearUpdateRequest.getStartDate());
        academicYear.setEndDate(academicYearUpdateRequest.getEndDate());
        academicYearRepository.save(academicYear);
        return new AcademicYearResponse(academicYear);
    }

    @Override
    public void deleteAcademicYear(Long id) {
        log.info("Deleting academic year with id: {}", id);
        AcademicYear academicYear = academicYearRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(SYSTEM_MESSAGE.ACADEMIC_YEAR_NOT_FOUND)
        );
        academicYearRepository.delete(academicYear);
    }
}
