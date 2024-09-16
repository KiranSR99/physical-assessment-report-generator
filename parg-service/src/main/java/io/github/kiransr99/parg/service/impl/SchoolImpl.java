package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.dto.request.SchoolRequest;
import io.github.kiransr99.parg.dto.request.SchoolUpdateRequest;
import io.github.kiransr99.parg.dto.response.SchoolResponse;
import io.github.kiransr99.parg.entity.School;
import io.github.kiransr99.parg.repository.SchoolRepository;
import io.github.kiransr99.parg.service.SchoolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchoolImpl implements SchoolService {
    private final SchoolRepository schoolRepository;

    @Override
    public SchoolResponse saveSchool(SchoolRequest request) {
        log.info("School save request received.");
        School school = new School();
        school.setName(request.getName());
        school.setPhone(request.getPhone());
        school.setAddress(request.getAddress());
        school.setEmail(request.getEmail());

        log.debug("School object before save: {}", school);

        School savedSchool = schoolRepository.save(school);
        log.info("School saved successfully.");

        return new SchoolResponse(savedSchool);
    }

    @Override
    public List<SchoolResponse> getAllSchools() {
        log.info("All school fetch request received.");
        List<School> schools = schoolRepository.findByStatusTrue();
        log.info("All school fetched successfully.");
        return schools.stream()
                .map(SchoolResponse::new)
                .toList();
    }

    @Override
    public SchoolResponse getSchoolById(Long schoolId) {
        log.info("School fetchById request received.");
        School school = schoolRepository.getReferenceById(schoolId);
        log.info("School fetched by Id successfully");
        return new SchoolResponse(school);
    }

    @Override
    public SchoolResponse updateSchool(SchoolUpdateRequest updateRequest) {
        log.info("School update request received.");
        School school = schoolRepository.getReferenceById(updateRequest.getSchoolId());
        school.setPhone(updateRequest.getPhone());
        school.setAddress(updateRequest.getAddress());
        school.setName(updateRequest.getName());
        school.setEmail(updateRequest.getEmail());
        School updatedSchool = schoolRepository.save(school);
        log.info("School updated successfully");
        return new SchoolResponse(updatedSchool);
    }

    @Override
    public String deleteSchool(Long id) {
        log.info("School deleted request received.");
        School school = schoolRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("School not found with id:" + id));
        school.setStatus(false);
        schoolRepository.save(school);
        log.info("School deleted successfully.");
        return "School deleted with id:" +id;
    }
}
