package io.github.kiransr99.parg.service.impl;

import io.github.kiransr99.parg.dto.request.SchoolRequest;
import io.github.kiransr99.parg.dto.request.SchoolUpdateRequest;
import io.github.kiransr99.parg.dto.response.SchoolResponse;
import io.github.kiransr99.parg.dto.response.StudentResponse;
import io.github.kiransr99.parg.service.SchoolService;

import java.util.List;

public class SchoolImpl implements SchoolService {
    @Override
    public SchoolResponse saveSchool(SchoolRequest request) {
        return null;
    }

    @Override
    public List<SchoolResponse> getAllSchools() {
        return List.of();
    }

    @Override
    public SchoolResponse getSchoolById() {
        return null;
    }

    @Override
    public StudentResponse updateSchool(SchoolUpdateRequest updateRequest) {
        return null;
    }

    @Override
    public String deleteSchool(Long id) {
        return "";
    }
}
